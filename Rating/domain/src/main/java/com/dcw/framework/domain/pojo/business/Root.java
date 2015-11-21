package com.dcw.framework.domain.pojo.business;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adao12 on 2015/5/20.
 */
public class Root {

    private String status;
    private int count;
    @SerializedName("total_count")
    private int totalCount;
    private List<Business> businessess;
}
