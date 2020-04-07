package zju.edu.qyTest.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import zju.edu.qyTest.common.pojo.Ctimgs;
import zju.edu.qyTest.common.pojo.Patients;
import zju.edu.qyTest.common.service.PatientsService;
import zju.edu.qyTest.provider.mapper.PatientsMapper;
import zju.edu.qyTest.provider.util.RedisUtil;

import java.util.List;

/**
 * Patients表
 * @author zj
 * @date 2.7, 2020
 */
@Slf4j
@Service(version = "1.0.0", interfaceClass = PatientsService.class)
@Component
public class PatientsServiceImpl implements PatientsService {

    @Autowired
    private PatientsMapper patientsMapper;
    @Autowired
    private RedisUtil redisUtil;

    private final static String ID = "patients_service_id:";
    private final static String USERNAME = "patients_service_username:";
    private final static String DOCTORID = "patients_service_doctorId:";
    private final static String ALLUSER = "patients_service_allUser";

    @Override
    public int save(Patients record) {
        int i;
        if(record.getId() == null || record.getId() == 0) {
            i = patientsMapper.insertSelective(record);
        } else {
            i = patientsMapper.updateByPrimaryKeySelective(record);
        }
        patientsRedisDelete(record);
        return i;
    }

    @Override
    public int delete(Patients record) {
        patientsRedisDelete(record);
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
        Patients patient = (Patients) redisUtil.get(ID + id);
        if (patient == null) {
            log.info("查询数据库");
            patient = patientsMapper.selectByPrimaryKey(id);
            if (!ObjectUtils.isEmpty(patient)) {
                redisUtil.set(ID + id, patient);
            } else {
                return null;
            }
        } else {
            log.info("查询缓存");
        }
        return patient;

    }

    @Override
    public List<Patients> findAll() {
        List<Patients> list = (List<Patients>) redisUtil.get(ALLUSER);
        if (list == null) {
            log.info("查询数据库");
            list = patientsMapper.findAll();
            if (!ObjectUtils.isEmpty(list)) {
                redisUtil.set(ALLUSER, list);
            } else {
                return null;
            }
        } else {
            log.info("查询缓存");
        }
        return list;
    }

    @Override
    public List<Patients> findByUsername(String username) {
        List<Patients> list =(List<Patients>) redisUtil.get(USERNAME + username);
        if (list == null) {
            log.info("查询数据库");
            list = patientsMapper.findByUsername(username);
            if (!ObjectUtils.isEmpty(list)) {
                redisUtil.set(USERNAME + username, list);
            } else {
                return null;
            }
        } else {
            log.info("查询缓存");
        }
        return list;
    }

    @Override
    public List<Patients> findByDoctorId(Long doctorId) {
        List<Patients> list =(List<Patients>) redisUtil.get(DOCTORID + doctorId);
        if (list == null) {
            log.info("查询数据库");
            list = patientsMapper.findByDoctorId(doctorId);
            if (!ObjectUtils.isEmpty(list)) {
                redisUtil.set(DOCTORID + doctorId, list);
            } else {
                return null;
            }
        } else {
            log.info("查询缓存");
        }
        return list;
    }

    public void patientsRedisDelete(Patients record){
        if (redisUtil.hasKey(ID + record.getId())) {
            redisUtil.del(ID + record.getId());
        }
        if (redisUtil.hasKey(USERNAME + record.getUsername())) {
            redisUtil.del(USERNAME + record.getUsername());
        }
        if (redisUtil.hasKey(DOCTORID + record.getDoctorId())){
            redisUtil.del(DOCTORID + record.getDoctorId());
        }
        redisUtil.del(ALLUSER );
    }
}
