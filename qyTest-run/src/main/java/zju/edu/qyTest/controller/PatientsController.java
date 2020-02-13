package zju.edu.qyTest.controller;

import com.google.common.base.Joiner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zju.edu.qyTest.configuration.HttpResult;
import zju.edu.qyTest.pojo.Ctimgs;
import zju.edu.qyTest.pojo.Patients;
import zju.edu.qyTest.pojo.Users;
import zju.edu.qyTest.service.CtimgsService;
import zju.edu.qyTest.service.PatientsService;
import zju.edu.qyTest.service.UsersService;
import zju.edu.qyTest.util.UserAuthorityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.List;

/**
 * 病人信息接口
 * @author zj
 * @date 2.9, 2020
 */

@RestController
@RequestMapping("/api")
@Api(tags = "病人信息接口")
public class PatientsController {

    @Autowired
    private PatientsService patientsService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private CtimgsService ctimgsService;
    @Autowired
    private UserAuthorityUtils userAuthorityUtils;

    private static final Joiner FILE_JOINER = Joiner.on("/");
    private final String UPLOAD_FOLDER = "UPLOAD_FOLDER";
    private JSONObject jsonObject = new JSONObject();

    @ApiOperation("获取病人信息")
    @GetMapping("/getPatients")
    public HttpResult getPatients(HttpServletRequest request) throws JSONException {
        Users user = usersService.findById((Long) request.getSession().getAttribute("current_id"));
        JSONArray patientArray = new JSONArray();
        //根据医生权限不同返回不同病人信息
        List<Patients> patients = userAuthorityUtils.listAuthority(user);
        if (patients == null) {
            return HttpResult.error("It's null");
        } else {
            for (Patients patient : patients) {
                jsonObject.put("value", patient.getUsername());
                patientArray.add(jsonObject);
            }
            return HttpResult.data(patientArray);
        }
    }

    @ApiOperation("删除病人信息")
    @PostMapping("/delPatient")
    public HttpResult delPatient(@RequestParam(value = "patientid") @NotNull Long patientid, HttpServletRequest request) {
        Patients patient = patientsService.findById(patientid);
        if (patient == null) {
            return HttpResult.error("It's null");
        } else {
            //判断有无权限
            if(!userAuthorityUtils.classAuthority(patient.getDoctorId(), request)){
                return HttpResult.error("无权限");
            } else {
                patientsService.delete(patient);
                List<Ctimgs> ctimgs = ctimgsService.findByPatientId(patient.getId());
                for (Ctimgs ctimg : ctimgs) {
                    //删除目录下的ct图像
                    (new File(FILE_JOINER.join(UPLOAD_FOLDER, ctimg.getPatientId(), ctimg.getFilename()))).delete();
                    ctimgsService.delete(ctimg);
                }
                return HttpResult.ok();
            }
        }
    }

    @ApiOperation("增加病人")
    @PostMapping("/addPatient")
    public HttpResult addPatient(@RequestBody Patients record, HttpServletRequest request){
        if(record == null){
            return HttpResult.error("It's null");
        } else {
            record.setDoctorId((Long)request.getSession().getAttribute("current_id"));
            patientsService.save(record);
            System.out.println(record);
            return HttpResult.ok();
        }
    }

    @ApiOperation("根据姓名查询病人")
    @PostMapping("/searchPatient")
    public HttpResult searchPatient(@RequestParam(value = "username") @NotNull String username, HttpServletRequest request) throws JSONException{
        List<Patients> patients = patientsService.findByUsername(username);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        if(patients == null) {
            return HttpResult.error("It's null");
        }
        for(Patients patient: patients){
            if(userAuthorityUtils.classAuthority(patient.getDoctorId(), request)){
                jsonObject1.put("patient", patient);
                jsonArray.add(jsonObject1);
            } else {
                return HttpResult.error("无权限");
            }
        }
        jsonObject.put("patients", jsonArray);
        return HttpResult.data(jsonObject);
    }

    @ApiOperation("根据Id查询病人")
    @PostMapping("/searchIdPatient")
    public HttpResult searchIdPatient(@RequestParam(value = "id") @NotNull Long id, HttpServletRequest request){
        Patients patient = patientsService.findById(id);
        if(patient == null) {
            return HttpResult.error("It's null");
        } else {
            if(userAuthorityUtils.classAuthority(patient.getDoctorId(), request)){
                jsonObject.put("patient", patient);
                return HttpResult.data(jsonObject);
            } else {
                return HttpResult.error("无权限");
            }
        }
    }

    @ApiOperation("更改病人信息")
    @PostMapping("/changePatient")
    public HttpResult changePatient(@RequestBody Patients record, HttpServletRequest request){
        Patients patient = patientsService.findById(record.getId());
        if(patient == null){
            return HttpResult.error("It's null");
        } else {
            if(userAuthorityUtils.classAuthority(patient.getDoctorId(), request)){
                record.setDoctorId((Long)request.getSession().getAttribute("current_id"));
                patientsService.save(record);
                return HttpResult.ok();
            } else {
                return HttpResult.error("无权限");
            }
        }
    }

}
