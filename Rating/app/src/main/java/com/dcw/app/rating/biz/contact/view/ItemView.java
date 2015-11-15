package com.dcw.app.rating.biz.contact.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dcw.app.R;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class ItemView extends BaseItemView {

    ImageView mIvIcon;
    TextView mTvTitle;
    TextView mTvContent;

    public ItemView(View itemView) {
        super(itemView.findViewById(R.id.list_item));
        mIvIcon = (ImageView) this.itemView.findViewById(R.id.iv_icon);
        mTvTitle = (TextView) this.itemView.findViewById(R.id.tv_title);
        mTvContent = (TextView) this.itemView.findViewById(R.id.tv_content);
    }

    public void update(String iconUrl, String title, String content) {
        mTvTitle.setText(title);
        mTvContent.setText(content);
    }
}
