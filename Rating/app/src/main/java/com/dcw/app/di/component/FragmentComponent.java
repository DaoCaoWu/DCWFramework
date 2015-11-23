package com.dcw.app.di.component;

import com.dcw.app.di.module.FragmentModule;
import com.dcw.app.di.scope.FragmentScope;
import cn.ninegame.framework.adapter.BaseActivityWrapper;
import cn.ninegame.framework.adapter.BaseFragmentWrapper;

import dagger.Component;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/11/19
 */
@FragmentScope
@Component(dependencies = {ActivityComponent.class}, modules = {FragmentModule.class})
public interface FragmentComponent {

    BaseActivityWrapper activity();

    BaseFragmentWrapper fragment();
}
