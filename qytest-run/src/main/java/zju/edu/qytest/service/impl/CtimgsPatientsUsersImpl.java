package zju.edu.qytest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zju.edu.qytest.mapper.CtimgsMapper;
import zju.edu.qytest.mapper.CtimgsPatientsUsersMapper;
import zju.edu.qytest.mapper.PatientsMapper;
import zju.edu.qytest.mapper.UsersMapper;
import zju.edu.qytest.pojo.CtimgsPatientsUsers;
import zju.edu.qytest.service.CurdService;

import java.util.List;

/**
 * Ctimgs_patients_users表
 * @author zj
 * @date 2.7, 2020
 */

@Service
public class CtimgsPatientsUsersImpl implements CurdService<CtimgsPatientsUsers> {

    @Autowired
    private CtimgsPatientsUsersMapper ctimgsPatientsUsersMapper;
    @Autowired
    private CtimgsMapper ctimgsMapper;
    @Autowired
    private PatientsMapper patientsMapper;
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public int save(CtimgsPatientsUsers record) {
        return 0;
    }

    @Override
    public int delete(CtimgsPatientsUsers record) {
        return 0;
    }

    @Override
    public int delete(List<CtimgsPatientsUsers> records) {
        return 0;
    }

    @Override
    public CtimgsPatientsUsers findById(Long id) {
        return null;
    }

    @Override
    public List<CtimgsPatientsUsers> findAll() {
        return null;
    }
}
