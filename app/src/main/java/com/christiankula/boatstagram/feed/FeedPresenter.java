package com.christiankula.boatstagram.feed;

import com.christiankula.boatstagram.feed.rest.BoatstragramService;
import com.christiankula.boatstagram.feed.rest.models.InstagramTagResult;
import com.christiankula.boatstagram.feed.rest.models.Post;
import com.christiankula.boatstagram.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class is responsible for "presenting" a FeedView. It handles business logic for requesting posts and displaying
 * them in the FeedView.
 * <br/>
 * <br/>
 * It also updates the "Model" according to the FeedView interactions. The "Model" is simply represented
 * by a {@code long} representing the last update timestamp and a list of {@link Post} representing the last updated
 * posts.
 */
public class FeedPresenter implements BasePresenter<FeedView> {
    private static final String TAG = FeedPresenter.class.getSimpleName();

    private static final long INTERVAL_BETWEEN_TWO_UPDATES = TimeUnit.SECONDS.toMillis(30);

    private FeedView feedView;

    private BoatstragramService boatstragramService;

    private List<Post> lastUpdatedPosts;

    private long lastUpdateTimestamp;

    @Inject
    public FeedPresenter(BoatstragramService boatstragramService) {
        this.boatstragramService = boatstragramService;
    }

    @Override
    public void attachView(FeedView view) {
        lastUpdateTimestamp = Long.MAX_VALUE;

        this.feedView = view;
    }

    @Override
    public void detachView() {
        this.feedView = null;
    }

    /**
     * Updates Boats posts if last update was more than 30 sec ago
     * <br/>
     * <br/>
     * Tied to {@link #feedView}'s {@code onResume} method.
     */
    void onResume() {
        if (System.currentTimeMillis() > lastUpdateTimestamp + INTERVAL_BETWEEN_TWO_UPDATES) {
            updateBoatPosts();
        }
    }

    /**
     * Called when the SwipeRefreshLayout of {@link #feedView} is swiped
     */
    void onRefresh() {
        updateBoatPosts();
    }

    /**
     * Updates boat posts by requesting the last posts about boats from Instagram
     */
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

    /**
     * Called when the user clicks the 'Download all pictures' menu item.
     * <br/>
     * <br/>
     * Starts a {@link com.christiankula.boatstagram.feed.download.DownloadPicturesService}.
     * If storage permission was not granted, requests storage permission first.
     */
    void onDownloadMenuItemClick() {
        if (feedView.hasStoragePermission()) {
            startDownloadingPictures();
        } else {
            feedView.requestStoragePermission();
        }
    }

    /**
     * Called when the user has granted storage permission
     */
    void onStoragePermissionGranted() {
        startDownloadingPictures();
    }

    /**
     * Called when the user has denied storage permission
     */
    void onStoragePermissionDenied() {

    }

    /**
     * Starts a {@link com.christiankula.boatstagram.feed.download.DownloadPicturesService} only if the list of last
     * updated posts is not empty
     */
    private void startDownloadingPictures() {
        if (lastUpdatedPosts == null || lastUpdatedPosts.isEmpty()) {
            feedView.displayNoPictureToDownloadToast();
        } else {
            feedView.startDownloadingPictures(lastUpdatedPosts);
        }
    }
}
