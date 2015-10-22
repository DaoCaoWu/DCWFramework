//package com.dcw.app.rating.biz.contact;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.provider.ContactsContract;
//import android.support.v4.widget.CursorAdapter;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.dcw.app.rating.R;
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
// * @create 15/10/22
// */
//public class AbsCursorAdapter<D, M extends CursorModel<D>> extends CursorAdapter implements Observer {
//
//    /**
//     * the activity context from the activity where adapter is in.
//     */
//    private Context mContext = null;
//
//    private M mModel;
//
//
//    public AbsCursorAdapter(Context context, M model) {
//        super(context, model.getCursor(), true);
//        mContext = context;
//        mModel = model;
//        mModel.addObserver(this);
//    }
//
//    public Context getContext() {
//        return mContext;
//    }
//
//    public void setContext(Context context) {
//        mContext = context;
//    }
//
//    public M getModel() {
//        return mModel;
//    }
//
//    public void setModel(M model) {
//        if (mModel.equals(model)) {
//            return;
//        }
//        mModel.deleteObserver(this);
//        mModel = model;
//        mModel.addObserver(this);
//    }
//
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        View convertView = LayoutInflater.from(context).inflate(R.layout.layout_list_item_catalog, null);
//        CatalogItemView holder = new CatalogItemView(convertView);
//        convertView.setTag(holder);
//        return convertView;
//    }
//
//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//        CatalogItemView holder = (CatalogItemView)view.getTag();
//
//        String[] matrixProjection = new String[] {
//                ContactsContract.Contacts._ID,
//                ContactsContract.Contacts.DISPLAY_NAME,
//                "phone_number"
//        };
//
//        int section = getModel().getSectionForPosition(position);
//        if (position == getModel().getPositionForSection(section)) {
//            holder.getLLCatalog().setVisibility(View.VISIBLE);
//        } else {
//            holder.getLLCatalog().setVisibility(View.GONE);
//        }
//        if (contact != null) {
//            holder.update(contact.getSortKey(), null, contact.getName(), contact.getPhoneNum());
//        }
//    }
//
//    @Override
//    public <T> void update(Observable observable, T data, Object... args) {
//
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return super.getItemId(position);
//    }
//
//    @Override
//    public D getItem(int position) {
//        Cursor cursor = (Cursor)super.getItem(position);
//        Contact contact = null;
//        if (cursor != null) {
//            contact = new Contact();
//            contact.setContactId(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)));
//            contact.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
//            contact.setPhoneNum(cursor.getString(cursor.getColumnIndex("phone_number")));
//        }
//        return contact;
//    }
//}
