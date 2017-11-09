package com.christiankula.boatstagram.feed;

import com.christiankula.boatstagram.feed.rest.models.Post;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * This class is responsible for getting the data provided by {@link #feedModel} and reacts accordingly on the {@link
 * #feedView}
 */
public class FeedPresenter implements FeedMvp.Presenter, FeedModel.onPostsUpdateListener {

    private static final String TAG = FeedPresenter.class.getSimpleName();

    private static final long INTERVAL_BETWEEN_TWO_UPDATES = TimeUnit.SECONDS.toMillis(30);

    private FeedMvp.View feedView;
    private FeedMvp.Model feedModel;

    @Inject
    public FeedPresenter(FeedMvp.Model feedModel) {
        this.feedModel = feedModel;
    }

    @Override
    public void attachView(FeedMvp.View view) {
        this.feedView = view;
        feedModel.resetLastUpdateTimestamp();
    }

    @Override
    public void detachView() {
        this.feedView = null;
    }

    @Override
    public void onResume() {
        if (System.currentTimeMillis() > feedModel.getLastUpdateTimestamp() + INTERVAL_BETWEEN_TWO_UPDATES) {
            updatePosts();
        }
    }

    @Override
    public void onRefresh() {
        updatePosts();
    }

    @Override
    public void onDownloadMenuItemClick() {
        if (feedView.hasStoragePermission()) {
            startDownloadingPictures();
        } else {
            feedView.requestStoragePermission();
        }
    }

    @Override
    public void onStoragePermissionGranted() {
        startDownloadingPictures();
    }

    @Override
    public void onStoragePermissionDenied() {

    }

    @Override
    public void onUpdate(List<Post> posts) {
        feedView.setRefreshing(false);

        //TODO Do something if posts is empty
        feedView.displayPosts(posts);
    }

    /**
     * Starts a {@link com.christiankula.boatstagram.feed.download.DownloadPicturesService} only if the list of last
     * updated posts is not empty
     */
    private void startDownloadingPictures() {
        List<Post> lastUpdatedPosts = feedModel.getLastUpdatedPosts();

        if (lastUpdatedPosts == null || lastUpdatedPosts.isEmpty()) {
            feedView.displayNoPictureToDownloadToast();
        } else {
            feedView.startDownloadingPictures(lastUpdatedPosts);
        }
    }

    /**
     * Updates boat posts by requesting the last posts about boats from Instagram
     */
    private void updatePosts() {
        feedView.setRefreshing(true);

        feedModel.updatePosts(this);
    }
}
