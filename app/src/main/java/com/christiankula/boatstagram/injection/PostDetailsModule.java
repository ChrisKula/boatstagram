package com.christiankula.boatstagram.injection;

import com.christiankula.boatstagram.post.details.PostDetailsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module in charge of providing dependencies for {@link com.christiankula.boatstagram.post.details.PostDetailsActivity}
 */
@Module
public class PostDetailsModule {

    /**
     * Provides a {@link PostDetailsPresenter}
     */
    @Singleton
    @Provides
    PostDetailsPresenter providePostDetailPresenter() {
        return new PostDetailsPresenter();
    }
}
