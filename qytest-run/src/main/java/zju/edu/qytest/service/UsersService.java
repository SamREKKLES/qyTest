package zju.edu.qytest.service;

import zju.edu.qytest.pojo.Users;

public interface UsersService extends CurdService<Users> {

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    Users findByUsername(String username);
}
