package zju.edu.qyTest.common.vo;


/**
 * 登录接口封装对象
 * @author zj
 * @date 2.6, 2020
 */
public class LoginBean {

    private String username;

    private String password;

    private String captcha;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
