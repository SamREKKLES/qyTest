package zju.edu.qyTest.mapper;

import org.apache.ibatis.annotations.Param;
import zju.edu.qyTest.pojo.Users;
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