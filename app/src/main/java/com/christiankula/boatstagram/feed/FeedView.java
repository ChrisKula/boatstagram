package com.christiankula.boatstagram.feed;

import com.christiankula.boatstagram.feed.rest.models.Post;

import java.util.List;

public interface FeedView {

    void displayPosts(List<Post> posts);

    boolean hasStoragePermission();

    void requestStoragePermission();

    void startDownloadingPictures(List<Post> posts);

    void displayNoPictureToDownloadToast();

    void setRefreshing(boolean enable);
}
