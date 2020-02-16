package zju.edu.qyTest.configuration;


import lombok.extern.slf4j.Slf4j;

/**
 * HTTP结果封装
 * @author zj
 * @date 2.8, 2020
 */
@Slf4j
public class HttpResult {

    private String status;
    private Object data;

    public static HttpResult error() {
        HttpResult httpResult = new HttpResult();
        httpResult.setStatus("fail");
        return httpResult;
    }

    public static HttpResult error(Object data) {
        HttpResult httpResult = new HttpResult();
        httpResult.setStatus("fail");
        httpResult.setData(data);
        log.info("失败" + data);
        return httpResult;
    }

    public static HttpResult ok() {
        HttpResult httpResult = new HttpResult();
        httpResult.setStatus("success");
        return httpResult;
    }

    public static HttpResult ok(Object data) {
        HttpResult httpResult = new HttpResult();
        httpResult.setStatus("success");
        httpResult.setData(data);
        return httpResult;
    }

    public static HttpResult repeat() {
        HttpResult httpResult = new HttpResult();
        httpResult.setStatus("repeat");
        log.info("出现重复");
        return httpResult;
    }

    public static HttpResult repeat(Object data) {
        HttpResult httpResult = new HttpResult();
        httpResult.setStatus("repeat");
        httpResult.setData(data);
        return httpResult;
    }

    public static HttpResult reload() {
        HttpResult httpResult = new HttpResult();
        httpResult.setStatus("reload");
        log.info("用户信息缺失");
        return httpResult;
    }

    public static HttpResult data(Object data) {
        HttpResult httpResult = new HttpResult();
        httpResult.setStatus("success");
        httpResult.setData(data);
        log.info("数据：" + data);
        return httpResult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

