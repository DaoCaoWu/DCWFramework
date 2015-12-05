package com.dcw.app.biz.test;

import android.os.Bundle;
import android.text.Spannable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dcw.app.app.App;
import com.dcw.app.biz.test.model.O;
import com.dcw.app.biz.test.model.Comment;
import com.dcw.app.biz.test.model.I;
import com.dcw.app.biz.test.model.IL;
import com.dcw.app.biz.toolbar.ToolbarController;
import com.dcw.app.biz.toolbar.ToolbarModel;
import com.dcw.app.net.api.GitHub;
import com.dcw.framework.util.LinkTouchMovementMethod;
import com.dcw.framework.util.RichTextBuilder;
import com.dcw.framework.util.TouchableSpan;
import com.dcw.framework.view.annotation.InjectLayout;
import com.devspark.appmsg.AppMsg;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ninegame.framework.ToastManager;
import cn.ninegame.framework.adapter.BaseFragmentWrapper;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@InjectLayout(com.dcw.app.R.layout.fragment_rich_text)
public class RichTextFragment extends BaseFragmentWrapper {
    private static final String TAG = "RichTextFragment";
    GitHub mGitHubService;
    @Bind(com.dcw.app.R.id.tv_content)
    TextView mTvContent;
    @Bind(com.dcw.app.R.id.iv_icon)
    ImageView mIvIcon;
    //    @InjectView(value = R.id.tv_content, listeners = View.OnClickListener.class)

    //    @InjectView(R.id.toolbar)
//    private Toolbar mToolbar;
//    @InjectView(R.id.tv_result)
//    private TextView mTVResult;

    @Override
    public void initData() {
//        RetrofitLoaderManager.initLoader(this, new CommentsLoader(getActivity(), mGitHubService), this);
        mGitHubService = ((App) getActivityComponent().activity().getApplication()).getNetworkComponent().retrofit().create(GitHub.class);
    }

    @Override
    public void initUI() {
        mToolbarController = new ToolbarController(findViewById(com.dcw.app.R.id.toolbar), new ToolbarModel(this.getClass().getSimpleName()));
        final String text = "文本点击事件测试:\n1.给新文本添加部分点击\n谷歌\n2.给整个新文本添加点击\n百度网址\n";
        int start = text.length() + 3;
        int end = start + 5;
        //给选中的文字添加链接和点击事件
        Spannable sp = new RichTextBuilder(getActivity()).append("文本点击事件测试:\n1.给新文本添加部分点击\n").appendTouchableText("谷歌网址", 0, 2, new TouchableSpan.OnClickListener() {
            @Override
            public void onClick(String content) {
//                    Toast.makeText(getActivity(), content, 0).show();
                startFragment(StateViewFragment.class);
            }
        }, "www.google.com").append("\n2.给整个新文本添加点击\n").appendTouchableText("百度网址", new TouchableSpan.OnClickListener() {
            @Override
            public void onClick(String content) {
                startFragment(StateViewFragment.class);
            }
        }, "www.baidu.com").append("\n3.给已存在文本添加点击\n").appendTouchableEdge(start, end, new TouchableSpan.OnClickListener() {
            @Override
            public void onClick(String content) {
                Call<IL<Comment>> call = mGitHubService.getComments(new O<String>("aaaaaa"));
                call.enqueue(new Callback<IL<Comment>>() {
                    @Override
                    public void onResponse(Response<IL<Comment>> response, Retrofit retrofit) {
                        if (response.body() != null && response.body().getList() != null) {
                            AppMsg appMsg = AppMsg.makeText(getActivity(), "成功aaa" + response.body().getList().size(), AppMsg.STYLE_INFO);
                            appMsg.setLayoutGravity(Gravity.BOTTOM);
                            appMsg.show();
                            Observable<IL<Comment>> observable = mGitHubService.getComments();
                            observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<IL<Comment>>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            e.printStackTrace();
                                            ToastManager.getInstance().showToast(e.getMessage());
                                        }

                                        @Override
                                        public void onNext(IL<Comment> listDataResultData) {
                                            AppMsg appMsg = AppMsg.makeText(getActivity(), "Observable成功" + listDataResultData.getList().size(), AppMsg.STYLE_INFO);
                                            appMsg.setLayoutGravity(Gravity.BOTTOM);
                                            appMsg.show();
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        ToastManager.getInstance().showToast(t.getMessage());
                    }
                });
            }
        }, "已存在文本").build();
        mTvContent.setText(sp);
        mTvContent.setMovementMethod(LinkTouchMovementMethod.getInstance());
        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                .defaultDisplayImageOptions(new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(10)).build())
                .build();
        ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().displayImage("http://img.tupianzj.com/uploads/allimg/140430/4-140430094939.jpg", mIvIcon);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
