package zju.edu.qyTest.consumer.util;


import zju.edu.qyTest.common.pojo.Users;
import zju.edu.qyTest.common.vo.GetUsersBean;

/**
 * 筛选User类中部分数据
 * @author zj
 * @date 2.14, 2020
 */
public class GetUsersUtils {

    /**
     * 为用户搜索提供类
     * @author zj
     * @date 2.14, 2020
     */
    public GetUsersBean forSearch(Users user){
        GetUsersBean getUsersBean = new GetUsersBean();
        getUsersBean.setGender(user.getGender());
        getUsersBean.setDepartment(user.getDepartment());
        getUsersBean.setHospital(user.getHospital());
        getUsersBean.setRealname(user.getRealname());
        getUsersBean.setTitle(user.getTitle());
        getUsersBean.setUsername(user.getUsername());
        getUsersBean.setUserType(user.getUserType());
        return getUsersBean;
    }
}
