package com.dcw.app.rating.biz.test;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.MainActivity;
import com.dcw.app.rating.ui.adapter.BaseFragmentWrapper;
import com.dcw.app.rating.util.TaskExecutor;
import com.dcw.framework.util.LinkTouchMovementMethod;
import com.dcw.framework.util.RichTextBuilder;
import com.dcw.framework.util.TouchableSpan;
import com.dcw.framework.view.annotation.InjectLayout;
import com.dcw.framework.view.annotation.InjectView;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;

@InjectLayout(R.layout.fragment_rich_text)
public class RichTextFragment extends BaseFragmentWrapper {
    private static final String TAG = "RichTextFragment";

    @InjectView(value = R.id.tv_content, listeners = View.OnClickListener.class)
    private TextView mTVContent;

    @InjectView(R.id.tv_result)
    private TextView mTVResult;

    @InjectView(R.id.toolbar)
    private Toolbar mToolbar;

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initUI() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setTitle("RichTextFragment");
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
//                    String a = "a[好的][好的][好叫][好的][好叫][好叫][好叫][好叫][好叫][好]";
//                    Pattern p = Pattern.compile("\\[[\u4e00-\u9fa5]+\\]");
//                    int b = 0;
//                    Matcher m = p.matcher(a);
//                    while (m.find()) {
//                        b++;
//                    }
//
//                    Toast.makeText(getActivity(), content + b, 0).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Create a very simple REST adapter which points the GitHub API endpoint.
                        RestAdapter restAdapter = new RestAdapter.Builder()
                                .setEndpoint(API_URL)
                                .build();

                        // Create an instance of our GitHub API interface.
                        GitHub github = restAdapter.create(GitHub.class);

                        // Fetch and print a list of the contributors to this library.
                        Contributor contributors = github.contributors();
                        StringBuffer sb = new StringBuffer();
                        for (Reviews review : contributors.reviews) {
//                                sb.append(contributors.status).append("(").append(contributors.count).append(")");
                            sb.append(review.reviewId).append("(").append(review.textExcerpt).append(")");
                        }
                        final CharSequence cs = sb.toString();
                        TaskExecutor.runTaskOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTVResult.setText(cs);
                                System.out.println(cs);
                            }
                        });
                    }
                }).start();

            }
        }, "已存在文本").build();
        mTVContent.setText(sp);
        mTVContent.setMovementMethod(LinkTouchMovementMethod.getInstance());
    }

    @Override
    public void initListeners() {

    }

    private static final String API_URL = "http://api.dianping.com";

    static class Contributor {
        String status;
        int count;
        List<Reviews> reviews;
    }

    static class Reviews {
        @SerializedName("review_id")
        long reviewId;
        @SerializedName("text_excerpt")
        String textExcerpt;
    }

    interface GitHub {
        @GET("/v1/review/get_recent_reviews?appkey=80855985&sign=D68211CE7C912A80237F874B3979B1B56CAFC9BF&business_id=6110204")
        Contributor contributors();
    }

}
