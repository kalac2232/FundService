package cn.kalac.bean;

import cn.kalac.http.ResultCode;
import com.google.gson.Gson;

public class ResultBean {
    private int code;
    private Object data;

    public ResultBean(Object data) {
        this.code = ResultCode.SUCCESS;
        this.data = data;
    }

    public ResultBean(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
