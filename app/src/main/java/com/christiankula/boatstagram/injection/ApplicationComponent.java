package com.christiankula.boatstagram.injection;

import com.christiankula.boatstagram.feed.BoatTagFeedActivity;
import com.christiankula.boatstagram.post.details.PostDetailsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        BoatTagFeedModule.class,
        PostDetailModule.class})
public interface ApplicationComponent {

    void inject(BoatTagFeedActivity target);

    void inject(PostDetailsActivity target);
}
