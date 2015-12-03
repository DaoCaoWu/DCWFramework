package com.dcw.app.net.api;


import com.dcw.app.biz.test.RichTextFragment;
import com.dcw.app.biz.test.model.Comment;
import com.dcw.app.biz.test.model.ListData;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public interface GitHub {

    @POST("/api/topic.comment.getList")
    Call<ListData<Comment>> getComments(@Body RichTextFragment.RequestData topicId);

    @POST("/api/topic.comment.getList")
    Observable<ListData<Comment>> getComments();
}
