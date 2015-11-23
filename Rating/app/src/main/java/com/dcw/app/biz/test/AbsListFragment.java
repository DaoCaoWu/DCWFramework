//package com.dcw.app.biz.test;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//
//import com.dcw.app.R;
//import com.dcw.app.app.App;
//import com.dcw.app.biz.MainActivity;
//import com.dcw.app.biz.toolbar.ToolbarController;
//import com.dcw.app.biz.toolbar.ToolbarModel;
//import com.dcw.framework.data.db.bean.Cache;
//import com.dcw.framework.data.db.dao.CacheDao;
//import cn.ninegame.framework.adapter.BaseFragmentWrapper;
//import com.dcw.app.util.TaskExecutor;
//import com.dcw.framework.view.annotation.InjectLayout;
//import com.dcw.framework.view.annotation.InjectView;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author JiaYing.Cheng
// * @version 1.0
// * @email adao12.vip@gmail.com
// * @create 15/5/5
// */
//@InjectLayout(R.layout.list_fragment)
//public class AbsListFragment extends BaseFragmentWrapper {
//
//    @InjectView(R.id.lv_list)
//    private ListView mLvBoxs;
//
//    private List<Cache> mBoxs;
//
//    @Override
//    public Class getHostActivity() {
//        return MainActivity.class;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
////        Fresco.initialize(getActivity());
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
//
//    @Override
//    public void initData() {
//        List<Cache> boxes = new ArrayList<Cache>();
//        for (int i = 0; i < 10; i++) {
//            Cache entity = new Cache();
//            entity.setKey(i + "");
//            entity.setValue("lalalallalalalal");
//            entity.setExpireTime(System.currentTimeMillis() / 1000 + i);
//            entity.setGroupId(i);
//            boxes.add(entity);
//        }
//        getCacheDao().insertOrReplaceInTx(boxes);
//
//        TaskExecutor.scheduleTask(50, new Runnable() {
//            @Override
//            public void run() {
//                mBoxs = getCacheDao().loadAll();
//                TaskExecutor.runTaskOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mLvBoxs.setAdapter(new BoxListAdapter(getActivity(), mBoxs));
//                    }
//                });
//            }
//        });
//    }
//
//    @Override
//    public void initUI() {
//        mToolbarController = new ToolbarController(findViewById(R.id.toolbar), new ToolbarModel(this.getClass().getSimpleName(), 0, true));
//    }
//
//    @Override
//    public void initListeners() {
//
//    }
//
////    private CacheDao getCacheDao() {
////        return App.getInstance().getDaoSession().getCacheDao();
////    }
//}
