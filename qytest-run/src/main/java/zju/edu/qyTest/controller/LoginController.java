package zju.edu.qyTest.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import zju.edu.qyTest.configuration.HttpResult;
import zju.edu.qyTest.pojo.Users;
import zju.edu.qyTest.security.JwtAuthenticatioToken;
import zju.edu.qyTest.service.UsersService;
import zju.edu.qyTest.util.Pbkdf2Sha256;
import zju.edu.qyTest.util.SecurityUtils;
import zju.edu.qyTest.vo.LoginBean;
import zju.edu.qyTest.vo.RegisterBean;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
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

//{
//        "captcha": "string",
//        "password": "111111111",
//        "username": "root"
//        }
@RestController
@RequestMapping("/api")
@Api(tags = "登陆注册接口")
public class LoginController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private Producer producer;
    @Autowired
    private AuthenticationManager authenticationManager;


    @ApiOperation("验证码")
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
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
//        Object kaptcha = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
//        if(kaptcha == null){
//            return HttpResult.error("验证码已失效");
//        }
//        if(!captcha.equals(kaptcha)){
//            return HttpResult.error("验证码不正确");
//        }
        //用户信息及账号，密码的判断
        Users user = usersService.findByUsername(username);
        if (user == null) {
            return HttpResult.error("账号不存在");
        }
        if(!Pbkdf2Sha256.verification(password, user.getPassword())){
            return HttpResult.error("密码不正确");
        }
        request.getSession().setAttribute("current_id", usersService.findByUsername(username).getId());
        JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);
        return HttpResult.data(token);
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public HttpResult register(@RequestBody RegisterBean registerBean, HttpServletRequest request){
        String username = registerBean.getUsername();
        String password = registerBean.getPassword();
        String realname = registerBean.getRealname();
        Long   gender = registerBean.getGender();
        String department = registerBean.getDepartment();
        String hospital = registerBean.getHospital();
        String title = registerBean.getTitle();
        if(usersService.findByUsername(username) != null){
            return HttpResult.error("Username"+ username +"already exists!");
        } else {
            Users user_new = new Users();
            user_new.setUsername(username);
            user_new.setPassword(password);
            user_new.setRealname(realname);
            user_new.setDepartment(department);
            user_new.setGender(gender);
            user_new.setHospital(hospital);
            user_new.setTitle(title);
            user_new.setPassword(Pbkdf2Sha256.encode(password));
            usersService.save(user_new);
            return HttpResult.ok();
        }
    }

}
