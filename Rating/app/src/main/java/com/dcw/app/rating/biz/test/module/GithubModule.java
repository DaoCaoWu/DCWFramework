package com.dcw.app.rating.biz.test.module;

import com.dcw.app.rating.net.api.ApiHost;
import com.dcw.app.rating.net.api.GitHub;

import retrofit.RestAdapter;

/**
 * Created by adao12 on 2015/8/13.
 */
public class GithubModule {

    public static GitHub buildGitHubRestClient() {
        RestAdapter adapter =
                new RestAdapter.Builder().setEndpoint(ApiHost.API_URL).build();
        return adapter.create(GitHub.class);
    }
}
