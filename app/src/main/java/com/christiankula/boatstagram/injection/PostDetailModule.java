package com.christiankula.boatstagram.injection;

import com.christiankula.boatstagram.post.details.PostDetailsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PostDetailModule {

    @Singleton
    @Provides
    PostDetailsPresenter providePostDetailPresenter() {
        return new PostDetailsPresenter();
    }
}
