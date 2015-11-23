package com.dcw.app.net.api;


import com.dcw.app.biz.test.RichTextFragment;
import com.dcw.app.biz.test.model.Comment;
import com.dcw.app.biz.test.model.ListData;
import com.dcw.app.biz.test.model.ResultData;

import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface GitHub {

    @POST("/api/topic.comment.getList")
    Call<ResultData<ListData<Comment>>> getComments(@Body RichTextFragment.RequestData topicId);
}
