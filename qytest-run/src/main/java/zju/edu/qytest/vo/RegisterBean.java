package zju.edu.qytest.vo;

/**
 * 注册接口封装对象
 * @author zj
 * @date 2.6, 2020
 */
public class RegisterBean {

    private String username;
    private String password;
    private String confirm;

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

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
