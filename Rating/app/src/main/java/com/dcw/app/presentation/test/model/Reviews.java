package com.dcw.app.presentation.test.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adao12 on 2015/8/13.
 */
public class Reviews {

    @SerializedName("review_id")
    public long reviewId;

    @SerializedName("text_excerpt")
    public String textExcerpt;
}
