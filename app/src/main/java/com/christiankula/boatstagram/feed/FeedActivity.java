package com.christiankula.boatstagram.feed;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.christiankula.boatstagram.BoatstagramApplication;
import com.christiankula.boatstagram.R;
import com.christiankula.boatstagram.feed.download.DownloadPicturesService;
import com.christiankula.boatstagram.feed.rest.models.Post;
import com.christiankula.boatstagram.utils.ViewUtils;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Application's main activity. Its primary goal is to display a scrollable list of Post with the tag "boat". The posts
 * are fetched from Instagram.
 * <br/>
 * <br/>
 * The user can download all posts' picture in full-res to the device storage by clicking on a menu item.
 */
public class FeedActivity extends AppCompatActivity implements FeedMvp.View {

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 3105;

    @BindView(R.id.srl_root_view)
    SwipeRefreshLayout srlRootView;

    @BindView(R.id.cl_container)
    ConstraintLayout clContainer;

    @BindView(R.id.rv_regular_posts)
    RecyclerView rvRegularPosts;

    private FeedMvp.Presenter feedPresenter;

    private PostAdapter postsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boat_tag_feed);

        ((BoatstagramApplication) getApplication()).getComponent().inject(this);
        ButterKnife.bind(this);

        initSwipeRefreshLayout();
        initRecyclerViewPosts();

        feedPresenter.attachView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        feedPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        feedPresenter.detachView();

        super.onDestroy();
    }

    @Inject
    @Override
    public void setPresenter(FeedMvp.Presenter presenter) {
        this.feedPresenter = presenter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_boat_tag_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_download_all_pictures:
                feedPresenter.onDownloadMenuItemClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void displayPosts(List<Post> posts) {
        postsAdapter.setData(posts);

        ViewUtils.fadeIn(rvRegularPosts);
    }

    @Override
    public void displayNoPictureToDownloadToast() {
        Toast.makeText(this, R.string.toast_no_picture_to_download, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startDownloadingPictures(List<Post> posts) {
        Intent intent = new Intent(this, DownloadPicturesService.class);

        intent.putExtra(DownloadPicturesService.POSTS_EXTRA, Parcels.wrap(posts));
        startService(intent);
    }

    //TODO Move to separate class
    @Override
    public boolean hasStoragePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                new AlertDialog.Builder(this)
                        .setTitle(R.string.alert_dialog_title_storage_permission_needed)
                        .setMessage(R.string.alert_dialog_message_storage_permission_needed)
                        .setPositiveButton(R.string.alert_dialog_positive_button_storage_permission_needed, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showRequestStoragePermissionDialog();
                            }
                        })
                        .show();
            } else {
                showRequestStoragePermissionDialog();
            }
        }
    }

    @Override
    public void setRefreshing(boolean enable) {
        srlRootView.setRefreshing(enable);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    feedPresenter.onStoragePermissionGranted();
                } else {
                    feedPresenter.onStoragePermissionDenied();
                }
            }
        }
    }

    /**
     * Initializes the SwipeRefreshLayout colors and onRefreshListener
     */
    private void initSwipeRefreshLayout() {
        srlRootView.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent));

        srlRootView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                feedPresenter.onRefresh();
            }
        });
    }

    /**
     * Initializes the RecyclerView displaying posts with an empty list and a vertical LayoutManager
     */
    private void initRecyclerViewPosts() {
        postsAdapter = new PostAdapter(new ArrayList<Post>());

        rvRegularPosts.setLayoutManager(new LinearLayoutManager(this));
        rvRegularPosts.setAdapter(postsAdapter);
    }

    private void showRequestStoragePermissionDialog() {
        ActivityCompat.requestPermissions(FeedActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
    }
}
