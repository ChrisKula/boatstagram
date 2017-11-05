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

public class BoatTagFeedPresenter {
    private static final String TAG = BoatTagFeedPresenter.class.getSimpleName();

    private BoatTagFeedView boatTagFeedView;

    private BoatstragramService boatstragramService;

    private List<Post> lastFetchedPosts;

    @Inject
    public BoatTagFeedPresenter(BoatstragramService boatstragramService) {
        this.boatstragramService = boatstragramService;
    }

    void onResume() {
        fetchBoatPosts();
    }

    void attachView(BoatTagFeedView view) {
        this.boatTagFeedView = view;
    }

    void detachView() {
        this.boatTagFeedView = null;
    }

    private void fetchBoatPosts() {
        boatstragramService.getBoatTagResult().enqueue(new Callback<InstagramTagResult>() {
            @Override
            public void onResponse(Call<InstagramTagResult> call, Response<InstagramTagResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    InstagramTagResult body = response.body();

                    lastFetchedPosts = body.getTag().getMedia().getPosts();

                    if (lastFetchedPosts == null) {
                        lastFetchedPosts = new ArrayList<>();
                    }

                    boatTagFeedView.displayPosts(lastFetchedPosts);
                } else {
                    //TODO error handling
                }
            }

            @Override
            public void onFailure(Call<InstagramTagResult> call, Throwable t) {
                t.printStackTrace();


                //TODO error handling
            }
        });
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

    private void startDownloadingPictures() {
        if (lastFetchedPosts == null || lastFetchedPosts.isEmpty()) {
            boatTagFeedView.displayNoPictureToDownloadToast();
        } else {
            boatTagFeedView.startDownloadingPictures(lastFetchedPosts);
        }
    }
}
