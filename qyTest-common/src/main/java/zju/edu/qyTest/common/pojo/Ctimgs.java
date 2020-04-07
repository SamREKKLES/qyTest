package zju.edu.qyTest.common.pojo;


import java.io.Serializable;

public class Ctimgs implements Serializable {
    private Long id;

    private String filename;

    private String uploadname;

    private String time;

    private String type;

    private Long patientId;

    private Long doctorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public String getUploadname() {
        return uploadname;
    }

    public void setUploadname(String uploadname) {
        this.uploadname = uploadname == null ? null : uploadname.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}