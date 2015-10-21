package com.dcw.app.rating.biz.contact;

import java.util.ArrayList;

public class PinYin {
    public static String getPinYin(String input) {
        ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance().get(input);
        StringBuilder sb = new StringBuilder();

        if (tokens != null && tokens.size() > 0) {
            for (HanziToPinyin.Token token : tokens) {
                if (HanziToPinyin.Token.PINYIN == token.type) {
                    if (sb.length() > 0) {
                        sb.append(' ');
                    }

                    sb.append(token.target);
                    sb.append(' ');
                    sb.append(token.source);

                } else {
                    if (sb.length() > 0) {
                        sb.append(' ');
                    }
                    sb.append(token.source);
                }
            }
        }

        return sb.toString().toLowerCase();
    }

    public static String getPinYin2(String input) {
        ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance().get(input);
        StringBuilder sb = new StringBuilder();

        if (tokens != null && tokens.size() > 0) {
            for (HanziToPinyin.Token token : tokens) {
                if (HanziToPinyin.Token.PINYIN == token.type) {
                    sb.append(token.target);

                } else {
                    sb.append(token.source);
                }
            }
        }

        return sb.toString().toUpperCase();
    }
}
