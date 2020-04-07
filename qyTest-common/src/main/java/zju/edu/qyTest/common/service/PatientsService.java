package zju.edu.qyTest.common.service;


import zju.edu.qyTest.common.pojo.Patients;

import java.util.List;

public interface PatientsService extends zju.edu.qyTest.common.service.CurdService<Patients> {

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    List<Patients> findByUsername(String username);

    /**
     * 根据医生ID查询
     * @param doctorId
     * @return
     */
    List<Patients> findByDoctorId(Long doctorId);
}
