package zju.edu.qyTest.mapper;

import zju.edu.qyTest.pojo.Ctimgs;

public interface CtimgsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Ctimgs record);

    int insertSelective(Ctimgs record);

    Ctimgs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Ctimgs record);

    int updateByPrimaryKey(Ctimgs record);
}