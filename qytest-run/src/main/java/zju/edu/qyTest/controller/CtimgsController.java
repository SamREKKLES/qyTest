package zju.edu.qyTest.controller;

import com.google.common.base.Joiner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zju.edu.qyTest.configuration.HttpResult;
import zju.edu.qyTest.pojo.Ctimgs;
import zju.edu.qyTest.pojo.Patients;
import zju.edu.qyTest.pojo.Users;
import zju.edu.qyTest.service.CtimgsService;
import zju.edu.qyTest.service.PatientsService;
import net.sf.json.*;
import zju.edu.qyTest.service.UsersService;
import zju.edu.qyTest.util.UserAuthorityUtils;
import zju.edu.qyTest.vo.ImgState;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Ct图像接口
 * @author zj
 * @date 2.8, 2020
 */
@RestController
@RequestMapping("/api")
@Api(tags = "CT图像接口")
public class CtimgsController {

    @Autowired
    private CtimgsService ctimgsService;
    @Autowired
    private PatientsService patientsService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private UserAuthorityUtils userAuthorityUtils;

    private static final Joiner FILE_JOINER = Joiner.on("/");
    private final String UPLOAD_FOLDER = "UPLOAD_FOLDER";
    private JSONObject jsonObject = new JSONObject();


    @ApiOperation("CT图像上传")
    @PostMapping("/ctupload")
    public HttpResult CTUpload(
                               @RequestParam(value = "patient_id") @NotNull Long patientId,
                               @RequestParam(value = "img_type") @NotNull String type,
                               @RequestParam(value = "datetime") @NotNull Date datetime,
                               @RequestPart MultipartFile file, HttpServletRequest request) throws IOException {
        Map<File, MultipartFile> toSave = new HashMap<>(1);
        String uploadName = file.getOriginalFilename();
        String time = new SimpleDateFormat("yyyy-MM-dd").format(datetime);
        System.out.println("patient_id" + patientId +
                "img_type" + type + "datetime" + time);
        String filename = type + "_" + UUID.randomUUID() + ".nii";
        File savePath = new File(FILE_JOINER.join(UPLOAD_FOLDER, patientId, filename));
        Ctimgs record = new Ctimgs();
        if(patientsService.findById(patientId) != null){
//            record.setId(id);
            record.setFilename(filename);
            record.setUploadname(uploadName);
            record.setTime(time);
            record.setType(type);
            record.setPatientId(patientId);
            record.setDoctorId((Long) request.getSession().getAttribute("current_id"));
            ctimgsService.save(record);
            toSave.put(savePath, file);
            for (Map.Entry<File, MultipartFile> entry : toSave.entrySet()) {
                FileUtils.writeByteArrayToFile(entry.getKey(), entry.getValue().getBytes());
            }
            return HttpResult.ok();
        } else {
            return HttpResult.error();
        }
    }


    @ApiOperation("病人的ct图像列表")
    @PostMapping("/imgList")
    public HttpResult imgList(@RequestParam(value = "patient") @NotNull String username, HttpServletRequest request) throws JSONException {
        Patients patient = patientsService.findByUsername(username);
        if(patient == null){
            return HttpResult.ok();
        } else {
            System.out.println(userAuthorityUtils.classAuthority(patient.getDoctorId(), request));
            if(userAuthorityUtils.classAuthority(patient.getDoctorId(), request)){
                jsonObject.put("imgs", patient);
                return HttpResult.data(jsonObject);
            } else {
                return HttpResult.error("无权限");
            }
        }
    }


    @ApiOperation("ct图像删除")
    @PostMapping("/delImage")
    public HttpResult delImage(@RequestParam @NotNull String filename, HttpServletRequest request) throws JSONException {
        ImgState imgState = new ImgState();
        Ctimgs ctimgs = ctimgsService.findByFilename(filename);
        Patients patient = patientsService.findById(ctimgs.getPatientId());
        if(ctimgs == null){
            return HttpResult.msg("It's null");
        } else {
            if(userAuthorityUtils.classAuthority(patient.getDoctorId(), request)){
                if(ctimgs.getType().equals("cbf")){
                    imgState.setCbf("success");
                } else if(ctimgs.getType().equals("cbv")){
                    imgState.setCbv("success");
                } else if(ctimgs.getType().equals("mtt")){
                    imgState.setMtt("success");
                } else if(ctimgs.getType().equals("ct")){
                    imgState.setCt("success");
                } else if(ctimgs.getType().equals("tamx")){
                    imgState.setTmax("success");
                }
                ctimgsService.delete(ctimgs);
                return HttpResult.data(jsonObject.fromObject(imgState));
            } else {
                return HttpResult.error("无权限");
            }
        }
    }

}
