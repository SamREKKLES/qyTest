package zju.edu.qyTest.common.pojo;


import java.io.Serializable;

public class Patients implements Serializable {
    private Long id;

    private String username;

    private Long gender;

    private Long age;

    private String date;

    private String description;

    private String illnesshistory;

    private Long doctorId;

    private String checkResult;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Long getGender() {
        return gender;
    }

    public void setGender(Long gender) {
        this.gender = gender;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getIllnesshistory() {
        return illnesshistory;
    }

    public void setIllnesshistory(String illnesshistory) {
        this.illnesshistory = illnesshistory == null ? null : illnesshistory.trim();
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult == null ? null : checkResult.trim();
    }
}