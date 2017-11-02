package com.christiankula.boatstagram.injection;

import com.christiankula.boatstagram.feed.BoatsFeedActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    void inject(BoatsFeedActivity target);
}
