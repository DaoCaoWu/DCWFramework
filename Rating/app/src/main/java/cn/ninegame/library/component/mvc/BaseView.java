package cn.ninegame.library.component.mvc;

import cn.ninegame.library.component.mvc.core.Observer;

/**
 * Created by adao12 on 2015/10/17.
 */
public interface BaseView<E> extends Observer {

    void setViewListener(E listener);
}
