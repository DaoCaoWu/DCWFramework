package com.dcw.app.rating.biz.pojo.city;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adao12 on 2015/5/20.
 */
public class Category {
    
	@SerializedName("category_name")
	private String categoryName;

    private List<String> subcategoriess;

}
