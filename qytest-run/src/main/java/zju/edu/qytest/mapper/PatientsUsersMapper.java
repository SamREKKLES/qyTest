package zju.edu.qytest.mapper;


import org.springframework.data.repository.query.Param;
import zju.edu.qytest.pojo.PatientsUsers;

import java.util.List;

public interface PatientsUsersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PatientsUsers record);

    int insertSelective(PatientsUsers record);

    PatientsUsers selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PatientsUsers record);

    int updateByPrimaryKey(PatientsUsers record);

    List<PatientsUsers> findPatientsUsers(@Param(value="patientId") Long patientId);

    int deleteByPatientId(@Param(value="patientId") Long patientId);
}