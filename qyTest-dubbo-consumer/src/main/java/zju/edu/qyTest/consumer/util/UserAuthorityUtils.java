package zju.edu.qyTest.consumer.util;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import zju.edu.qyTest.common.pojo.Patients;
import zju.edu.qyTest.common.service.PatientsService;
import zju.edu.qyTest.common.service.UsersService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限管理相关
 * @author zj
 * @date 2.13, 2020
 */
@Component
public class UserAuthorityUtils {

    @Reference(version = "1.0.0")
    private PatientsService patientsService;
    @Reference(version = "1.0.0")
    private UsersService usersService;

    /**
     * 对于List patients用户的判断权限
     * @param request
     * @return
     */
    public List<Patients> listAuthority(HttpServletRequest request) throws NullPointerException{
        List<Patients> patients = new ArrayList<>();
        Long usertype_current = (Long)request.getSession().getAttribute("current_type");
        Long userId_current = (Long)request.getSession().getAttribute("current_id");
        if (userId_current == null){
            patients = null;
        } else if (usertype_current == 0) {
            patients = patientsService.findAll();
        } else {
            patients = patientsService.findByDoctorId(userId_current);
        }
        return patients;

    }

    /**
     * 对于patient单个用户的判断权限
     * @param userId_search
     * @param request
     * @return
     */
    public boolean classAuthority(Long userId_search, HttpServletRequest request) throws NullPointerException {
        Long usertype_current = (Long)request.getSession().getAttribute("current_type");
        Long usertype_search = usersService.findById(userId_search).getUserType();
        if (usertype_current == null){
            return false;
        } else if (usertype_current <= usertype_search) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 对登录信息是否正确的判断权限
     * true: 登录信息
     * false: 无数据
     * @param request
     * @return
     */

    public boolean checkLoginInfo(HttpServletRequest request) {
        Long userId_current = (Long)request.getSession().getAttribute("current_id");
        if (userId_current == null){
            return true;
        } else {
            return false;
        }
    }
}
