package zju.edu.qytest.mapper;

import org.springframework.data.repository.query.Param;
import zju.edu.qytest.pojo.Ctimgs;
import zju.edu.qytest.pojo.Users;

import java.util.List;

public interface UsersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    Users findByUsername(@Param(value="username") String username);

    List<Users> findAll();
}