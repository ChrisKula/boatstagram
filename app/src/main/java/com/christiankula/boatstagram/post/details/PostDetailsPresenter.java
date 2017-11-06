package com.christiankula.boatstagram.post.details;

import com.christiankula.boatstagram.feed.rest.models.Post;

import java.util.Date;

public class PostDetailsPresenter {

    private PostDetailsView postDetailsView;

    private Post currentDisplayedPost;

    private boolean isInfoPanelVisible;

    void attachView(PostDetailsView view) {
        this.postDetailsView = view;
        isInfoPanelVisible = true;
    }

    void detachView() {
        this.postDetailsView = null;
    }

    void onCreate() {
        currentDisplayedPost = postDetailsView.getPostFromIntent();

        postDetailsView.setPicture(currentDisplayedPost.getDisplaySrc());
        postDetailsView.setCaption(currentDisplayedPost.getCaption());
        postDetailsView.setDate(new Date(currentDisplayedPost.getDate() * 1000));
        postDetailsView.setLikesCount(currentDisplayedPost.getLikes().getCount());
    }


    void onRootViewTouch() {
        isInfoPanelVisible = !isInfoPanelVisible;

        postDetailsView.setToolbarVisibility(isInfoPanelVisible);
        postDetailsView.setPostInfoVisibility(isInfoPanelVisible);
    }
}
