package com.christiankula.boatstagram.post.details;

import com.christiankula.boatstagram.feed.rest.models.Post;

import javax.inject.Inject;

/**
 * This class represents data displayed by a {@link PostDetailsMvp.Presenter} in a {@link PostDetailsMvp.View}.
 * <br/>
 * <br/>
 * Conceptually, it holds the current displayed Post and whether the UI overlay
 */
public class PostDetailsModel implements PostDetailsMvp.Model {

    private Post currentDisplayedPost;

    private boolean isUiOverlayVisible;

    @Inject
    public PostDetailsModel() {

    }

    @Override
    public void setUiOverlayVisibility(boolean visible) {
        isUiOverlayVisible = visible;
    }

    @Override
    public boolean isUiOverlayVisible() {
        return isUiOverlayVisible;
    }

    @Override
    public Post getCurrentDisplayedPost() {
        return currentDisplayedPost;
    }

    @Override
    public void setCurrentDisplayedPost(Post currentDisplayedPost) {
        this.currentDisplayedPost = currentDisplayedPost;
    }
}
