package com.dcw.app.rating.biz.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dcw.app.rating.R;
import com.dcw.app.rating.app.RatingApplication;
import com.dcw.app.rating.biz.MainActivity;
import com.dcw.app.rating.db.bean.Cache;
import com.dcw.app.rating.db.dao.CacheDao;
import com.dcw.app.rating.ui.adapter.BaseFragmentWrapper;
import com.dcw.app.rating.util.TaskExecutor;
import com.dcw.framework.view.annotation.InjectLayout;
import com.dcw.framework.view.annotation.InjectView;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/5/5
 */
@InjectLayout(R.layout.list_fragment)
public class AbsListFragment extends BaseFragmentWrapper {

    @InjectView(R.id.lv_list)
    private ListView mLvBoxs;

    private List<Cache> mBoxs;

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Fresco.initialize(getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initData() {
        List<Cache> boxes = new ArrayList<Cache>();
        for (int i = 0; i < 10; i++) {
            Cache entity = new Cache();
            entity.setKey(i + "");
            entity.setValue("lalalallalalalal");
            entity.setExpireTime(System.currentTimeMillis() / 1000 + i);
            entity.setGroupId(i);
            boxes.add(entity);
        }
        getCacheDao().insertOrReplaceInTx(boxes);

        TaskExecutor.scheduleTask(50, new Runnable() {
            @Override
            public void run() {
                mBoxs = getCacheDao().loadAll();
                TaskExecutor.runTaskOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLvBoxs.setAdapter(new BoxListAdapter(getActivity(), mBoxs));
                    }
                });
            }
        });
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initListeners() {

    }

    private CacheDao getCacheDao() {
        return RatingApplication.getInstance().getDaoSession().getCacheDao();
    }
}
