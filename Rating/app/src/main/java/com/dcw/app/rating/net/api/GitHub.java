package com.dcw.app.rating.net.api;

import com.dcw.app.rating.biz.test.model.Contributor;

import retrofit.http.GET;

public interface GitHub {

    @GET("/v1/review/get_recent_reviews?appkey=80855985&sign=D68211CE7C912A80237F874B3979B1B56CAFC9BF&business_id=6110204")
    Contributor getContributors();
}
