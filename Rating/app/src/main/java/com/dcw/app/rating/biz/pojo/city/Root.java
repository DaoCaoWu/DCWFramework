package com.dcw.app.rating.biz.pojo.city;

import java.util.List;

/**
 * Created by adao12 on 2015/5/20.
 */
public class Root {

    private String status;

    private List<City> citiess;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<City> getCities() {
        return this.citiess;
    }

    public void setCities(List<City> cities) {
        this.citiess = cities;
    }
}
