package com.dcw.app.rating.biz.pojo.tuangou;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adao12 on 2015/5/20.
 */
public class DownloadLinkStatus {

    private String status;

    private String count;

	@SerializedName("download_linkss")
	private List<DownloadLink> downloadLinkss;
}
