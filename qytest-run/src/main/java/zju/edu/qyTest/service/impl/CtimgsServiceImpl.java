package zju.edu.qyTest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zju.edu.qyTest.mapper.CtimgsMapper;
import zju.edu.qyTest.pojo.Ctimgs;
import zju.edu.qyTest.service.CtimgsService;

import java.util.List;

/**
 * Ctimgs表
 * @author zj
 * @date 2.7, 2020
 */

@Service
public class CtimgsServiceImpl implements CtimgsService {

    @Autowired
    private CtimgsMapper ctimgsMapper;

    @Override
    public int save(Ctimgs record) {
        if(record.getId() == null || record.getId() == 0) {
            return ctimgsMapper.insertSelective(record);
        }
        return ctimgsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(Ctimgs record) {
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
        return ctimgsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Ctimgs> findAll() {
        return ctimgsMapper.findAll();
    }

    @Override
    public Ctimgs findByFilename(String filename) {
        return ctimgsMapper.findByFilename(filename);
    }

    @Override
    public List<Ctimgs> findByPatientId(Long patientId) {
        return ctimgsMapper.findByPatientId(patientId);
    }
}
