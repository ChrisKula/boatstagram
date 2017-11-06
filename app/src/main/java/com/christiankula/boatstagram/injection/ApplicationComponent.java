package com.christiankula.boatstagram.injection;

import com.christiankula.boatstagram.feed.FeedActivity;
import com.christiankula.boatstagram.post.details.PostDetailsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        FeedModule.class,
        PostDetailsModule.class})
public interface ApplicationComponent {

    void inject(FeedActivity target);

    void inject(PostDetailsActivity target);
}
