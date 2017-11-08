package com.christiankula.boatstagram.post.details;

import com.christiankula.boatstagram.feed.rest.models.Post;
import com.christiankula.boatstagram.mvp.BasePresenter;
import com.christiankula.boatstagram.utils.FileUtils;

import java.util.Date;

import javax.inject.Inject;

/**
 * This class is responsible for "presenting" a PostDetailsView. It handles business logic for displaying post details
 * <br/>
 * <br/>
 * It also updates the "Model" according to the PostDetailsView interactions. The "Model" is simply represented
 * by a {@link Post} representing the displayed Post and a {@code boolean} indicating whether the toolbar and the info
 * panel overlay are visible
 */
public class PostDetailsPresenter implements BasePresenter<PostDetailsView> {

    private PostDetailsView postDetailsView;

    private Post currentDisplayedPost;

    private boolean isInfoPanelVisible;

    @Inject
    public PostDetailsPresenter() {
    }

    @Override
    public void attachView(PostDetailsView view) {
        this.postDetailsView = view;
        isInfoPanelVisible = true;
    }

    @Override
    public void detachView() {
        this.postDetailsView = null;
    }


    /**
     * Display post details and picture in full-res. First checks if full-res pictures has been previously and loads it
     * instead of downloading it again if it exists
     * <br/>
     * <br/>
     * Tied to {@link #postDetailsView}'s {@code onResume} method.
     */
    void onCreate() {
        currentDisplayedPost = postDetailsView.getPostFromIntent();

        if (FileUtils.postPictureExistsOnDisk(currentDisplayedPost)) {
            postDetailsView.setPicture(FileUtils.getPostPictureFile(currentDisplayedPost));
        } else {
            postDetailsView.setPicture(currentDisplayedPost.getDisplaySrc());
        }

        postDetailsView.setCaption(currentDisplayedPost.getCaption());
        postDetailsView.setDate(new Date(currentDisplayedPost.getDate() * 1000));
        postDetailsView.setLikesCount(currentDisplayedPost.getLikes().getCount());
    }


    /**
     * Toggles toolbar and info panel overlay visibility when user touches the {@link #postDetailsView}'s root view
     */
    void onRootViewTouch() {
        isInfoPanelVisible = !isInfoPanelVisible;

        postDetailsView.setToolbarVisibility(isInfoPanelVisible);
        postDetailsView.setPostInfoVisibility(isInfoPanelVisible);
    }
}
