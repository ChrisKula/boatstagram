package com.christiankula.boatstagram.feed;

import com.christiankula.boatstagram.feed.rest.models.Post;
import com.christiankula.boatstagram.mvp.BaseView;

import java.util.List;

/**
 * Interface responsible for every interactions between FeedActivity and FeedPresenter
 */
public interface FeedView extends BaseView<FeedPresenter> {

    /**
     * Displays the given List of Post
     */
    void displayPosts(List<Post> posts);


    /**
     * Returns whether the user has granted storage permission
     */
    boolean hasStoragePermission();


    /**
     * Requests storage permission
     */
    void requestStoragePermission();

    /**
     * Start a {@link com.christiankula.boatstagram.feed.download.DownloadPicturesService} that will download posts'
     * full-res picture
     *
     * @param posts a List of Post to download pictures of
     */
    void startDownloadingPictures(List<Post> posts);

    /**
     * Display a Toast indicating that there's no picture to download
     */
    void displayNoPictureToDownloadToast();

    /**
     * Sets whether the spinning progress bar should be displayed
     */
    void setRefreshing(boolean enable);
}
