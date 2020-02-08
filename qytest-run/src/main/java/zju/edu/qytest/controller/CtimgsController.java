package zju.edu.qytest.controller;

import com.google.common.base.Joiner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zju.edu.qytest.configuration.HttpResult;
import zju.edu.qytest.pojo.Ctimgs;
import zju.edu.qytest.pojo.Patients;
import zju.edu.qytest.service.CtimgsService;
import zju.edu.qytest.service.PatientsService;
import zju.edu.qytest.service.UsersService;
import net.sf.json.*;
import zju.edu.qytest.vo.ImgState;

import java.io.File;
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

    private static final Joiner FILE_JOINER = Joiner.on("/");
    private final String UPLOAD_FOLDER = "UPLOAD_FOLDER";

    @ApiOperation("CT图像上传")
    @PostMapping("/ctupload")
    public HttpResult CTUpload(@RequestBody Ctimgs record, @RequestPart MultipartFile file) throws JSONException {
        Map<File, MultipartFile> toSave = new HashMap<>(1);
        System.out.println("patient_id" + record.getPatientId() +
                "img_type" + record.getType() + "datetime" + record.getTime());
        String filename = record.getType() + "_" + UUID.randomUUID() + ".nii";
        File savePath = new File(FILE_JOINER.join(UPLOAD_FOLDER,filename));
        if(patientsService.findById(record.getId()) != null){
            ctimgsService.save(record);
            toSave.put(savePath, file);
            return HttpResult.ok();
        } else {
            return HttpResult.error();
        }
    }

    @ApiOperation("ct图像列表")
    @PostMapping("/imgList")
    public HttpResult imgList(@RequestBody String username) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        Patients patient = patientsService.findByUsername(username);
        if(patient == null){
            return HttpResult.ok();
        } else {
            return HttpResult.data(jsonObject.fromObject(patient));
        }
    }

    @ApiOperation("ct图像删除")
    @PostMapping("/delImage")
    public HttpResult delImage(@RequestBody String filename) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        ImgState imgState = new ImgState();
        Ctimgs ctimgs = ctimgsService.findByFilename(filename);
        if(ctimgs == null){
            return HttpResult.msg("It's null");
        } else {
            if(ctimgs.getType() == "cbf"){
                imgState.setCbf("success");
            } else if(ctimgs.getType() == "cbv"){
                imgState.setCbv("success");
            } else if(ctimgs.getType() == "mtt"){
                imgState.setMtt("success");
            } else if(ctimgs.getType() == "ct"){
                imgState.setCt("success");
            } else if(ctimgs.getType() == "tmax"){
                imgState.setTmax("success");
            }
            ctimgsService.delete(ctimgs);
            return HttpResult.data(jsonObject.fromObject(imgState));
        }
    }

}
