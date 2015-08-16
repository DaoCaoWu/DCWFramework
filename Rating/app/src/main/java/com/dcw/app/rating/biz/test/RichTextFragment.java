package com.dcw.app.rating.biz.test;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.MainActivity;
import com.dcw.app.rating.biz.test.model.Contributor;
import com.dcw.app.rating.biz.test.model.Reviews;
import com.dcw.app.rating.biz.test.module.GithubModule;
import com.dcw.app.rating.net.api.GitHub;
import com.dcw.app.rating.net.loader.RetrofitLoader;
import com.dcw.app.rating.net.loader.RetrofitLoaderManager;
import com.dcw.app.rating.ui.adapter.BaseFragmentWrapper;
import com.dcw.framework.util.LinkTouchMovementMethod;
import com.dcw.framework.util.RichTextBuilder;
import com.dcw.framework.util.TouchableSpan;
import com.dcw.framework.view.annotation.InjectLayout;
import com.dcw.framework.view.annotation.InjectView;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@InjectLayout(R.layout.fragment_rich_text)
public class RichTextFragment extends BaseFragmentWrapper implements Callback<Contributor> {
    private static final String TAG = "RichTextFragment";

    @InjectView(value = R.id.tv_content, listeners = View.OnClickListener.class)
    private TextView mTVContent;

    @InjectView(R.id.tv_result)
    private TextView mTVResult;

//    @InjectView(R.id.toolbar)
//    private Toolbar mToolbar;

    GitHub gitHubService;

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

    @Override
    public void initData() {
        gitHubService = GithubModule.buildGitHubRestClient();
        RetrofitLoaderManager.initLoader(this, new ContributorLoader(getActivity(), gitHubService), this);
    }

    @Override
    public void initUI() {
//        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        setTitle("RichTextFragment");
        String text = "文本点击事件测试:\n1.给新文本添加部分点击\n谷歌\n2.给整个新文本添加点击\n百度网址\n";
        int start = text.length() + 3;
        int end = start + 5;
        //给选中的文字添加链接和点击事件
        Spannable sp = new RichTextBuilder(getActivity()).append("文本点击事件测试:\n1.给新文本添加部分点击\n").appendTouchableText("谷歌网址", 0, 2, new TouchableSpan.OnClickListener() {
            @Override
            public void onClick(String content) {
//                    Toast.makeText(getActivity(), content, 0).show();
                startFragment(AbsListFragment.class);
            }
        }, "www.google.com").append("\n2.给整个新文本添加点击\n").appendTouchableText("百度网址", new TouchableSpan.OnClickListener() {
            @Override
            public void onClick(String content) {
                startFragment(StateViewFragment.class);
            }
        }, "www.baidu.com").append("\n3.给已存在文本添加点击\n").appendTouchableEdge(start, end, new TouchableSpan.OnClickListener() {
            @Override
            public void onClick(String content) {
                getLoaderManager().getLoader(ContributorLoader.class.hashCode()).startLoading();
            }
        }, "已存在文本").build();
        mTVContent.setText(sp);
        mTVContent.setMovementMethod(LinkTouchMovementMethod.getInstance());
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void failure(RetrofitError retrofitError) {

        Toast.makeText(getActivity(), "Error: " + retrofitError.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void success(Contributor contributors, Response response) {
        Log.d("ContributorLoader", "onSuccess");
        StringBuffer sb = new StringBuffer();
        for (Reviews review : contributors.reviews) {
//                                sb.append(contributors.status).append("(").append(contributors.count).append(")");
            sb.append(review.reviewId).append("(").append(review.textExcerpt).append(")");
        }
        final CharSequence cs = sb.toString();
        mTVResult.setText(cs);
        System.out.println(cs);
    }

    static class ContributorLoader extends RetrofitLoader<Contributor, GitHub> {

        public ContributorLoader(Context context, GitHub service) {
            super(context, service);
        }

        @Override
        public void call(GitHub service, Callback<Contributor> callback) {
            Log.d(this.getClass().getSimpleName(), "call");
            service.getContributors(callback);
        }
    }

}
