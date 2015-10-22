package com.dcw.app.rating.biz.contact;

import android.text.TextUtils;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/21.
 */
public class Contact extends SortLetter {

    private String mContactId;
    private String mName;
    private String mPhoneNum;
    private Long mPhotoId;
    private String mLookUpKey;
    private int mSelected = 0;
    private String mFormattedNumber;
    private String mPinyin;

    public Contact() {
    }

    public Contact(String contactId, String name, String phoneNum) {
        setContactId(contactId);
        setName(name);
        setPhoneNum(phoneNum);
    }

    public String getContactId() {
        return mContactId;
    }

    public void setContactId(String contactId) {
        mContactId = contactId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
        if (!TextUtils.isEmpty(name)) {
            // 有联系人姓名得到对应的拼音
            String pinyin = PinYin.getPinYin(name);
            setPinyin(pinyin);
            String firstPinyin = converterToFirstSpell(name);
            String sortString = firstPinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                setSortKey(sortString);
            } else {
                setSortKey("#");
            }
        }
    }

    private String converterToFirstSpell(String chines) {
        String firstPinYin = chines.substring(0, 1);
        String result = PinYin.getPinYin2(firstPinYin);
        if (TextUtils.isEmpty(result)) {
            result = chines;
        }
        return result;
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
