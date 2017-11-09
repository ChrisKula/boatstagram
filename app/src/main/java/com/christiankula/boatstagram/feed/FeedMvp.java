package com.christiankula.boatstagram.feed;

import com.christiankula.boatstagram.feed.rest.models.Post;
import com.christiankula.boatstagram.mvp.BasePresenter;
import com.christiankula.boatstagram.mvp.BaseView;

import java.util.List;


/**
 * Root interface for the Model, View and Presenter interfaces for the Feed
 */
public interface FeedMvp {

    interface Model {

        /**
         * Updates posts
         *
         * @param listener listener that will react to posts' update
         */
        void updatePosts(onPostsUpdateListener listener);

        /**
         * Returns last updated posts
         */
        List<Post> getLastUpdatedPosts();

        /**
         * Returns last update timestamp. Returns -1 if no update has been done
         */
        long getLastUpdateTimestamp();

        /**
         * Resets last update timestamp
         */
        void resetLastUpdateTimestamp();

        /**
         * Listener that reacts to posts' update
         */
        interface onPostsUpdateListener {
            /**
             * Invoked when posts have been updated
             *
             * @param posts updated posts
             */
            void onUpdate(List<Post> posts);
        }
    }

    interface View extends BaseView<FeedMvp.Presenter> {

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

    interface Presenter extends BasePresenter<FeedMvp.View> {

        /**
         * Called when {@link FeedMvp.View} is refreshed
         */
        void onRefresh();

        /**
         * Called when {@link FeedMvp.View} is resumed
         */
        void onResume();

        /**
         * Called when the user clicks the 'Download all pictures' menu item.
         */
        void onDownloadMenuItemClick();

        /**
         * Called when the user has granted storage permission
         */
        void onStoragePermissionGranted();

        /**
         * Called when the user has denied storage permission
         */
        void onStoragePermissionDenied();
    }
}
