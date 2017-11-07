package com.christiankula.boatstagram.post.details;

import com.christiankula.boatstagram.feed.rest.models.Post;
import com.christiankula.boatstagram.mvp.BaseView;

import java.io.File;
import java.util.Date;

interface PostDetailsView extends BaseView<PostDetailsPresenter> {

    Post getPostFromIntent();

    void setPicture(String pictureUrl);

    void setPicture(File pictureFile);

    void setCaption(String caption);

    void setDate(Date timestamp);

    void setLikesCount(int likesCount);

    void setToolbarVisibility(boolean isVisible);

    void setPostInfoVisibility(boolean isVisible);
}
