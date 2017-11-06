package com.christiankula.boatstagram.feed;

import com.christiankula.boatstagram.feed.rest.BoatstragramService;
import com.christiankula.boatstagram.feed.rest.models.InstagramTagResult;
import com.christiankula.boatstagram.feed.rest.models.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedPresenter {
    private static final String TAG = FeedPresenter.class.getSimpleName();

    private static final long INTERVAL_BETWEEN_TWO_UPDATES = TimeUnit.SECONDS.toMillis(30);

    private FeedView feedView;

    private BoatstragramService boatstragramService;

    private List<Post> lastUpdatedPosts;

    private long lastUpdateTimestamp = Long.MAX_VALUE;

    @Inject
    public FeedPresenter(BoatstragramService boatstragramService) {
        this.boatstragramService = boatstragramService;
    }

    void onResume() {
        if (System.currentTimeMillis() > lastUpdateTimestamp + INTERVAL_BETWEEN_TWO_UPDATES) {
            updateBoatPosts();
        }
    }

    void attachView(FeedView view) {
        this.feedView = view;
    }

    void detachView() {
        this.feedView = null;
    }

    private void updateBoatPosts() {
        feedView.setRefreshing(true);

        boatstragramService.getBoatTagResult().enqueue(new Callback<InstagramTagResult>() {
            @Override
            public void onResponse(Call<InstagramTagResult> call, Response<InstagramTagResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    InstagramTagResult body = response.body();

                    lastUpdatedPosts = body.getTag().getMedia().getPosts();

                    if (lastUpdatedPosts == null) {
                        lastUpdatedPosts = new ArrayList<>();
                    }

                    feedView.displayPosts(lastUpdatedPosts);
                } else {
                    //TODO error handling
                }

                feedView.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<InstagramTagResult> call, Throwable t) {
                t.printStackTrace();

                //TODO error handling
                feedView.setRefreshing(false);
            }
        });

        lastUpdateTimestamp = System.currentTimeMillis();
    }

    void onDownloadMenuItemClick() {
        if (feedView.hasStoragePermission()) {
            startDownloadingPictures();
        } else {
            feedView.requestStoragePermission();
        }
    }

    void onStoragePermissionGranted() {
        startDownloadingPictures();
    }

    void onStoragePermissionDenied() {

    }

    void onRefresh() {
        updateBoatPosts();
    }

    private void startDownloadingPictures() {
        if (lastUpdatedPosts == null || lastUpdatedPosts.isEmpty()) {
            feedView.displayNoPictureToDownloadToast();
        } else {
            feedView.startDownloadingPictures(lastUpdatedPosts);
        }
    }
}
