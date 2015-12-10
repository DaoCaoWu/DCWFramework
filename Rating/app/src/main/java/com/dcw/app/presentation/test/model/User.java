package com.dcw.app.presentation.test.model;

/**
 * <p>Title: ucweb</p>
 * <p/>
 * <p>Description: </p>
 * ......
 * <p>Copyright: Copyright (c) 2015</p>
 * <p/>
 * <p>Company: ucweb.com</p>
 *
 * @author zengzhuo
 * @version 1.0
 * @email zengzhuo@ucweb.com
 * @create 2015/3/31
 */
public class User {
    private long ucid;
    private String name;
    private String avatar;
    private int biggieFlag;
    private int gender;
    private int level;

    public long getUcid() {
        return ucid;
    }

    public void setUcid(long ucid) {
        this.ucid = ucid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getBiggieFlag() {
        return biggieFlag;
    }

    public void setBiggieFlag(int biggieFlag) {
        this.biggieFlag = biggieFlag;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
