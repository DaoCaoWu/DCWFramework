package com.dcw.app.rating.biz.contact;

import android.text.TextUtils;
import android.widget.SectionIndexer;

import java.util.Collections;
import java.util.List;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class ContactModel extends ListDataModel<Contact> implements SectionIndexer {

    public ContactModel(List<Contact> dataList) {
        super(dataList);
    }

    @Override
    public void setDataList(List<Contact> dataList) {
        super.setDataList(dataList);
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
        sortDataList();
    }

    private void sortDataList() {
        if (getCount() == 0) {
            return;
        }
        List<Contact> contacts = getDataList();
        for (Contact contact : contacts) {

            String pinyin = converterToFirstSpell(contact.getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                contact.setSortKey(sortString);
            } else {
                contact.setSortKey("#");
            }
        }
        Collections.sort(getDataList(), new PinyinComparator());
    }

    private String converterToFirstSpell(String chines) {
        String firstPinYin = chines.substring(0, 1);
        String result = PinYin.getPinYin2(firstPinYin);
        if (TextUtils.isEmpty(result)) {
            result = chines;
        }
        return result;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            if (getItem(i).getSortKey() == null) {
                continue;
            }
            char firstChar = getItem(i).getSortKey().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int pos) {
        return getItem(pos).getSortKey().charAt(0);
    }
}
