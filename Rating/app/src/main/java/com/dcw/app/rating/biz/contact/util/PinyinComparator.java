package com.dcw.app.rating.biz.contact.util;

import com.dcw.app.rating.biz.contact.model.bean.SortLetter;

import java.util.Comparator;

/**
 * <p>Title: ucweb</p>
 * <p/>
 * <p>Description: </p>
 * 根据好友昵称首字母排序
 * <p>Copyright: Copyright (c) 2015</p>
 * <p/>
 * <p>Company: ucweb.com</p>
 *
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create ${DATE}
 */
public class PinyinComparator implements Comparator<SortLetter> {

    public static final String SORT_LETTER_NUM_SIGN = "#";

    public int compare(SortLetter o1, SortLetter o2) {

        final String o1SortLetters = o1.getSortKey();
        final String o2SortLetters = o2.getSortKey();

        if (o1SortLetters == null || (o1SortLetters.length() == 0)) {
            return -1;
        }
        if (o2SortLetters == null || (o1SortLetters.length() == 0)) {
            return 1;
        }

        if (o2SortLetters.equals(SORT_LETTER_NUM_SIGN)) {
            return -1;
        } else if (o1.getSortKey().equals(SORT_LETTER_NUM_SIGN)) {
            return 1;
        } else {
            return o1.getSortKey().compareTo(o2SortLetters);
        }
    }
}
