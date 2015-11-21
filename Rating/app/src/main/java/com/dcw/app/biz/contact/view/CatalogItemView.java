package com.dcw.app.biz.contact.view;

import android.view.View;
import android.widget.TextView;

import com.dcw.app.R;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class CatalogItemView extends BaseItemView {
    View mLLCatalog;
    TextView mCatalogView;
    ItemView mItemView;

    public CatalogItemView(View itemView) {
        super(itemView);
        mLLCatalog = itemView.findViewById(R.id.catalog_bar);
        mCatalogView = (TextView) itemView.findViewById(R.id.tv_content);
        mItemView = new ItemView(itemView);
    }

    public View getLLCatalog() {
        return mLLCatalog;
    }

    public ItemView getItemView() {
        return mItemView;
    }

    public void update(String sortKey, String iconUrl, String title, String content) {
        mCatalogView.setText(sortKey);
        mItemView.update(iconUrl, title, content);
    }
}
