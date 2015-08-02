package com.dcw.app.rating.biz.pojo.business;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adao12 on 2015/5/20.
 */
public class Business {

	@SerializedName("business_id")
	private String businessId;

    private String name;

	@SerializedName("branch_name")
	private String branchName;

    private String address;

    private String telephone;

    private String city;

    private List<Region> regionss;

    private List<Category> categoriess;

    private String latitude;

    private String longitude;

	@SerializedName("avg_rating")
	private String avgRating;

	@SerializedName("rating_img_url")
	private String ratingImgUrl;

	@SerializedName("rating_s_img_url")
	private String ratingSImgUrl;

	@SerializedName("product_grade")
	private String productGrade;

	@SerializedName("decoration_grade")
	private String decorationGrade;

	@SerializedName("service_grade")
	private String serviceGrade;

	@SerializedName("product_score")
	private String productScore;

	@SerializedName("decoration_score")
	private String decorationScore;

	@SerializedName("service_score")
	private String serviceScore;

	@SerializedName("avg_price")
	private String avgPrice;

	@SerializedName("review_count")
	private String reviewCount;

	@SerializedName("review_list_url")
	private String reviewListUrl;

    private String distance;

	@SerializedName("business_url")
	private String businessUrl;

	@SerializedName("photo_url")
	private String photoUrl;

	@SerializedName("s_photo_url")
	private String sPhotoUrl;

	@SerializedName("photo_count")
	private String photoCount;

	@SerializedName("photo_list_url")
	private String photoListUrl;

	@SerializedName("has_coupon")
	private String hasCoupon;

	@SerializedName("coupon_id")
	private String couponId;

	@SerializedName("coupon_description")
	private String couponDescription;

	@SerializedName("coupon_url")
	private String couponUrl;

	@SerializedName("has_deal")
	private String hasDeal;

	@SerializedName("deal_count")
	private String dealCount;

    private List<Deal> dealss ;

	@SerializedName("has_online_reservation")
	private String hasOnlineReservation;

	@SerializedName("online_reservation_url")
	private String onlineReservationUrl;
}
