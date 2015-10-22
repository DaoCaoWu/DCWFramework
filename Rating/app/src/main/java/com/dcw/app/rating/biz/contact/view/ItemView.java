package com.dcw.app.rating.biz.contact.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dcw.app.rating.R;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class ItemView {

    ImageView mIvIcon;
    TextView mTvTitle;
    TextView mTvContent;

    public ItemView(View convertView) {
        mIvIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
        mTvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        mTvContent = (TextView) convertView.findViewById(R.id.tv_content);
    }

    public void update(String iconUrl, String title, String content) {
        mTvTitle.setText(title);
        mTvContent.setText(content);
    }
}
