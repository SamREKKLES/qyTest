package zju.edu.qytest.service;

import zju.edu.qytest.pojo.Ctimgs;


public interface CtimgsService extends CurdService<Ctimgs> {

    /**
     * 根据文件名查询
     * @param filename
     * @return
     */
    Ctimgs findByFilename(String filename);
}
