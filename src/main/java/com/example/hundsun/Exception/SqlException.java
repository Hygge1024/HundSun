package com.example.hundsun.Exception;

public class SqlException extends java.sql.SQLException {
    private Integer code;
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code){
        this.code = code;
    }
    public SqlException(Integer code, String message){
        super(message);
        this.code = code;
    }
    public SqlException(Integer code, String message, Throwable cause){
        super(message,cause);
        this.code = code;
    }
}
