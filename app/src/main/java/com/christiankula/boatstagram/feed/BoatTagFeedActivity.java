package com.christiankula.boatstagram.feed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.christiankula.boatstagram.BaseApplication;
import com.christiankula.boatstagram.R;
import com.christiankula.boatstagram.feed.rest.BoatstragramService;
import com.christiankula.boatstagram.feed.rest.models.Post;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BoatTagFeedActivity extends AppCompatActivity implements BoatTagFeedView {


    @BindView(R.id.rv_regular_posts)
    RecyclerView rvRegularPosts;

    @Inject
    BoatTagFeedPresenter boatTagFeedPresenter;

    private PostAdapter postsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boat_tag_feed);

        ((BaseApplication) getApplication()).getApplicationComponent().inject(this);
        ButterKnife.bind(this);

        initRecyclerViewPosts();

        boatTagFeedPresenter.attachView(this);
    }

    private void initRecyclerViewPosts() {
        postsAdapter = new PostAdapter(new ArrayList<Post>());

        rvRegularPosts.setLayoutManager(new LinearLayoutManager(this));
        rvRegularPosts.setAdapter(postsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boatTagFeedPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        boatTagFeedPresenter.detachView();

        super.onDestroy();
    }

    @Override
    public void displayPosts(List<Post> posts) {
        postsAdapter.setData(posts);
    }
}