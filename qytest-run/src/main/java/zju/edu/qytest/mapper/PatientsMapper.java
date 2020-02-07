package zju.edu.qytest.mapper;

import org.springframework.data.repository.query.Param;
import zju.edu.qytest.pojo.Patients;
import zju.edu.qytest.pojo.Users;

import java.util.List;

public interface PatientsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Patients record);

    int insertSelective(Patients record);

    Patients selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Patients record);

    int updateByPrimaryKeyWithBLOBs(Patients record);

    int updateByPrimaryKey(Patients record);

    Patients findByUsername(@Param(value="username") String username);

    List<Patients> findByDoctorId(@Param(value="doctorId") Long doctorId);

    List<Patients> findAll();
}