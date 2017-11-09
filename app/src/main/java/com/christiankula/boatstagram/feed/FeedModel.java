package com.christiankula.boatstagram.feed;

import com.christiankula.boatstagram.feed.rest.BoatstragramService;
import com.christiankula.boatstagram.feed.rest.models.InstagramTagResult;
import com.christiankula.boatstagram.feed.rest.models.Post;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class represents data displayed by a {@link FeedMvp.Presenter} in a {@link FeedMvp.View}.
 * <br/>
 * <br/>
 * Conceptually, it holds a list of last updated posts and last update timestamp. It is also in charge of fetching posts
 * from Instagram
 */
public class FeedModel implements FeedMvp.Model {

    private final BoatstragramService boatstragramService;

    private List<Post> lastUpdatedPosts;

    private long lastUpdateTimestamp;

    @Inject
    public FeedModel(BoatstragramService boatstragramService) {
        this.boatstragramService = boatstragramService;
    }

    @Override
    public void updatePosts(final OnPostsUpdateListener listener) {
        boatstragramService.getBoatTagResult().enqueue(new Callback<InstagramTagResult>() {
            @Override
            public void onResponse(Call<InstagramTagResult> call, Response<InstagramTagResult> response) {
                List<Post> posts = new ArrayList<>();

                if (response.isSuccessful() && response.body() != null) {
                    InstagramTagResult body = response.body();

                    if (body.getTag().getMedia().getPosts() != null) {
                        posts = body.getTag().getMedia().getPosts();
                    }
                }

                listener.onUpdate(posts);
                lastUpdatedPosts = posts;
            }

            @Override
            public void onFailure(Call<InstagramTagResult> call, Throwable t) {
                t.printStackTrace();
                listener.onUpdate(new ArrayList<Post>());
                lastUpdatedPosts = new ArrayList<>();
            }
        });

        lastUpdateTimestamp = System.currentTimeMillis();
    }

    @Override
    public List<Post> getLastUpdatedPosts() {
        return lastUpdatedPosts;
    }

    @Override
    public long getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    @Override
    public void resetLastUpdateTimestamp() {
        lastUpdateTimestamp = -1;
    }
}
