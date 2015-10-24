//package com.dcw.app.rating.biz.select;
//
//import android.widget.TextView;
//
//import com.dcw.app.rating.biz.contact.adapter.ContactAdapter;
//import com.dcw.app.rating.biz.contact.controller.LetterIndexController;
//import com.dcw.app.rating.biz.contact.model.ContactModel;
//import com.dcw.app.rating.ui.mvc.Controller;
//import com.dcw.app.rating.ui.mvc.core.Observable;
//import com.dcw.app.rating.ui.mvc.core.Observer;
//
///**
// * <p>Title: ucweb</p>
// * <p/>
// * <p>Description: </p>
// * ......
// * <p>Copyright: Copyright (c) 2015</p>
// * <p/>
// * <p>Company: ucweb.com</p>
// *
// * @author JiaYing.Cheng
// * @version 1.0
// * @email adao12.vip@gmail.com
// * @create 15/10/24
// */
//public class SelectContactController extends Controller<StickyView, ContactModel> implements StickyView.ViewListener, Observer {
//
//    public SelectContactController(StickyView view, ContactModel model) {
//        super(view, model);
//        new LetterIndexController(getView().getIndexView(), model);
//        ContactAdapter adapter = new ContactAdapter(getView().getContext(), getModel());
////        getView().getListView().setAdapter(adapter);
//        getModel().addObserver(this);
//        getModel().addObserver(adapter);
//        getModel().addObserver(getView());
//        getModel().addObserver(getView().getIndexView());
//        getView().setViewListener(this);
//    }
//
//    @Override
//    public void onCatalogViewShouldUpdate(TextView catalogView, int firstVisibleItem) {
////        catalogView.setText(((Contact) getView().getListView().getItemAtPosition(firstVisibleItem)).getSortKey());
//    }
//
//    @Override
//    public <T> void update(Observable observable, T data, Object... args) {
//    }
//}
