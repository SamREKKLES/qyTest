package zju.edu.qyTest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zju.edu.qyTest.pojo.Patients;
import zju.edu.qyTest.pojo.Users;
import zju.edu.qyTest.service.PatientsService;
import zju.edu.qyTest.service.UsersService;

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

    @Autowired
    private PatientsService patientsService;
    @Autowired
    private UsersService usersService;

    /**
     * 对于List patients用户的判断权限
     * @param user
     * @return
     */
    public List<Patients> listAuthority(Users user){
        List<Patients> patients = new ArrayList<>();
            if (user.getUsertype() == 0) {
                patients = patientsService.findAll();
            } else {
                patients = patientsService.findByDoctorId(user.getId());
            }
            return patients;
    }

    /**
     * 对于patient单个用户的判断权限
     * @param userId_search
     * @param request
     * @return
     */
    public boolean classAuthority(Long userId_search, HttpServletRequest request) {
        Long doctorId = (Long)request.getSession().getAttribute("current_id");
        Long usertype_search = usersService.findById(userId_search).getUsertype();
        Long usertype_current = usersService.findById(doctorId).getUsertype();
        if(usertype_current <= usertype_search) {
            return true;
        } else {
            return false;
        }
    }
}
