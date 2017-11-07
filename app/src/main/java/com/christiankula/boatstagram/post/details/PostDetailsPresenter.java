package com.christiankula.boatstagram.post.details;

import com.christiankula.boatstagram.feed.rest.models.Post;
import com.christiankula.boatstagram.mvp.BasePresenter;
import com.christiankula.boatstagram.utils.FileUtils;

import java.util.Date;

import javax.inject.Inject;

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


    void onRootViewTouch() {
        isInfoPanelVisible = !isInfoPanelVisible;

        postDetailsView.setToolbarVisibility(isInfoPanelVisible);
        postDetailsView.setPostInfoVisibility(isInfoPanelVisible);
    }
}
