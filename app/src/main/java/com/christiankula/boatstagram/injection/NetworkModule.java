package com.christiankula.boatstagram.injection;

import android.content.Context;

import com.christiankula.boatstagram.R;
import com.christiankula.boatstagram.feed.rest.BoatstragramService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Dagger module in charge of providing network related dependencies
 */
@Module
public class NetworkModule {
    private final static String INSTAGRAM_BASE_URL_NAME = "instagramBaseUrl";

    /**
     * Provides the base URL of Instagram
     */
    @Provides
    @Singleton
    @Named(INSTAGRAM_BASE_URL_NAME)
    String provideInstagramBaseUrl(Context context) {
        return context.getString(R.string.instagram_base_url);
    }

    /**
     * Provides an implementation of the API endpoints defined by {@link BoatstragramService}
     *
     * @param baseUrl Instagram base URL
     */
    @Provides
    @Singleton
    BoatstragramService provideBoatstragramService(@Named(INSTAGRAM_BASE_URL_NAME) String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(BoatstragramService.class);
    }
}
