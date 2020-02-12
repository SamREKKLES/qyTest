package zju.edu.qyTest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zju.edu.qyTest.mapper.PatientsMapper;
import zju.edu.qyTest.pojo.Patients;
import zju.edu.qyTest.service.PatientsService;

import java.util.List;

/**
 * Patients表
 * @author zj
 * @date 2.7, 2020
 */

@Service
public class PatientsServiceImpl implements PatientsService {

    @Autowired
    private PatientsMapper patientsMapper;

    @Override
    public int save(Patients record) {
        if(record.getId() == null || record.getId() == 0) {
            return patientsMapper.insertSelective(record);
        }
        return patientsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(Patients record) {
        return patientsMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<Patients> records) {
        for(Patients record:records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public Patients findById(Long id) {
        return patientsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Patients> findAll() {
        return patientsMapper.findAll();
    }

    @Override
    public Patients findByUsername(String username) {
        return patientsMapper.findByUsername(username);
    }

    @Override
    public List<Patients> findByDoctorId(Long doctorId) {
        return patientsMapper.findByDoctorId(doctorId);
    }
}
