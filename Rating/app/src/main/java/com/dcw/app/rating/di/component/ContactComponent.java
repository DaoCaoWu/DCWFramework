package com.dcw.app.rating.di.component;

import com.dcw.app.rating.di.module.ContactModule;
import com.dcw.app.rating.di.scope.ContactModuleScope;

import dagger.Component;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/11/19
 */
@ContactModuleScope
@Component(dependencies = FragmentComponent.class, modules = ContactModule.class)
public interface ContactComponent {

    void plus(FragmentComponent component);

}
