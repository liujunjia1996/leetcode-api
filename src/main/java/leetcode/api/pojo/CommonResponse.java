package leetcode.api.pojo;

import lombok.Data;

@Data
public class CommonResponse {

    int code = 200;

    Object data;

    String msg = "ok";

    CommonResponse(Object data) {
        this.data = data;
    }

    public static CommonResponse success(Object data) {
        return new CommonResponse(data);
    }

    public static CommonResponse fail(int code, String msg) {
        CommonResponse resp = new CommonResponse(null);
        resp.setCode(code);
        resp.setMsg(msg);
        return resp;
    }

}
