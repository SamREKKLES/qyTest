package zju.edu.qyTest.service;


import java.util.List;

/**
 * 通用CURD接口
 * @author zj
 * @date 2.2, 2020
 */
public interface CurdService<T> {
	
	/**
	 * 保存操作
	 * @param record
	 * @return
	 */
	int save(T record);
	
	/**
	 * 删除操作
	 * @param record
	 * @return
	 */
	int delete(T record);
	
	/**
	 * 批量删除操作
	 * @param records
	 */
	int delete(List<T> records);
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	T findById(Long id);

	/**
	 * 查询所有
	 * @return
	 */
	List<T> findAll();

}