package com.neuedu.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;

/**
 * 响应前端的高复用对象，也就是在controller中通过
 * 该复用对象中的方法返回相应的信息
 * @param <T>
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) //排除null值
public class ServerResponse<T> {
    private Integer status; //返回响应的状态码：0-成功

    private T data;     //当status=0时，data接口返回响应的数据

    private String msg;     //响应的提示信息

    private ServerResponse(){

    }
    private ServerResponse(Integer status){
        this.status = status;
    }
    private ServerResponse(Integer status,String msg){
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(Integer status,String msg,T data){
        this.msg = msg;
        this.status = status;
        this.data = data;
    }

    /*
    判断接口是否连接成功
     */
    @JsonIgnore
    public  boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS;
    }

    /*
    当status=0
     */
    public static ServerResponse createServerResponseBySuccess(){
        return new ServerResponse(ResponseCode.SUCCESS);
    }


    /*
    当status=0且附带提示信息时
     */
    public static ServerResponse createServerResponseBySuccess(String msg){
        return new ServerResponse(ResponseCode.SUCCESS,msg);
    }

    /*
    当status=0且附带提示信息和data对象数据时
     */
    public static <T> ServerResponse createServerResponseBySuccess(String msg,T data){
        return new ServerResponse(ResponseCode.SUCCESS,msg,data);
    }


    /*
        当status=1时，此时表示响应因为某种原因而失败
        故因此此时只返回status或者status和msg
     */

    public static ServerResponse createServerResponseByError(){
        return new ServerResponse(ResponseCode.ERROR);
    }


    /*
    当status=1且返回提示信息时
     */

    public static ServerResponse createServerResponseByError(String msg){
        return new ServerResponse(ResponseCode.ERROR,msg);
    }

    /*
    当发生错误信息，但是状态码！=1，需要自定义状态码时，也是存在两种情况
    只返回status或者status和msg
     */

    public static ServerResponse createServerResponseByErrorCustom(Integer status){
        return new ServerResponse(status);
    }

    /*
    当返回status以及msg时
     */

    public static ServerResponse createServerResponseByErrorCustom(Integer status,String msg){
        return new ServerResponse(status,msg);
    }



    @Override
    public String toString() {
        return "ServerResponse{" +
                "status=" + status +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
