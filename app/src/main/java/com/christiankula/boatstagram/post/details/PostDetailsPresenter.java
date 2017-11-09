package com.christiankula.boatstagram.post.details;

import com.christiankula.boatstagram.feed.rest.models.Post;
import com.christiankula.boatstagram.utils.FileUtils;

import java.util.Date;

import javax.inject.Inject;

/**
 * This class is responsible for getting the data provided by {@link #postDetailsModel} and reacts accordingly on the
 * {@link #postDetailsView}
 */
public class PostDetailsPresenter implements PostDetailsMvp.Presenter {

    private PostDetailsMvp.View postDetailsView;
    private PostDetailsMvp.Model postDetailsModel;

    @Inject
    public PostDetailsPresenter(PostDetailsMvp.Model model) {
        this.postDetailsModel = model;
    }

    @Override
    public void attachView(PostDetailsMvp.View view) {
        this.postDetailsView = view;
        postDetailsModel.setUiOverlayVisibility(true);
        postDetailsModel.setCurrentDisplayedPost(postDetailsView.getPostFromIntent());
    }

    @Override
    public void detachView() {
        this.postDetailsView = null;
    }

    @Override
    public void onCreate() {
        Post post = postDetailsModel.getCurrentDisplayedPost();

        if (FileUtils.postPictureExistsOnDisk(post)) {
            postDetailsView.setPicture(FileUtils.getPostPictureFile(post));
        } else {
            postDetailsView.setPicture(post.getDisplaySrc());
        }

        postDetailsView.setCaption(post.getCaption());
        postDetailsView.setDate(new Date(post.getDate() * 1000));
        postDetailsView.setLikesCount(post.getLikes().getCount());

        postDetailsModel.setCurrentDisplayedPost(post);
    }

    @Override
    public void onRootViewTouch() {
        boolean isVisible = !postDetailsModel.isUiOverlayVisible();

        postDetailsView.setToolbarVisibility(isVisible);
        postDetailsView.setPostInfoVisibility(isVisible);

        postDetailsModel.setUiOverlayVisibility(isVisible);
    }
}
