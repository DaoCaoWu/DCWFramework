package com.dcw.app.rating.biz.contact.controller;

import com.dcw.app.rating.biz.contact.model.ContactModel;
import com.dcw.app.rating.biz.contact.view.LetterIndexView;
import com.dcw.app.rating.ui.mvc.Controller;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class LetterIndexController extends Controller<LetterIndexView, ContactModel> implements LetterIndexView.ViewListener {

    public LetterIndexController(LetterIndexView view, ContactModel model) {
        super(view, model);
        getView().setViewListener(this);
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        //该字母首次出现的位置
        int position = getModel().getPositionForSection(s.charAt(0));
        if (position != -1) {
            getView().getListView().setSelection(position + getView().getListView().getHeaderViewsCount());
        }
    }
}
