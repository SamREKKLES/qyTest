package zju.edu.qyTest.common.vo;

/**
 * ct图像格式封装对象
 * @author zj
 * @date 2.6, 2020
 */
public class ImgState {

    private String status = "success";

    private String cbf = "fail";

    private String cbv = "fail";

    private String mtt = "fail";

    private String ct = "fail";

    private String tmax = "fail";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCbf() {
        return cbf;
    }

    public void setCbf(String cbf) {
        this.cbf = cbf;
    }

    public String getCbv() {
        return cbv;
    }

    public void setCbv(String cbv) {
        this.cbv = cbv;
    }

    public String getMtt() {
        return mtt;
    }

    public void setMtt(String mtt) {
        this.mtt = mtt;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getTmax() {
        return tmax;
    }

    public void setTmax(String tmax) {
        this.tmax = tmax;
    }
}
