package zju.edu.qyTest.provider.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import zju.edu.qyTest.common.pojo.Ctimgs;
import zju.edu.qyTest.common.service.CtimgsService;
import zju.edu.qyTest.provider.mapper.CtimgsMapper;
import zju.edu.qyTest.provider.util.RedisUtil;

import java.util.List;

/**
 * Ctimgs表
 * @author zj
 * @date 2.7, 2020
 */

@Slf4j
@Service(version = "1.0.0", interfaceClass = CtimgsService.class)
@Component
public class CtimgsServiceImpl implements CtimgsService {

    @Autowired
    private CtimgsMapper ctimgsMapper;
    @Autowired
    private RedisUtil redisUtil;
    
    private final static String ID = "ctimgs_service_id:";
    private final static String FILENAME = "ctimgs_service_filename:";
    private final static String PATIENTID = "ctimgs_service_patientId:";
    private final static String ALLUSER = "ctimgs_service_allUser";

    @Override
    public int save(Ctimgs record) {
        int i;
        if(record.getId() == null || record.getId() == 0) {
            i = ctimgsMapper.insertSelective(record);
        } else {
            i = ctimgsMapper.updateByPrimaryKeySelective(record);
        }
        ctimgsRedisDelete(record);
        return i;
    }

    @Override
    public int delete(Ctimgs record) {
        ctimgsRedisDelete(record);
        return ctimgsMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<Ctimgs> records) {
        for(Ctimgs record:records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public Ctimgs findById(Long id) {
        Ctimgs ctimg = (Ctimgs) redisUtil.get(ID + id);
        if (ctimg == null) {
            log.info("查询数据库");
            ctimg = ctimgsMapper.selectByPrimaryKey(id);
            if (!ObjectUtils.isEmpty(ctimg)) {
                redisUtil.set(ID + id, ctimg);
            } else {
                return null;
            }
        } else {
            log.info("查询缓存");
        }
        return ctimg;
    }

    @Override
    public List<Ctimgs> findAll() {
        List<Ctimgs> list = (List<Ctimgs>) redisUtil.get(ALLUSER);
        if (list == null) {
            log.info("查询数据库");
            list = ctimgsMapper.findAll();
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
    public Ctimgs findByFilename(String filename) {
        Ctimgs ctimg =(Ctimgs) redisUtil.get(FILENAME + filename);
        if (ctimg == null) {
            log.info("查询数据库");
            ctimg = ctimgsMapper.findByFilename(filename);
            if (!ObjectUtils.isEmpty(ctimg)) {
                redisUtil.set(FILENAME + filename, ctimg);
            } else {
                return null;
            }
        } else {
            log.info("查询缓存");
        }
        return ctimg;
    }

    @Override
    public List<Ctimgs> findByPatientId(Long patientId) {
        List<Ctimgs> list = (List<Ctimgs>) redisUtil.get(PATIENTID + patientId);
        if (list == null) {
            log.info("查询数据库");
            list = ctimgsMapper.findByPatientId(patientId);
            if (!ObjectUtils.isEmpty(list)) {
                redisUtil.set(PATIENTID + patientId, list);
            } else {
                return null;
            }
        } else {
            log.info("查询缓存");
        }
        return list;
    }
    
    public void ctimgsRedisDelete(Ctimgs record){
        if (redisUtil.hasKey(ID + record.getId())) {
            redisUtil.del(ID + record.getId());
        }
        if (redisUtil.hasKey(FILENAME + record.getFilename())) {
            redisUtil.del(FILENAME + record.getFilename());
        }
        if (redisUtil.hasKey(PATIENTID + record.getPatientId())){
            redisUtil.del(PATIENTID + record.getPatientId());
        }
        redisUtil.del(ALLUSER );
    }
}
