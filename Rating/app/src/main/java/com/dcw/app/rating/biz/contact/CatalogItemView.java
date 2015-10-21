package com.dcw.app.rating.biz.contact;

import android.view.View;
import android.widget.TextView;

import com.dcw.app.rating.R;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class CatalogItemView {
    View mLLCatalog;
    TextView mCatalogView;
    ItemView mItemView;

    public CatalogItemView(View convertView) {
        mLLCatalog = convertView.findViewById(R.id.catalog_bar);
        mCatalogView = (TextView) convertView.findViewById(R.id.tv_content);
        mItemView = new ItemView(convertView.findViewById(R.id.list_item));
    }

    public View getLLCatalog() {
        return mLLCatalog;
    }

    public void update(String sortKey, String iconUrl, String title, String content) {
        mCatalogView.setText(sortKey);
        mItemView.update(iconUrl, title, content);
    }
}
