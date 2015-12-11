package com.dcw.app.presentation.contact.pinyin;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/10/22.
 */
public class LetterToDigital {

    /**
     * 将联系人的姓名拼音全部转化为数字
     *
     * @param 联系人姓名拼音
     * @return 姓名拼音对应数字
     */
    public static String getNum(String search, boolean status) {
        String str = "";
        for (int i = 0; i < search.length(); i++) {
            String c = search.charAt(i) + "";
            if (c.equals("1")) {
                str = str + "1";
                if (status) {
                    i = i + 1;
                }
                continue;
            } else if (c.equals("A") || c.equals("B") || c.equals("C") || c.equals("2")
                    || c.equals("a") || c.equals("b") || c.equals("c")) {
                str = str + "2";
                if (status) {
                    i = i + 1;
                }
                continue;
            } else if (c.equals("D") || c.equals("E") || c.equals("F") || c.equals("3")
                    || c.equals("d") || c.equals("e") || c.equals("f")) {
                str = str + "3";
                if (status) {
                    i = i + 1;
                }
                continue;
            } else if (c.equals("G") || c.equals("H") || c.equals("I") || c.equals("4")
                    || c.equals("g") || c.equals("h") || c.equals("i")) {
                str = str + "4";
                if (status) {
                    i = i + 1;
                }
                continue;
            } else if (c.equals("J") || c.equals("K") || c.equals("L") || c.equals("5")
                    || c.equals("j") || c.equals("k") || c.equals("l")) {
                str = str + "5";
                if (status) {
                    i = i + 1;
                }
                continue;
            } else if (c.equals("M") || c.equals("N") || c.equals("O") || c.equals("6")
                    || c.equals("m") || c.equals("n") || c.equals("o")) {
                str = str + "6";
                if (status) {
                    i = i + 1;
                }
                continue;
            } else if (c.equals("P") || c.equals("Q") || c.equals("R") || c.equals("S") || c.equals("7")
                    || c.equals("p") || c.equals("q") || c.equals("r") || c.equals("s")) {
                str = str + "7";
                if (status) {
                    i = i + 1;
                }
                continue;
            } else if (c.equals("T") || c.equals("U") || c.equals("V") || c.equals("8")
                    || c.equals("t") || c.equals("u") || c.equals("v")) {
                str = str + "8";
                if (status) {
                    i = i + 1;
                }
                continue;
            } else if (c.equals("W") || c.equals("X") || c.equals("Y") || c.equals("Z") || c.equals("9")
                    || c.equals("w") || c.equals("x") || c.equals("y") || c.equals("z")) {
                str = str + "9";
                if (status) {
                    i = i + 1;
                }
                continue;
            } else if (c.equals("0")) {
                str = str + "0";
                if (status) {
                    i = i + 1;
                }
                continue;
            }
        }
        return str;
    }
}
