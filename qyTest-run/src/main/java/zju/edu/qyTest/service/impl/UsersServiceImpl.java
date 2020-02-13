package zju.edu.qyTest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zju.edu.qyTest.mapper.UsersMapper;
import zju.edu.qyTest.pojo.Users;
import zju.edu.qyTest.service.UsersService;

import java.util.List;

/**
 * Users表
 * @author zj
 * @date 2.7, 2020
 */

@Service
public class
UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public int save(Users record) {
        if(record.getId() == null || record.getId() == 0) {
            return usersMapper.insertSelective(record);
        }
        return usersMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(Users record) {
        return usersMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<Users> records) {
        for(Users record:records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public Users findById(Long id) {
        return usersMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Users> findAll() {
        return usersMapper.findAll();
    }

    @Override
    public Users findByUsername(String username) {
        return usersMapper.findByUsername(username);
    }

    @Override
    public String findPermissions(String userName) {
        String perms = null;
        Users user = usersMapper.findByUsername(userName);
        if(user.getUsertype() != null && !"".equals(user.getUsertype())) {
            perms =user.getUsertype().toString();
        }
        return perms;
    }
}
