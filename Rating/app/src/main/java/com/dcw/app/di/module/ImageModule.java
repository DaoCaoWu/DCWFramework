package com.dcw.app.di.module;

import com.dcw.app.app.App;
import com.dcw.app.config.ImageLoaderConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/23.
 */
@Module
public class ImageModule {

    @Provides @Singleton
    ImagePipelineConfig provideAppImageConfig(App application) {
        return ImageLoaderConfig.getImagePipelineConfig(application);
    }

    @Provides @Singleton
    Fresco provideImageLoader(App application, ImagePipelineConfig config) {
        Fresco.initialize(application, config);
        return null;
    }
}
