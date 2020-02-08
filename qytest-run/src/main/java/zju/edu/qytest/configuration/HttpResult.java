package zju.edu.qytest.configuration;

import net.sf.json.JSONObject;
import net.sf.json.JSONException;

/**
 * HTTP结果封装
 * @author zj
 * @date 2.8, 2020
 */
public class HttpResult {

    private JSONObject object;

    public static HttpResult error() throws JSONException {
        HttpResult httpResult = new HttpResult();
        httpResult.object.put("status", "fail");
        return httpResult;
    }

    public static HttpResult ok() throws JSONException {
        HttpResult httpResult = new HttpResult();
        httpResult.object.put("status", "success");
        return httpResult;
    }

    public static HttpResult msg(String msg) throws JSONException {
        HttpResult httpResult = new HttpResult();
        httpResult.object.put("message", msg);
        return httpResult;
    }

    public static HttpResult data(JSONObject data) {
        HttpResult httpResult = new HttpResult();
        httpResult.object = data;
        return httpResult;
    }

    public JSONObject getObject() {
        return object;
    }

    public void setObject(JSONObject object) {
        this.object = object;
    }
}

