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

public class BoatTagFeedPresenter {
    private static final String TAG = BoatTagFeedPresenter.class.getSimpleName();

    private static final long INTERVAL_BETWEEN_TWO_UPDATES = TimeUnit.SECONDS.toMillis(30);

    private BoatTagFeedView boatTagFeedView;

    private BoatstragramService boatstragramService;

    private List<Post> lastUpdatedPosts;

    private long lastUpdateTimestamp = Long.MAX_VALUE;

    @Inject
    public BoatTagFeedPresenter(BoatstragramService boatstragramService) {
        this.boatstragramService = boatstragramService;
    }

    void onResume() {
        if (System.currentTimeMillis() > lastUpdateTimestamp + INTERVAL_BETWEEN_TWO_UPDATES) {
            updateBoatPosts();
        }
    }

    void attachView(BoatTagFeedView view) {
        this.boatTagFeedView = view;
    }

    void detachView() {
        this.boatTagFeedView = null;
    }

    private void updateBoatPosts() {
        boatTagFeedView.setRefreshing(true);

        boatstragramService.getBoatTagResult().enqueue(new Callback<InstagramTagResult>() {
            @Override
            public void onResponse(Call<InstagramTagResult> call, Response<InstagramTagResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    InstagramTagResult body = response.body();

                    lastUpdatedPosts = body.getTag().getMedia().getPosts();

                    if (lastUpdatedPosts == null) {
                        lastUpdatedPosts = new ArrayList<>();
                    }

                    boatTagFeedView.displayPosts(lastUpdatedPosts);
                } else {
                    //TODO error handling
                }

                boatTagFeedView.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<InstagramTagResult> call, Throwable t) {
                t.printStackTrace();

                //TODO error handling
                boatTagFeedView.setRefreshing(false);
            }
        });

        lastUpdateTimestamp = System.currentTimeMillis();
    }

    void onDownloadMenuItemClick() {
        if (boatTagFeedView.hasStoragePermission()) {
            startDownloadingPictures();
        } else {
            boatTagFeedView.requestStoragePermission();
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
            boatTagFeedView.displayNoPictureToDownloadToast();
        } else {
            boatTagFeedView.startDownloadingPictures(lastUpdatedPosts);
        }
    }
}
