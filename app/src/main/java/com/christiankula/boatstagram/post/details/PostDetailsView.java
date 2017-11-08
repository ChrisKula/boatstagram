package com.christiankula.boatstagram.post.details;

import com.christiankula.boatstagram.feed.rest.models.Post;
import com.christiankula.boatstagram.mvp.BaseView;

import java.io.File;
import java.util.Date;

interface PostDetailsView extends BaseView<PostDetailsPresenter> {

    /**
     * Returns the {@link Post} received through Intent
     */
    Post getPostFromIntent();

    /**
     * Sets the post's picture
     *
     * @param pictureUrl the URL of the picture to display
     */
    void setPicture(String pictureUrl);


    /**
     * Sets the post's picture
     *
     * @param pictureFile the File representing the picture to display
     */
    void setPicture(File pictureFile);

    /**
     * Sets the post's caption
     */
    void setCaption(String caption);

    /**
     * Sets the post's date
     */
    void setDate(Date timestamp);

    /**
     * Sets the post's likes count
     */
    void setLikesCount(int likesCount);

    /**
     * Sets toolbar visibility
     */
    void setToolbarVisibility(boolean isVisible);

    /**
     * Sets post's info panel visibility
     */
    void setPostInfoVisibility(boolean isVisible);
}
