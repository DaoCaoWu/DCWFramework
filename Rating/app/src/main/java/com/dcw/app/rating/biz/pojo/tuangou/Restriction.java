package com.dcw.app.rating.biz.pojo.tuangou;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adao12 on 2015/5/20.
 */
public class Restriction {

	@SerializedName("is_reservation_required")
	private String isReservationRequired;

	@SerializedName("is_refundable")
	private String isRefundable;

	@SerializedName("special_tips")
	private String specialTips;
}
