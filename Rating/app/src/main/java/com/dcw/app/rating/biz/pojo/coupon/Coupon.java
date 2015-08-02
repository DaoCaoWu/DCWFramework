package com.dcw.app.rating.biz.pojo.coupon;

import com.dcw.app.rating.biz.pojo.business.Business;
import com.dcw.app.rating.biz.pojo.business.Category;
import com.dcw.app.rating.biz.pojo.business.Region;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adao12 on 2015/5/20.
 */
public class Coupon {

	@SerializedName("coupon_id")
	private String couponId;

    private String title;

    private String description;

    private List<Region> regionss ;

    private List<Category> categoriess ;

	@SerializedName("download_count")
	private String downloadCount;

	@SerializedName("publish_date")
	private String publishDate;

	@SerializedName("expiration_date")
	private String expirationDate;

    private String distance;

	@SerializedName("logo_img_url")
	private String logoImgUrl;

	@SerializedName("coupon_url")
	private String couponUrl;

	@SerializedName("coupon_h5_url")
	private String couponH5Url;

    private List<Business> businessess ;
}
