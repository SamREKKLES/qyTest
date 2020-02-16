package zju.edu.qyTest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zju.edu.qyTest.configuration.HttpResult;
import zju.edu.qyTest.pojo.Users;
import zju.edu.qyTest.service.UsersService;
import zju.edu.qyTest.util.GetUsersUtils;
import zju.edu.qyTest.util.Pbkdf2Sha256;
import zju.edu.qyTest.util.UserAuthorityUtils;
import zju.edu.qyTest.vo.GetUsersBean;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息接口
 * @author zj
 * @date 2.9, 2020
 */
@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "用户信息接口")
public class UsersController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private UserAuthorityUtils userAuthorityUtils;

    @ApiOperation("用户名校验")
    @PostMapping("/usernameCheck")
    public HttpResult usernameCheck(@RequestParam(value = "username") @NotNull String username){
        if(usersService.findByUsername(username) == null){
            log.info("用户名校验成功");
            return HttpResult.ok();
        } else {
            return HttpResult.repeat();
        }
    }

    @ApiOperation("搜索用户")
    @PostMapping("/searchUsers")
    public HttpResult searchUsers(@RequestBody @NotNull String username, HttpServletRequest request) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        List<GetUsersBean> getUsersBeanArrayList = new ArrayList<>();
        GetUsersUtils getUsersUtils = new GetUsersUtils();
        Users user = usersService.findByUsername(username);
        Long userType = user.getUserType();
        if (userAuthorityUtils.checkLoginInfo(request)){
            return HttpResult.reload();
        } else{
            if (username == null){
                return HttpResult.error("It's null");
            } else if(userType == 0){
                List<Users> users = usersService.findAll();
                for(Users us: users){
                    GetUsersBean getUsersBean = getUsersUtils.forSearch(us);
                    getUsersBeanArrayList.add(getUsersBean);
                }
                jsonObject.put("users", getUsersBeanArrayList);
                return HttpResult.data(jsonObject);
            } else {
                GetUsersBean getUsersBean = getUsersUtils.forSearch(user);
                jsonObject.put("users", getUsersBean);
                return HttpResult.data(jsonObject);
            }
        }
    }

    @ApiOperation("登陆界面内增加用户")
    @PostMapping("/addUser")
    public HttpResult addUser(@RequestBody Users user, HttpServletRequest request){
        String username = user.getUsername();
        String password = user.getPassword();
        if (userAuthorityUtils.checkLoginInfo(request)){
            return HttpResult.reload();
        } else{
            if(usersService.findByUsername(username) != null){
                return HttpResult.error("用户名 "+ username +" 已存在");
            } else {
                user.setPassword(Pbkdf2Sha256.encode(password));
                usersService.save(user);
                log.info("登陆界面内增加用户成功");
                return HttpResult.ok();
            }
        }
    }
}
