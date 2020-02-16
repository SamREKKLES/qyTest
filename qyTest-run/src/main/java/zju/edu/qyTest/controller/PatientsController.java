package zju.edu.qyTest.controller;

import com.google.common.base.Joiner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zju.edu.qyTest.configuration.HttpResult;
import zju.edu.qyTest.pojo.Ctimgs;
import zju.edu.qyTest.pojo.Patients;
import zju.edu.qyTest.service.CtimgsService;
import zju.edu.qyTest.service.PatientsService;
import zju.edu.qyTest.util.UserAuthorityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 病人信息接口
 * @author zj
 * @date 2.9, 2020
 */
@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "病人信息接口")
public class PatientsController {

    @Autowired
    private PatientsService patientsService;
    @Autowired
    private CtimgsService ctimgsService;
    @Autowired
    private UserAuthorityUtils userAuthorityUtils;

    private static final Joiner FILE_JOINER = Joiner.on("/");
    private final String UPLOAD_FOLDER = "UPLOAD_FOLDER";

    @ApiOperation("获取病人信息")
    @GetMapping("/getPatients")
    public HttpResult getPatients(HttpServletRequest request) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        //根据医生权限不同返回不同病人信息
        List<Patients> patients = userAuthorityUtils.listAuthority(request);
        if (patients == null) {
            //判断登录信息是否存在（用于后台刷新后session无数据产生的错误）
            if (userAuthorityUtils.checkLoginInfo(request)) {
                return HttpResult.reload();
            } else {
                return HttpResult.error("It's null");
            }
        } else {
            jsonObject.put("patients", patients);
            return HttpResult.data(jsonObject);
        }
    }

    @ApiOperation("删除病人信息")
    @PostMapping("/delPatient")
    public HttpResult delPatient(@RequestBody @NotNull Patients record, HttpServletRequest request) {
        Patients patient = patientsService.findById(record.getId());
        if (patient == null) {
            return HttpResult.error("It's null");
        } else {
            //判断有无权限
            if(!userAuthorityUtils.classAuthority(patient.getDoctorId(), request)){
                //判断登录信息是否存在（用于后台刷新后session无数据产生的错误）
                if (userAuthorityUtils.checkLoginInfo(request)) {
                    return HttpResult.reload();
                } else {
                    return HttpResult.error("无权限");
                }
            } else {
                patientsService.delete(patient);
                List<Ctimgs> ctimgs = ctimgsService.findByPatientId(patient.getId());
                for (Ctimgs ctimg : ctimgs) {
                    //删除目录下的ct图像
                    (new File(FILE_JOINER.join(UPLOAD_FOLDER, ctimg.getPatientId(), ctimg.getFilename()))).delete();
                    ctimgsService.delete(ctimg);
                }
                log.info("病人信息删除成功");
                return HttpResult.ok();
            }
        }
    }

    @ApiOperation("增加病人")
    @PostMapping("/addPatient")
    public HttpResult addPatient(@RequestBody Patients record, HttpServletRequest request){
        //判断登录信息是否存在（用于后台刷新后session无数据产生的错误）
        if (userAuthorityUtils.checkLoginInfo(request)) {
            return HttpResult.reload();
        } else {
            if(record == null){
                return HttpResult.error("It's null");
            } else {
                record.setDoctorId((Long)request.getSession().getAttribute("current_id"));
                patientsService.save(record);
                log.info("病人信息增加成功");
                return HttpResult.ok();
            }
        }
    }

    @ApiOperation("根据姓名查询病人")
    @PostMapping("/searchPatient")
    public HttpResult searchPatient(@RequestBody String username, HttpServletRequest request) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        List<Patients> patients = patientsService.findByUsername(username);
        List<Patients> patientsList = new ArrayList<>();
        if(patients == null) {
            return HttpResult.error("It's null");
        }
        for(Patients patient: patients){
            if(userAuthorityUtils.classAuthority(patient.getDoctorId(), request)){
                patientsList.add(patient);
            } else {
                return HttpResult.error("无权限");
            }
        }
        jsonObject.put("patients", patientsList);
        return HttpResult.data(jsonObject);
    }

    @ApiOperation("根据Id查询病人")
    @PostMapping("/searchIdPatient")
    public HttpResult searchIdPatient(@RequestBody @NotNull Patients record, HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        List<Patients> patientsList = new ArrayList<>();
        Patients patient = patientsService.findById(record.getId());
        if(patient == null) {
            return HttpResult.error("It's null");
        } else {
            if(userAuthorityUtils.classAuthority(patient.getDoctorId(), request)){
                patientsList.add(patient);
                jsonObject.put("patient", patientsList);
                return HttpResult.data(jsonObject);
            } else {
                //判断登录信息是否存在（用于后台刷新后session无数据产生的错误）
                if (userAuthorityUtils.checkLoginInfo(request)) {
                    return HttpResult.reload();
                } else {
                    return HttpResult.error("无权限");
                }
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
                log.info("病人信息修改成功");
                return HttpResult.ok();
            } else {
                //判断登录信息是否存在（用于后台刷新后session无数据产生的错误）
                if (userAuthorityUtils.checkLoginInfo(request)) {
                    return HttpResult.reload();
                } else {
                    return HttpResult.error("无权限");
                }
            }
        }
    }

}
