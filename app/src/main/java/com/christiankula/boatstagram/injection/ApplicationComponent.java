package com.christiankula.boatstagram.injection;

import com.christiankula.boatstagram.feed.BoatTagFeedActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    void inject(BoatTagFeedActivity target);
}
