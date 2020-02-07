package zju.edu.qyTest.mapper;

import zju.edu.qyTest.pojo.Patients;

public interface PatientsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Patients record);

    int insertSelective(Patients record);

    Patients selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Patients record);

    int updateByPrimaryKeyWithBLOBs(Patients record);

    int updateByPrimaryKey(Patients record);
}