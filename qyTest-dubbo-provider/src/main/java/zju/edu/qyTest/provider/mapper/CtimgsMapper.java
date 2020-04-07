package zju.edu.qyTest.provider.mapper;

import org.apache.ibatis.annotations.Param;
import zju.edu.qyTest.common.pojo.Ctimgs;

import java.util.List;

public interface CtimgsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Ctimgs record);

    int insertSelective(Ctimgs record);

    Ctimgs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Ctimgs record);

    int updateByPrimaryKey(Ctimgs record);

    Ctimgs findByFilename(@Param(value = "filename") String filename);

    List<Ctimgs> findAll();

    List<Ctimgs> findByPatientId(@Param(value = "patientId") Long patientId);
}