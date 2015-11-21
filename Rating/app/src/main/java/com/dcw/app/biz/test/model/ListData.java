package com.dcw.app.biz.test.model;

import java.util.List;

/**
 * <p>Title: ucweb</p>
 * <p/>
 * <p>Description: </p>
 * ......
 * <p>Copyright: Copyright (c) 2015</p>
 * <p/>
 * <p>Company: ucweb.com</p>
 *
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/10/21
 */
public class ListData<D> {

    List<D> list;

    public List<D> getList() {
        return list;
    }

    public void setList(List<D> list) {
        this.list = list;
    }
}
