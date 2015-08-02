package com.dcw.app.rating.biz.pojo.error;

/**
 * Created by adao12 on 2015/5/20.
 */
public class Error {

    private String errorCode;

    private String errorMessage;

    public void setErrorCode(String errorCode){
        this.errorCode = errorCode;
    }
    public String getErrorCode(){
        return this.errorCode;
    }
    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
    public String getErrorMessage(){
        return this.errorMessage;
    }
}
