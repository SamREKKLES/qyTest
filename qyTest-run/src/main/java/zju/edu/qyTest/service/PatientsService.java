package zju.edu.qyTest.service;


import zju.edu.qyTest.pojo.Patients;
import java.util.List;

public interface PatientsService extends CurdService<Patients> {

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
