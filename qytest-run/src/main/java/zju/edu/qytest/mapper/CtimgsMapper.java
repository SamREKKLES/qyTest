package zju.edu.qytest.mapper;

import org.springframework.data.repository.query.Param;
import zju.edu.qytest.pojo.Ctimgs;
import java.util.List;

public interface CtimgsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Ctimgs record);

    int insertSelective(Ctimgs record);

    Ctimgs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Ctimgs record);

    int updateByPrimaryKey(Ctimgs record);

    Ctimgs findByFilename(@Param(value="filename") String filename);

    List<Ctimgs> findAll();
}