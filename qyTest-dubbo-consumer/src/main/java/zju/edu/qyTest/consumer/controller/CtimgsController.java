package zju.edu.qyTest.consumer.controller;

import net.sf.json.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.base.Joiner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zju.edu.qyTest.common.pojo.Ctimgs;
import zju.edu.qyTest.common.pojo.Patients;
import zju.edu.qyTest.common.service.CtimgsService;
import zju.edu.qyTest.common.service.PatientsService;
import zju.edu.qyTest.common.vo.ImgState;
import zju.edu.qyTest.consumer.config.HttpResult;
import zju.edu.qyTest.consumer.util.UserAuthorityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Ct图像接口
 * @author zj
 * @date 2.8, 2020
 */
@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "CT图像接口")
public class CtimgsController {

    @Reference(version = "1.0.0")
    private CtimgsService ctimgsService;
    @Reference(version = "1.0.0")
    private PatientsService patientsService;
    @Autowired
    private UserAuthorityUtils userAuthorityUtils;

    private static final Joiner FILE_JOINER = Joiner.on(File.separator);
    private static final String UPLOAD_FOLDER = "UPLOAD_FOLDER";

    @ApiOperation("CT图像上传")
    @PostMapping("/ctUpload")
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
                log.info("CT图像上传成功");
                return HttpResult.ok();
            } else {
                //判断登录信息是否存在（用于后台刷新后session无数据产生的错误）
                if (userAuthorityUtils.checkLoginInfo(request)) {
                    return HttpResult.reload();
                } else {
                    return HttpResult.error("无权限");
                }
            }
        } else {
            return HttpResult.error("It's null");
        }
    }


    @ApiOperation("病人的ct图像列表")
    @PostMapping("/imgList")
    public HttpResult imgList(@RequestParam(value = "patient") @NotNull Long patientId, HttpServletRequest request) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        Patients patient = patientsService.findById(patientId);
        if(patient == null){
            return HttpResult.error("It's null");
        } else {
            if(userAuthorityUtils.classAuthority(patient.getDoctorId(), request)){
                List<Ctimgs> ctimgs = ctimgsService.findByPatientId(patient.getId());
                jsonObject.put("imgs", ctimgs);
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


    @ApiOperation("ct图像删除")
    @PostMapping("/delImage")
    public HttpResult delImage(@RequestParam @NotNull String filename, HttpServletRequest request) throws JSONException {
        JSONObject jsonObject = new JSONObject();
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
