package zju.edu.qyTest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zju.edu.qyTest.configuration.HttpResult;
import zju.edu.qyTest.pojo.Users;
import zju.edu.qyTest.service.UsersService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户信息接口
 * @author zj
 * @date 2.9, 2020
 */

@RestController
@RequestMapping("/api")
@Api(tags = "用户信息接口")
public class UsersController {

    @Autowired
    private UsersService usersService;

    private JSONObject jsonObject = new JSONObject();

    @ApiOperation("用户名校验")
    @PostMapping("/usernameCheck")
    public HttpResult usernameCheck(@RequestParam(value = "username") @NotNull String username){
        if(usersService.findByUsername(username) == null){
            return HttpResult.ok();
        } else {
            return HttpResult.repeat();
        }
    }

    @PreAuthorize("hasAuthority('0')")
    @ApiOperation("搜索用户")
    @PostMapping("/searchUsers")
    public HttpResult searchUsers(@RequestParam(value = "username") @NotNull String username){
        if(username == null){
            return HttpResult.msg("It's null");
        } else if(username.equals("all")){
            List<Users> users = usersService.findAll();
            for(Users user: users) {
                jsonObject.put("users", user);
            }
            return HttpResult.data(jsonObject);
        } else {
            Users user = usersService.findByUsername(username);
            jsonObject.put("users", user);
            return HttpResult.data(jsonObject);
        }
    }
}
