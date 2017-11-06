package com.christiankula.boatstagram.injection;

import com.christiankula.boatstagram.feed.FeedPresenter;
import com.christiankula.boatstagram.feed.rest.BoatstragramService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FeedModule {

    @Provides
    @Singleton
    FeedPresenter provideBoatTagFeedPresenter(BoatstragramService boatstragramService) {
        return new FeedPresenter(boatstragramService);
    }
}
