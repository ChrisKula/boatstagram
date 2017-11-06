package com.christiankula.boatstagram.injection;

import com.christiankula.boatstagram.feed.FeedActivity;
import com.christiankula.boatstagram.post.details.PostDetailsActivity;

public interface BoastagramComponent {

    void inject(FeedActivity target);

    void inject(PostDetailsActivity target);
}
