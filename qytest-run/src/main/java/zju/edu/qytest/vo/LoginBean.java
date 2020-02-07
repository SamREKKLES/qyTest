package zju.edu.qytest.vo;


/**
 * 登录接口封装对象
 * @author zj
 * @date 2.6, 2020
 */
public class LoginBean {

    private String username;
    private String password;
    private boolean remember;

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

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }
}
