package com.christiankula.boatstagram.feed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.christiankula.boatstagram.BaseApplication;
import com.christiankula.boatstagram.R;
import com.christiankula.boatstagram.feed.rest.BoatstragramService;
import com.christiankula.boatstagram.feed.rest.models.InstagramTagResult;
import com.christiankula.boatstagram.feed.rest.models.Post;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoatTagFeedActivity extends AppCompatActivity {

    @Inject
    BoatstragramService s;

    @BindView(R.id.rv_regular_posts)
    RecyclerView rvRegularPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boat_tag_feed);

        ButterKnife.bind(this);
        ((BaseApplication) getApplication()).getApplicationComponent().inject(this);

        final PostAdapter postAdapter = new PostAdapter(new ArrayList<Post>());

        rvRegularPosts.setLayoutManager(new LinearLayoutManager(this));
        rvRegularPosts.setAdapter(postAdapter);

        Call<InstagramTagResult> callBoat = s.getBoatTagResult();

        callBoat.enqueue(new Callback<InstagramTagResult>() {
            @Override
            public void onResponse(Call<InstagramTagResult> call, Response<InstagramTagResult> response) {
                if (response.isSuccessful()) {
                    InstagramTagResult iter = response.body();

                    postAdapter.setData(iter.getTag().getMedia().getPosts());
                }
            }

            @Override
            public void onFailure(Call<InstagramTagResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
