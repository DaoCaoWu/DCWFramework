package com.dcw.app.rating.biz.pojo.business;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adao12 on 2015/5/20.
 */
public class Deal {

    private String id;

    private String url;

	@SerializedName("deal_id")
	private String dealId;

    private String title;

    private String description;

    private String city;

	@SerializedName("list_price")
	private String listPrice;

	@SerializedName("current_price")
	private String currentPrice;

    private List<Region> regionss ;

    private List<Category> categoriess ;

	@SerializedName("purchase_count")
	private String purchaseCount;

	@SerializedName("purchase_deadline")
	private String purchaseDeadline;

	@SerializedName("publish_date")
	private String publishDate;

    private String details;

	@SerializedName("image_url")
	private String imageUrl;

	@SerializedName("s_image_url")
	private String sImageUrl;

	@SerializedName("more_image_urlss")
	private List<String> moreImageUrlss;

	@SerializedName("more_s_image_urlss")
	private List<String> moreSImageUrlss;

	@SerializedName("is_popular")
	private String isPopular;

    private String restrictions;

    private String notice;

	@SerializedName("deal_url")
	private String dealUrl;

	@SerializedName("deal_h5_url")
	private String dealH5Url;

	@SerializedName("commission_ratio")
	private String commissionRatio;

    private List<Business> businessess ;
}
