package zju.edu.qyTest.pojo;

import java.util.Date;

public class CtimgsPatientsUsers {
    private Integer id;

    private Integer patientId;

    private Integer ctimgId;

    private Date createTime;

    private Date updateTime;

    private Integer doctorId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getCtimgId() {
        return ctimgId;
    }

    public void setCtimgId(Integer ctimgId) {
        this.ctimgId = ctimgId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }
}