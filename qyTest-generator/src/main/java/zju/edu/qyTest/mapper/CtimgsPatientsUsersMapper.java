package zju.edu.qyTest.mapper;

import zju.edu.qyTest.pojo.CtimgsPatientsUsers;

public interface CtimgsPatientsUsersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CtimgsPatientsUsers record);

    int insertSelective(CtimgsPatientsUsers record);

    CtimgsPatientsUsers selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CtimgsPatientsUsers record);

    int updateByPrimaryKey(CtimgsPatientsUsers record);
}