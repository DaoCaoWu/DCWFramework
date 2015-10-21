package com.dcw.app.rating.biz.contact;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class Contact extends SortLetter {

    private int mContactId;
    private String mName;
    private String mPhoneNum;
    private Long mPhotoId;
    private String mLookUpKey;
    private int mSelected = 0;
    private String mFormattedNumber;
    private String mPinyin;

    public Contact(String name, String phoneNum) {
        mName = name;
        mPhoneNum = phoneNum;
    }

    public int getContactId() {
        return mContactId;
    }

    public void setContactId(int contactId) {
        mContactId = contactId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhoneNum() {
        return mPhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        mPhoneNum = phoneNum;
    }

    public Long getPhotoId() {
        return mPhotoId;
    }

    public void setPhotoId(Long photoId) {
        mPhotoId = photoId;
    }

    public String getLookUpKey() {
        return mLookUpKey;
    }

    public void setLookUpKey(String lookUpKey) {
        mLookUpKey = lookUpKey;
    }

    public int getSelected() {
        return mSelected;
    }

    public void setSelected(int selected) {
        mSelected = selected;
    }

    public String getFormattedNumber() {
        return mFormattedNumber;
    }

    public void setFormattedNumber(String formattedNumber) {
        mFormattedNumber = formattedNumber;
    }

    public String getPinyin() {
        return mPinyin;
    }

    public void setPinyin(String pinyin) {
        mPinyin = pinyin;
    }
}
