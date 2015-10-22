package com.dcw.app.rating.biz.contact;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.database.MatrixCursor;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.widget.SectionIndexer;

import com.dcw.app.rating.util.TaskExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class ContactModel extends ListDataModel<Contact> implements SectionIndexer {

    public ContactModel(final Context context) {
        super();
        setDataList(getContactListFromLocal2(context));
        TaskExecutor.executeTask(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public ContactModel(List<Contact> dataList) {
        super(dataList);
    }

    private List<Contact> getContactListFromLocal2(Context context) {
        List<Contact> contactList = new ArrayList<Contact>();

        String[] contactProjection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
        };
        String[] phoneProjection = new String[] {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
        };
        String[] matrixProjection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                "phone_number"
        };
        // 查询联系人数据
        Cursor contactCursor = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, contactProjection, null, null, "sort_key");
        // 查询联系人数据
        Cursor phoneCursor = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, phoneProjection, null, null, "sort_key");

        CursorJoiner cursorJoiner = new CursorJoiner(contactCursor, new String[]{ContactsContract.Contacts._ID}
                , phoneCursor, new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID});
        MatrixCursor matrixCursor = new MatrixCursor(matrixProjection, contactCursor.getColumnCount());

        for (CursorJoiner.Result result : cursorJoiner) {
            switch (result) {
                case BOTH:
                    String contactId = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String contactName = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Contact contact = new Contact();
                    contact.setContactId(contactId);
                    contact.setName(contactName);
                    contact.setPhoneNum(phoneNumber);
                    matrixCursor.addRow(new String[]{contactId, contactName, phoneNumber});
                    contactList.add(contact);
                    break;
            }
        }

        contactCursor.close();
        phoneCursor.close();

        return contactList;
    }

    private List<Contact> getContactListFromLocal(Context context) {
        List<Contact> contactList = new ArrayList<Contact>();

        // 查询联系人数据
        Cursor cursor = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, "sort_key");
        if (cursor == null) {
            return contactList;
        }
        while (cursor.moveToNext()) {
            boolean hasPhoneNumber = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0;
            // 获取联系人的姓名
            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            if (!TextUtils.isEmpty(contactName) && hasPhoneNumber) {
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phoneCursor = context.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                                + contactId, null, null);
                if (phoneCursor != null) {
                    while (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        if (!TextUtils.isEmpty(phoneNumber)) {
                            // 获取联系人的Id
                            Contact contact = new Contact();
                            contact.setContactId(contactId);
                            contact.setName(contactName);
                            contact.setPhoneNum(phoneNumber);
                            if (!phoneCursor.isClosed()) {
                                phoneCursor.close();
                            }
                            contactList.add(contact);
                            break;
                        }
                    }
                }
            }
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return contactList;
    }

    @Override
    public void setDataList(List<Contact> dataList) {
        super.setDataList(dataList);
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
//        sortDataList();
    }

    private void sortDataList() {
        if (getCount() == 0) {
            return;
        }
        Collections.sort(getDataList(), new PinyinComparator());
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
}
