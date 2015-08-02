package com.dcw.app.rating.biz.pojo.book;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adao12 on 2015/5/20.
 */
public class Review {

    @SerializedName("review_id")
    private String reviewId;

    @SerializedName("user_nickname")
    private String userNickname;

    @SerializedName("created_time")
    private String createdTime;

    @SerializedName("text_excerpt")
    private String textExcerpt;

    @SerializedName("review_rating")
    private String reviewRating;

    @SerializedName("rating_img_url")
    private String ratingImgUrl;

    @SerializedName("rating_s_img_url")
    private String ratingSImgUrl;

    @SerializedName("product_rating")
    private String productRating;

    @SerializedName("decoration_rating")
    private String decorationRating;

    @SerializedName("service_rating")
    private String serviceRating;

    @SerializedName("review_url")
    private String reviewUrl;

}

