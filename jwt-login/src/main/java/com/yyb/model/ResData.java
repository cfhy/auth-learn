package com.yyb.model;

public class ResData {
    private Object data;
    private int code;
    private String message;
    public ResData() {
        this(null,0,"");
    }
    public ResData(Object data) {
        this(data,0,"");
    }
    public ResData(Object data, int code) {
        this(data,code,"");
    }
    public ResData(Object data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
