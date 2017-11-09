package com.christiankula.boatstagram.injection;

import com.christiankula.boatstagram.post.details.PostDetailsModel;
import com.christiankula.boatstagram.post.details.PostDetailsMvp;
import com.christiankula.boatstagram.post.details.PostDetailsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module in charge of providing dependencies for everything related to the Post details
 */
@Module
public class PostDetailsModule {

    /**
     * Provides a {@link PostDetailsMvp.Presenter}
     *
     * @param model corresponding {@link PostDetailsMvp.Presenter}
     */
    @Singleton
    @Provides
    PostDetailsMvp.Presenter providePostDetailPresenter(PostDetailsMvp.Model model) {
        return new PostDetailsPresenter(model);
    }

    /**
     * Provides a {@link PostDetailsMvp.Model}
     */
    @Singleton
    @Provides
    PostDetailsMvp.Model providePostDetailsModel() {
        return new PostDetailsModel();
    }
}
