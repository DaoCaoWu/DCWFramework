package com.dcw.app.rating.biz.pojo.tuangou;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adao12 on 2015/5/20.
 */
public class IdListStatus {

    private String status;

    private String count;

	@SerializedName("id_lists")
	private List<Integer> idLists;
}
