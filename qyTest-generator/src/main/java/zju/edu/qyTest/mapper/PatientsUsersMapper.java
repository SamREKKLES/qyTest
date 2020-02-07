package zju.edu.qyTest.mapper;

import zju.edu.qyTest.pojo.PatientsUsers;

public interface PatientsUsersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PatientsUsers record);

    int insertSelective(PatientsUsers record);

    PatientsUsers selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PatientsUsers record);

    int updateByPrimaryKey(PatientsUsers record);
}