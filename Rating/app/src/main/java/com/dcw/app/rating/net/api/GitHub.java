package com.dcw.app.rating.net.api;

import com.dcw.app.rating.biz.test.model.Comment;
import com.dcw.app.rating.biz.test.model.ListData;
import com.dcw.app.rating.biz.test.model.ResultData;

import retrofit.Callback;
import retrofit.http.GET;

public interface GitHub {

    @GET("/api/topic.comment.getList")
    void getComments(Callback<ResultData<ListData<Comment>>> callback);
}
