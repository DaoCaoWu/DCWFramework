package com.dcw.framework.data.api;

import com.dcw.framework.domain.user.User;

import retrofit.Call;
import retrofit.http.Body;

/**
 * create by adao12.vip@gmail.com on 15/11/23
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public interface UserService {

    Call<User> login(@Body User user);

}
