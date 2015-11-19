package com.dcw.app.rating.di.component;

import com.dcw.app.rating.di.module.FragmentModule;
import com.dcw.app.rating.di.scope.FragmentScope;
import com.dcw.app.ui.framework.BaseFragmentWrapper;

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

    BaseFragmentWrapper fragment();
}
