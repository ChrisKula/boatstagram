package com.christiankula.boatstagram.post.details;

import com.christiankula.boatstagram.feed.rest.models.Post;

import java.util.Date;

interface PostDetailsView {

    Post getPostFromIntent();

    void setPicture(String pictureUrl);

    void setCaption(String caption);

    void setDate(Date timestamp);

    void setLikesCount(int likesCount);

    void setToolbarVisibility(boolean isVisible);

    void setPostInfoVisibility(boolean isVisible);
}
