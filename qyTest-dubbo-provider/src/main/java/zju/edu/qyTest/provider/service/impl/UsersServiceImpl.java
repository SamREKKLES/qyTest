package zju.edu.qyTest.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import zju.edu.qyTest.common.pojo.Users;
import zju.edu.qyTest.common.service.UsersService;
import zju.edu.qyTest.provider.mapper.UsersMapper;
import zju.edu.qyTest.provider.util.RedisUtil;

import java.util.List;

/**
 * Users表
 * @author zj
 * @date 2.7, 2020
 */
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Slf4j
@Service(version = "1.0.0", interfaceClass = UsersService.class)
@Component
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private RedisUtil redisUtil;

    private final static String ID = "users_service_id:";
    private final static String USERNAME = "users_service_username:";
    private final static String PERMISSIONS = "users_service_permissions:";
    private final static String ALLUSER = "users_service_allUser";

    @Override
    public int save(Users record) {
        int i;
        if(record.getId() == null || record.getId() == 0) {
            i = usersMapper.insertSelective(record);
        } else {
            i = usersMapper.updateByPrimaryKeySelective(record);
        }
        usersRedisDelete(record);
        return i;
    }

    @Override
    public int delete(Users record) {
        usersRedisDelete(record);
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
        Users user = (Users) redisUtil.get(ID + id);
        if (user == null) {
            log.info("查询数据库");
            user = usersMapper.selectByPrimaryKey(id);
            if (!ObjectUtils.isEmpty(user)) {
                redisUtil.set(ID + id, user);
            } else {
                return null;
            }
        } else {
            log.info("查询缓存");
        }
        return user;
    }

    @Override
    public List<Users> findAll() {
        List<Users> list = (List<Users>) redisUtil.get(ALLUSER);
        if (list == null) {
            log.info("查询数据库");
            list = usersMapper.findAll();
            if (!ObjectUtils.isEmpty(list)) {
                redisUtil.set(ALLUSER, list);
            } else {
                return null;
            }
        } else {
            log.info("查询缓存");
        }
        return list;
    }

    @Override
    public Users findByUsername(String username) {
        Users user = (Users) redisUtil.get(USERNAME + username);
        if (user == null) {
            log.info("查询数据库");
            user = usersMapper.findByUsername(username);
            if (!ObjectUtils.isEmpty(user)) {
                redisUtil.set(USERNAME + username, user);
            } else {
                return null;
            }
        } else {
            log.info("查询缓存");
        }
        return user;
    }

    @Override
    public String findPermissions(String userName) {
        Users user = findByUsername(userName);
        String perms = null;
        if(user.getUserType() != null && !"".equals(user.getUserType())) {
            perms = String.valueOf(user.getUserType());
        }
        return perms;
    }

    public void usersRedisDelete(Users record){
        if (redisUtil.hasKey(ID + record.getId())) {
            redisUtil.del(ID + record.getId());
        }
        if (redisUtil.hasKey(USERNAME + record.getUsername())) {
            redisUtil.del(USERNAME + record.getUsername());
        }
        if (redisUtil.hasKey(PERMISSIONS + record.getUserType())){
            redisUtil.del(PERMISSIONS + record.getUserType());
        }
        redisUtil.del(ALLUSER );
    }

}
