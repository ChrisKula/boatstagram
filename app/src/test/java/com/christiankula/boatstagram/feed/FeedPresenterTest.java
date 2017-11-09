package com.christiankula.boatstagram.feed;

import com.christiankula.boatstagram.BuildConfig;
import com.christiankula.boatstagram.feed.rest.models.Likes;
import com.christiankula.boatstagram.feed.rest.models.Post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class FeedPresenterTest {

    @Mock
    private FeedMvp.Model model;

    @Mock
    private FeedMvp.View view;

    private FeedMvp.Presenter presenter;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        presenter = new FeedPresenter(model);
        presenter.attachView(view);

        final List<Post> posts = new ArrayList<>();

        Likes likes = new Likes();
        likes.setCount(0);

        Post testingPost = new Post();
        testingPost.setCommentsDisabled(false);
        testingPost.setId("1642357460588894082");
        testingPost.setCaption("Why are sunsets so inspiring??? Love that colors \\ud83c\\udf04\\ud83d\\ude0d @igersmallorca #mallorca #sunset #pink #sky #sea #blue #goodnight #island #instagram #instagood #instadaily #instaday #love #smile #nature #mountains #happy #happiness #boat #sailor #photography #photooftheday #picoftheday #canon #sealover #longexposure #lights #inlove");
        testingPost.setDate(1510004287);
        testingPost.setLikes(likes);
        testingPost.setDisplaySrc("https://scontent-cdg2-1.cdninstagram.com/t51.2885-15/e35/23279428_127480974594040_5235408951633248256_n.jpg");

        posts.add(testingPost);
        posts.add(testingPost);
        posts.add(testingPost);
        posts.add(testingPost);
        posts.add(testingPost);

        Mockito.when(model.getLastUpdatedPosts()).thenReturn(posts);

        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((FeedMvp.Model.OnPostsUpdateListener) presenter).onUpdate(posts);
                return null;
            }
        }).when(model).updatePosts((FeedMvp.Model.OnPostsUpdateListener) presenter);
    }

    @Test
    public void Refreshing_Should_UpdatePosts() {
        presenter.onRefresh();

        Mockito.verify(view).setRefreshing(Mockito.eq(true));
        Mockito.verify(model, Mockito.times(1)).updatePosts((FeedMvp.Model.OnPostsUpdateListener) presenter);
        Mockito.verify(view).setRefreshing(Mockito.eq(false));
        Mockito.verify(view).displayPosts(Mockito.<Post>anyList());
    }

    @Test
    public void LastUpdateNow_On_Resume_Should_Not_UpdatePosts() {
        long nowTimestamp = System.currentTimeMillis();

        Mockito.when(model.getLastUpdateTimestamp()).thenReturn(nowTimestamp);

        presenter.onResume();

        Mockito.verify(view, Mockito.never()).setRefreshing(Mockito.anyBoolean());
        Mockito.verify(model, Mockito.never()).updatePosts((FeedMvp.Model.OnPostsUpdateListener) presenter);
        Mockito.verify(view, Mockito.never()).displayPosts(Mockito.<Post>anyList());
    }

    @Test
    public void LastUpdate45secAgo_On_Resume_Should_UpdatePosts() {
        long oldTimestamp = System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(45);

        Mockito.when(model.getLastUpdateTimestamp()).thenReturn(oldTimestamp);

        presenter.onResume();

        Mockito.verify(view).setRefreshing(Mockito.eq(true));
        Mockito.verify(model, Mockito.times(1)).updatePosts((FeedMvp.Model.OnPostsUpdateListener) presenter);
        Mockito.verify(view).setRefreshing(Mockito.eq(false));
        Mockito.verify(view).displayPosts(Mockito.<Post>anyList());
    }

    @Test
    public void OnDownloadMenuItemClick_With_Perm_Should_Start_Downloading_Pictures() {
        Mockito.when(view.hasStoragePermission()).thenReturn(true);

        presenter.onDownloadMenuItemClick();

        Mockito.verify(model, Mockito.times(1)).getLastUpdatedPosts();

        Mockito.verify(view, Mockito.times(1)).startDownloadingPictures(Mockito.<Post>anyList());
    }

    @Test
    public void onDownloadMenuItemClick_WithNoPosts_Should_DisplayErrorToast() {
        Mockito.when(view.hasStoragePermission()).thenReturn(true);

        Mockito.when(model.getLastUpdatedPosts()).thenReturn(null);

        presenter.onDownloadMenuItemClick();

        Mockito.verify(model, Mockito.times(1)).getLastUpdatedPosts();

        Mockito.verify(view, Mockito.times(1)).displayNoPictureToDownloadToast();
    }

    @Test
    public void OnDownloadMenuItemClick_WithoutPerm_Should_Request_Perm(){
        presenter.onDownloadMenuItemClick();

        Mockito.verify(view, Mockito.times(1)).requestStoragePermission();
    }

    @Test
    public void OnPermissionGranted_Should_Start_DownloadingPictures(){
        presenter.onStoragePermissionGranted();

        Mockito.verify(model, Mockito.times(1)).getLastUpdatedPosts();

        Mockito.verify(view, Mockito.times(1)).startDownloadingPictures(Mockito.<Post>anyList());
    }
}
