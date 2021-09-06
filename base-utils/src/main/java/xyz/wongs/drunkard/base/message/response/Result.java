package xyz.wongs.drunkard.base.message.response;

import xyz.wongs.drunkard.base.message.enums.ResultCode;

import java.io.Serializable;

/**
 * @Description
 * @author WCNGS@QQ.COM
 * @Github <a>https://github.com/rothschil</a>
 * @date 2020/8/2 13:48
 * @Version 1.0.0
 */
public class Result extends SubResult implements Serializable {
    private static final long serialVersionUID = -4505655308965878999L;

    /**
     * 对需要响应的数据进行封装
     */
    private Object data;

    private Result() {
    }

    public Result(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMsg();
        this.data = data;
    }

    private void setResultCode(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMsg();
    }

    /** 返回成功
     * @Description
     * @param
     * @return xyz.wongs.drunkard.base.message.response.R
     * @throws
     * @date 20/11/13 17:15
     */
    public static Result success() {

        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }
    /** 返回成功
     * @Description
     * @param
     * @return xyz.wongs.drunkard.base.message.response.R
     * @throws
     * @date 20/11/13 17:15
     */
    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    /** 返回失败
     * @Description
     * @param
     * @return xyz.wongs.drunkard.base.message.response.R
     * @throws
     * @date 20/11/13 17:15
     */
    public static Result fail(Integer code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /** 返回失败
     * @Description
     * @param
     * @return xyz.wongs.drunkard.base.message.response.R
     * @throws
     * @date 20/11/13 17:15
     */
    public static Result fail(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
