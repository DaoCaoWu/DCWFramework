package com.dcw.app.di.component;

import com.dcw.app.di.module.AppModule;
import com.dcw.app.di.module.ImageModule;
import com.facebook.drawee.backends.pipeline.Fresco;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/24.
 */
@Singleton
@Component(modules = {AppModule.class, ImageModule.class})
public interface ImageComponent {

    Fresco imageLoader();
}
