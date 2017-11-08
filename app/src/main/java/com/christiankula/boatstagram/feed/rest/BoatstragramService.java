package com.christiankula.boatstagram.feed.rest;

import com.christiankula.boatstagram.feed.rest.models.InstagramTagResult;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Service interface defining Instagram's API endpoints
 */
public interface BoatstragramService {

    /**
     * Returns a {@link Call} of {@link InstagramTagResult} for the tag "boat"
     */
    @GET("explore/tags/boat/?__a=1")
    Call<InstagramTagResult> getBoatTagResult();
}
