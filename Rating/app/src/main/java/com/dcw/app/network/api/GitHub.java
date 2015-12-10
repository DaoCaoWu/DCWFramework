package com.dcw.app.network.api;


import com.dcw.app.presentation.test.model.O;
import com.dcw.app.presentation.test.model.Comment;
import com.dcw.app.presentation.test.model.IL;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public interface GitHub {

    @POST("/api/topic.comment.getList")
    Call<IL<Comment>> getComments(@Body O<String> topicId);

    @POST("/api/topic.comment.getList")
    Observable<IL<Comment>> getComments();
}
