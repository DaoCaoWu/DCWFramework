package com.dcw.app.rating.biz.test;

import android.content.Context;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.MainActivity;
import com.dcw.app.rating.biz.test.model.Comment;
import com.dcw.app.rating.biz.test.model.ListData;
import com.dcw.app.rating.biz.test.model.ResultData;
import com.dcw.app.rating.biz.test.module.GithubModule;
import com.dcw.app.rating.biz.toolbar.ToolbarController;
import com.dcw.app.rating.biz.toolbar.ToolbarModel;
import com.dcw.app.rating.net.api.GitHub;
import com.dcw.app.rating.net.loader.RetrofitLoader;
import com.dcw.app.rating.net.loader.RetrofitLoaderManager;
import com.dcw.app.rating.ui.framework.BaseFragmentWrapper;
import com.dcw.framework.util.LinkTouchMovementMethod;
import com.dcw.framework.util.RichTextBuilder;
import com.dcw.framework.util.TouchableSpan;
import com.dcw.framework.view.annotation.InjectLayout;
import com.dcw.framework.view.annotation.InjectView;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@InjectLayout(R.layout.fragment_rich_text)
public class RichTextFragment extends BaseFragmentWrapper implements Callback<ResultData<ListData<Comment>>> {
    private static final String TAG = "RichTextFragment";
    GitHub gitHubService;
    @InjectView(value = R.id.tv_content, listeners = View.OnClickListener.class)
    private TextView mTVContent;

    //    @InjectView(R.id.toolbar)
//    private Toolbar mToolbar;
    @InjectView(R.id.tv_result)
    private TextView mTVResult;

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

    @Override
    public void initData() {
        gitHubService = GithubModule.buildGitHubRestClient();
        RetrofitLoaderManager.initLoader(this, new CommentsLoader(getActivity(), gitHubService), this);
    }

    @Override
    public void initUI() {
        mToolbarController = new ToolbarController(findViewById(R.id.toolbar), new ToolbarModel(this.getClass().getSimpleName()));
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
                getLoaderManager().getLoader(CommentsLoader.class.hashCode()).startLoading();
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
    public void success(ResultData<ListData<Comment>> resultData, Response response) {
        Log.d("Loader", "onSuccess");
        StringBuffer sb = new StringBuffer();
        if (resultData.getData() != null) {
            List<Comment> list = resultData.getData().getList();
            if (list != null) {
                for (Comment comment : list) {
                    sb.append(comment.getText()).append("\n");
                }
                CharSequence cs = sb.toString();
                mTVResult.setText(cs);
                System.out.println(cs);
            }
        }
    }

    static class CommentsLoader extends RetrofitLoader<ResultData<ListData<Comment>>, GitHub> {

        public CommentsLoader(Context context, GitHub service) {
            super(context, service);
        }

        @Override
        public void call(GitHub service, Callback<ResultData<ListData<Comment>>> callback) {
            Log.d(this.getClass().getSimpleName(), "call");
            service.getComments(callback);
        }
    }

}
