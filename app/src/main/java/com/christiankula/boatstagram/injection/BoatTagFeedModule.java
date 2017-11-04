package com.christiankula.boatstagram.injection;

import com.christiankula.boatstagram.feed.BoatTagFeedPresenter;
import com.christiankula.boatstagram.feed.rest.BoatstragramService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BoatTagFeedModule {

    @Provides
    @Singleton
    BoatTagFeedPresenter provideBoatTagFeedPresenter(BoatstragramService boatstragramService) {
        return new BoatTagFeedPresenter(boatstragramService);
    }
}
