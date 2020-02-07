package zju.edu.qytest.mapper;

import org.springframework.data.repository.query.Param;
import zju.edu.qytest.pojo.CtimgsPatientsUsers;

import java.util.List;

public interface CtimgsPatientsUsersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CtimgsPatientsUsers record);

    int insertSelective(CtimgsPatientsUsers record);

    CtimgsPatientsUsers selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CtimgsPatientsUsers record);

    int updateByPrimaryKey(CtimgsPatientsUsers record);

    List<CtimgsPatientsUsers> findCtimgsPatientsUsers(@Param(value= "ctimgId") Long ctimgId);

    int deleteByCtimgId(@Param(value="ctimgId") Long ctimgId);
}