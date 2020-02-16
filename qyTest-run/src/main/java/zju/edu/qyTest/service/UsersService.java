package zju.edu.qyTest.service;

import zju.edu.qyTest.pojo.Users;
import zju.edu.qyTest.vo.GetUsersBean;

import java.util.List;

public interface UsersService extends CurdService<Users> {

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    Users findByUsername(String username);

    /**
     * 查找用户的菜单权限标识集合
     * @param userName
     * @return
     */
    String findPermissions(String userName);

}
