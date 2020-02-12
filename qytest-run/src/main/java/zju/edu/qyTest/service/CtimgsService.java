package zju.edu.qyTest.service;

import zju.edu.qyTest.pojo.Ctimgs;

import java.util.List;


public interface CtimgsService extends CurdService<Ctimgs> {

    /**
     * 根据文件名查询
     * @param filename
     * @return
     */
    Ctimgs findByFilename(String filename);

    /**
     * 根据病人ID查询
     * @param patientId
     * @return
     */
    List<Ctimgs> findByPatientId(Long patientId);
}
