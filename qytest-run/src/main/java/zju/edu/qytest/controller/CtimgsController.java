package zju.edu.qyTest.controller;

import com.google.common.base.Joiner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zju.edu.qyTest.configuration.HttpResult;
import zju.edu.qyTest.pojo.Ctimgs;
import zju.edu.qyTest.pojo.Patients;
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
import java.util.*;

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
                               @RequestParam(value = "datetime") @NotNull String time,
                               @RequestPart MultipartFile file, HttpServletRequest request) throws IOException
    {
        Map<File, MultipartFile> toSave = new HashMap<>(1);
        String uploadName = file.getOriginalFilename();
        String filename = type + "_" + UUID.randomUUID() + ".nii";
        File savePath = new File(FILE_JOINER.join(UPLOAD_FOLDER, patientId, filename));
        Patients patient = patientsService.findById(patientId);
        if(patient != null){
            Ctimgs record = new Ctimgs();
            record.setFilename(filename);
            record.setUploadname(uploadName);
            record.setTime(time);
            record.setType(type);
            record.setPatientId(patientId);
            record.setDoctorId((Long) request.getSession().getAttribute("current_id"));
            if(userAuthorityUtils.classAuthority(patient.getDoctorId(), request)){
                ctimgsService.save(record);
                toSave.put(savePath, file);
                for (Map.Entry<File, MultipartFile> entry : toSave.entrySet()) {
                    FileUtils.writeByteArrayToFile(entry.getKey(), entry.getValue().getBytes());
                }
                return HttpResult.ok();
            } else {
                return HttpResult.error("无权限");
            }
        } else {
            return HttpResult.error("It's null");
        }
    }


    @ApiOperation("病人的ct图像列表")
    @PostMapping("/imgList")
    public HttpResult imgList(@RequestParam(value = "patient") @NotNull Long patientId, HttpServletRequest request) throws JSONException {
        Patients patient = patientsService.findById(patientId);
        JSONArray ctArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        if(patient == null){
            return HttpResult.error("It's null");
        } else {
            if(userAuthorityUtils.classAuthority(patient.getDoctorId(), request)){
                List<Ctimgs> ctimgs = ctimgsService.findByPatientId(patient.getId());
                for(Ctimgs ctimg : ctimgs){
                    jsonObject1.put("img", ctimg);
                    ctArray.add(jsonObject1);
                }
                jsonObject.put("imgs", ctArray);
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
        if(ctimgs == null){
            return HttpResult.error("It's null");
        } else {
            if(userAuthorityUtils.classAuthority(ctimgs.getDoctorId(), request)){
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
