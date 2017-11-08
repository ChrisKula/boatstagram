package com.christiankula.boatstagram.post.details;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.christiankula.boatstagram.BoatstagramApplication;
import com.christiankula.boatstagram.R;
import com.christiankula.boatstagram.feed.rest.models.Post;
import com.christiankula.boatstagram.utils.ViewUtils;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

/**
 * Its primary goal is to display clicked post's details such as the picture in full-res, the full caption, the date and
 * likes count.
 */
public class PostDetailsActivity extends AppCompatActivity implements PostDetailsView {

    public static final String POST_EXTRA = "POST";

    @BindView(R.id.tb_post_detail)
    Toolbar toolbar;

    @BindView(R.id.cl_post_detail_info_panel)
    ConstraintLayout clInfoPanel;

    @BindView(R.id.iv_post_detail_picture)
    ImageView ivPicture;

    @BindView(R.id.tv_post_detail_caption)
    TextView tvCaption;

    @BindView(R.id.tv_post_detail_likes_count)
    TextView tvLikesCount;

    @BindView(R.id.tv_post_detail_date)
    TextView tvDate;

    private PostDetailsPresenter postDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        ((BoatstagramApplication) getApplication()).getComponent().inject(this);
        ButterKnife.bind(this);

        setupActionBar();

        postDetailsPresenter.attachView(this);
        postDetailsPresenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        postDetailsPresenter.detachView();

        super.onDestroy();
    }

    @Inject
    @Override
    public void setPresenter(PostDetailsPresenter presenter) {
        this.postDetailsPresenter = presenter;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void setToolbarVisibility(boolean isVisible) {
        if (isVisible) {
            ViewUtils.fadeIn(toolbar);
        } else {
            ViewUtils.fadeOut(toolbar);
        }
    }

    @Override
    public void setPostInfoVisibility(boolean isVisible) {
        if (isVisible) {
            ViewUtils.fadeIn(clInfoPanel);
        } else {
            ViewUtils.fadeOut(clInfoPanel);
        }
    }

    @Override
    public Post getPostFromIntent() {
        return Parcels.unwrap(getIntent().getParcelableExtra(POST_EXTRA));
    }

    @Override
    public void setPicture(String pictureUrl) {
        Picasso.with(this)
                .load(pictureUrl)
                .placeholder(R.drawable.ic_boat_blue_24dp)
                .into(ivPicture);
    }

    @Override
    public void setPicture(File pictureFile) {
        Picasso.with(this)
                .load(pictureFile)
                .placeholder(R.drawable.ic_boat_blue_24dp)
                .into(ivPicture);
    }

    @Override
    public void setCaption(String caption) {
        tvCaption.setText(caption);
    }

    @Override
    public void setDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.date_time_format), Locale.getDefault());

        tvDate.setText(sdf.format(date));
    }

    @Override
    public void setLikesCount(int likesCount) {
        tvLikesCount.setText(Integer.toString(likesCount));
    }

    @OnTouch(R.id.cl_post_detail_root_view)
    boolean onTouch() {
        postDetailsPresenter.onRootViewTouch();
        return false;
    }

    /**
     * Sets upp the Toolbar to be used as the ActionBar
     */
    private void setupActionBar() {
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
