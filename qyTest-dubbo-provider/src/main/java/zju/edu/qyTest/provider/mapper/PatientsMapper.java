package zju.edu.qyTest.provider.mapper;

import org.apache.ibatis.annotations.Param;
import zju.edu.qyTest.common.pojo.Patients;

import java.util.List;

public interface PatientsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Patients record);

    int insertSelective(Patients record);

    Patients selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Patients record);

    int updateByPrimaryKeyWithBLOBs(Patients record);

    int updateByPrimaryKey(Patients record);

    List<Patients> findByUsername(@Param(value = "username") String username);

    List<Patients> findByDoctorId(@Param(value = "doctorId") Long doctorId);

    List<Patients> findAll();
}