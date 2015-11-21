package com.dcw.app.net.api;


import com.dcw.app.biz.test.model.Comment;
import com.dcw.app.biz.test.model.ListData;
import com.dcw.app.biz.test.model.ResultData;

import retrofit.Callback;
import retrofit.http.GET;

public interface GitHub {

    @GET("/api/topic.comment.getList")
    void getComments(Callback<ResultData<ListData<Comment>>> callback);
}
