package com.dcw.app.biz.contact.model;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.widget.SectionIndexer;

import com.dcw.app.biz.contact.model.bean.Contact;
import com.dcw.app.biz.contact.pinyin.PinyinComparator;
import cn.ninegame.library.component.adapter.model.select.SelectModel;
import com.dcw.app.util.TaskExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class ContactModel extends SelectModel<Contact> implements SectionIndexer {

    public static final int NOT_LOAD = 0;
    public static final int LOADING = 1;
    public static final int LOADED = 2;

    int mLoadContactListState;

    public ContactModel() {
        super();
    }

    public ContactModel(List<Contact> dataList) {
        super(dataList);
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 3 == 0) {
            return 1;
        }
        return 0;
    }

    public void loadContactListAsyn(final Context context) {
        TaskExecutor.executeTask(new Runnable() {
            @Override
            public void run() {
                mLoadContactListState = LOADING;
                final List<Contact> contacts = getContactListFromContactProvider(context);
                TaskExecutor.runTaskOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setDataList(contacts);
                        mLoadContactListState = LOADED;
                    }
                });
            }
        });
    }

    public void loadContactListAsyn(final Context context, final TaskExecutor.RunnableCallback<List<Contact>> callback) {
        TaskExecutor.executeTask(new Runnable() {
            @Override
            public void run() {
                mLoadContactListState = LOADING;
                final List<Contact> contacts = getContactListFromContactProvider(context);
                TaskExecutor.runTaskOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setDataList(contacts);
                        mLoadContactListState = LOADED;
                        if (callback != null) {
                            callback.onRun(contacts);
                        }
                    }
                });
            }
        });
    }

    public int getLoadContactListState() {
        return mLoadContactListState;
    }

    private List<Contact> getContactListFromContactProvider(Context context) {
        List<Contact> contactList = new ArrayList<Contact>();
        String[] contactProjection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.LOOKUP_KEY
        };
        String[] phoneProjection = new String[]{
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        // 查询联系人数据
        Cursor contactCursor = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, contactProjection, null, null, ContactsContract.Contacts.DISPLAY_NAME);
        if (contactCursor == null) {
            return contactList;
        }
        Cursor phoneCursor = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, phoneProjection, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        if (phoneCursor == null) {
            contactCursor.close();
            return contactList;
        }
        CursorJoiner cursorJoiner = new CursorJoiner(contactCursor, new String[]{ContactsContract.Contacts.DISPLAY_NAME}
                , phoneCursor, new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME});
        for (CursorJoiner.Result result : cursorJoiner) {
            switch (result) {
                case BOTH:
                    contactList.add(transform(contactCursor, phoneCursor));
                    break;
            }
        }
        contactCursor.close();
        phoneCursor.close();
        return contactList;
    }

    private Contact transform(Cursor contactCursor, Cursor phoneCursor) {
        String contactId = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts._ID));
        String contactName = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        String lookupKey = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
        Long phoneId = phoneCursor.getLong(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        return new Contact(contactId, contactName, phoneNumber, phoneId, lookupKey);
    }

    public void search(ContactModel model, String keyword) {
        if (model == null || model.getLoadContactListState() != LOADED || model.getCount() == 0 || TextUtils.isEmpty(keyword)) {
            return;
        }
        List<Contact> cacheContacts = model.getDataList();
        List<Contact> contacts = getDataList();
        if (contacts == null) {
            contacts = new ArrayList<Contact>();
        } else {
            contacts.clear();
        }
        for (Contact contact : cacheContacts) {
            if (contact.contain(keyword)) {
                contacts.add(contact);
            }
        }
        setDataList(contacts);
    }

    @Override
    public void clearData() {
        super.clearData();
        mLoadContactListState = NOT_LOAD;
    }

    @Override
    public void setDataList(List<Contact> dataList) {
        super.setDataList(dataList);
        sortDataList();
        notifyObservers();
    }

    private void sortDataList() {
        if (getCount() == 0) {
            return;
        }
        Collections.sort(getDataList(), new PinyinComparator());
        notifyObservers();
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
        Contact item = getItem(pos);
        if (item != null && item.getSortKey() != null && item.getSortKey().length() > 0) {
            return item.getSortKey().charAt(0);
        }
        return -1;
    }

    @Override
    public int getCount() {
        return super.getCount() >= 2 ? 2 : 0;
    }
}
