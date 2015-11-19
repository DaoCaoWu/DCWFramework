package com.dcw.app.rating.di.module;

import com.dcw.app.rating.di.component.FragmentComponent;
import com.dcw.app.rating.di.scope.ContactModuleScope;
import com.dcw.app.rating.biz.contact.controller.ContactController;
import com.dcw.app.rating.biz.contact.model.bean.Contact;

import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/11/19
 */
@Module
public class ContactModule {

    ContactController mController;

    @Inject
    FragmentComponent mFragmentComponent;

    @Inject
    public ContactModule(ContactController controller) {
        mController = controller;
    }

    @Provides
    @ContactModuleScope
    public List<Contact> provideContacts() {
        return null;
    }
}
