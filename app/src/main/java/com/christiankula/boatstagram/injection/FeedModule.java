package com.christiankula.boatstagram.injection;

import com.christiankula.boatstagram.feed.FeedModel;
import com.christiankula.boatstagram.feed.FeedMvp;
import com.christiankula.boatstagram.feed.FeedPresenter;
import com.christiankula.boatstagram.feed.rest.BoatstragramService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module in charge of providing dependencies for everything related to the Feed of Posts
 */
@Module
public class FeedModule {

    /**
     * Provides a {@link FeedMvp.Presenter}
     *
     * @param feedModel corresponding {@link FeedMvp.Model}
     */
    @Provides
    @Singleton
    FeedMvp.Presenter provideFeedPresenter(FeedMvp.Model feedModel) {
        return new FeedPresenter(feedModel);
    }

    /**
     * Provides a {@link FeedMvp.Model}
     *
     * @param boatstragramService REST client
     */
    @Provides
    @Singleton
    FeedMvp.Model provideFeedModel(BoatstragramService boatstragramService) {
        return new FeedModel(boatstragramService);
    }
}
