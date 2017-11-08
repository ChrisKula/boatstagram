package com.christiankula.boatstagram.injection;

import com.christiankula.boatstagram.feed.FeedPresenter;
import com.christiankula.boatstagram.feed.rest.BoatstragramService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module in charge of providing dependencies for {@link com.christiankula.boatstagram.feed.FeedActivity}
 */
@Module
public class FeedModule {

    /**
     * Provides a {@link FeedPresenter}
     *
     * @param boatstragramService a BoatstagramService for REST calls
     */
    @Provides
    @Singleton
    FeedPresenter provideFeedPresenter(BoatstragramService boatstragramService) {
        return new FeedPresenter(boatstragramService);
    }
}
