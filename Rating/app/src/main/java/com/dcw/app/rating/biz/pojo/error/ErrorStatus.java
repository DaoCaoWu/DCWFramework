package com.dcw.app.rating.biz.pojo.error;

/**
 * Created by adao12 on 2015/5/20.
 */
public class ErrorStatus {

    private String status;

    private String error;

    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setError(String error){
        this.error = error;
    }
    public String getError(){
        return this.error;
    }
}
