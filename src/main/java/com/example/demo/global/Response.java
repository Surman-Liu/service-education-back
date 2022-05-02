package com.example.demo.global;

public class Response<T> {

    private StatusCode code;
    private String msg;
    private T data;

    public StatusCode isCode() {
        return code;
    }

    public void setCode(StatusCode code) {
        this.code = code;
    }

    public StatusCode getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 处理成功返回
     *
     * @return
     */
    public static <T> Response<T> success() {
        Response<T> response = new Response<T>();
        response.setCode(StatusCode.SUCCESS);
        response.setMsg("success");
        response.setData(null);
        return response;
    }

    /**
     * 处理成功返回
     *
     * @return
     */
    public static <T> Response<T> success(String msg) {
        Response<T> response = new Response<T>();
        response.setCode(StatusCode.SUCCESS);
        response.setMsg(msg);
        response.setData(null);
        return response;
    }

    /**
     * 处理成功返回
     *
     * @return
     */
    public static <T> Response<T> success(T obj) {
        Response<T> response = new Response<T>();
        response.setCode(StatusCode.SUCCESS);
        response.setMsg("success");
        response.setData(obj);
        return response;
    }

    /**
     * 处理成功返回
     *
     * @return
     */
    public static <T> Response<T> success(T obj, String msg) {
        Response<T> response = new Response<T>();
        response.setCode(StatusCode.SUCCESS);
        response.setMsg(msg);
        response.setData(obj);
        return response;
    }

    /**
     * 处理失败返回
     *
     * @return
     */
    public static <T> Response<T> error(T obj, String msg) {
        Response<T> response = new Response<T>();
        response.setCode(StatusCode.FAIL);
        response.setMsg(msg);
        response.setData(obj);
        return response;
    }

    /**
     * 处理异常返回
     *
     * @param msg
     * @return
     */
    public static <T> Response<T> error(String msg) {
        Response<T> response = new Response<T>();
        response.setCode(StatusCode.FAIL);
        response.setMsg(msg);
        response.setData(null);
        return response;
    }
}

