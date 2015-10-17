package com.dcw.app.rating.ui.mvc;

import com.dcw.app.rating.ui.mvc.core.Observer;

/**
 * Created by adao12 on 2015/10/17.
 */
public interface View<E> extends Observer {

    void setViewListener(E listener);
}
