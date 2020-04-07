package zju.edu.qyTest.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import zju.edu.qyTest.common.pojo.Users;
import zju.edu.qyTest.common.service.UsersService;
import zju.edu.qyTest.common.vo.LoginBean;
import zju.edu.qyTest.consumer.config.HttpResult;
import zju.edu.qyTest.consumer.security.JwtAuthenticatioToken;
import zju.edu.qyTest.consumer.util.Pbkdf2Sha256;
import zju.edu.qyTest.consumer.util.SecurityUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录注册接口
 * @author zj
 * @date 2.9, 2020
 */
@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "登陆注册接口")
public class LoginController {

    @Reference(version = "1.0.0")
    private UsersService usersService;
    @Autowired
    private Producer producer;
    @Autowired
    private AuthenticationManager authenticationManager;


    @ApiOperation("验证码")
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        // 生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);
        // 保存到验证码到 session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public HttpResult login(@RequestBody LoginBean loginBean, HttpServletRequest request){
        String username = loginBean.getUsername();
        String password = loginBean.getPassword();
        String captcha = loginBean.getCaptcha();
        // 从session中获取之前保存的验证码跟前台传来的验证码进行匹配
        Object kaptcha = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if(kaptcha == null){
            return HttpResult.error("验证码已失效");
        }
        if(!captcha.equals(kaptcha)){
            return HttpResult.error("验证码不正确");
        }
        //用户信息及账号，密码的判断
        Users user = usersService.findByUsername(username);
        if (user == null) {
            return HttpResult.error("账号不存在");
        }
        if(!Pbkdf2Sha256.verification(password, user.getPassword())){
            return HttpResult.error("密码不正确");
        }
        request.getSession().setAttribute("current_id", user.getId());
        request.getSession().setAttribute("current_type", user.getUserType());
        JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);
        log.info("登陆成功");
        return HttpResult.data(token);
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public HttpResult register(@RequestBody Users user){
        String username = user.getUsername();
        String password = user.getPassword();
        if(usersService.findByUsername(username) != null){
            return HttpResult.error("用户名 "+ username +" 已存在");
        } else {
            user.setPassword(Pbkdf2Sha256.encode(password));
            usersService.save(user);
            log.info("用户"+ username +"注册成功");
            return HttpResult.ok();
        }
    }

    @ApiOperation("登出")
    @GetMapping("/logout")
    public void logout(HttpServletRequest request){
        //1.销毁session
        request.getSession().invalidate();
        log.info("退出登录");
//        request.getCookies();
    }

}
