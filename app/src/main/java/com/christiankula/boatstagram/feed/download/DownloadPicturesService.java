package com.christiankula.boatstagram.feed.download;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.christiankula.boatstagram.R;
import com.christiankula.boatstagram.feed.rest.models.Post;

import org.apache.commons.io.FileUtils;
import org.parceler.Parcels;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class DownloadPicturesService extends IntentService {

    private static final String TAG = DownloadPicturesService.class.getSimpleName();

    public static final int DOWNLOADING_PICTURES_NOTIFICATION_ID = 31;

    public static final String POSTS_EXTRA = "POSTS";

    private static final long CONNECTION_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(2);
    private static final long READ_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(2);

    private static final File BOASTAGRAM_DOWNLOAD_FOLDER = new File(Environment.getExternalStorageDirectory(),
            "Boastagram/Downloads/");

    private NotificationManager notificationManager;

    public DownloadPicturesService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            List<Post> posts = Parcels.unwrap(intent.getParcelableExtra(POSTS_EXTRA));

            int successfullyDownloadedPictures = 0;

            for (int i = 0; i < posts.size(); i++) {
                Post post = posts.get(i);

                File pictureFile = new File(BOASTAGRAM_DOWNLOAD_FOLDER, post.getId() + ".jpg");

                try {
                    FileUtils.copyURLToFile(new URL(post.getDisplaySrc()), pictureFile, (int) CONNECTION_TIMEOUT_MS, (int) READ_TIMEOUT_MS);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                } finally {
                    showProgressNotification(i + 1, posts.size());
                }

                successfullyDownloadedPictures++;
            }

            showDownloadCompleteNotification(successfullyDownloadedPictures, posts.size());
        }
    }

    private void showProgressNotification(int progress, int max) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getBaseContext(), getString(R.string.notification_channel_id_download_pictures))
                .setContentTitle(getString(R.string.notification_title_pictures_download_in_progress, max))
                .setContentText(getString(R.string.notification_text_pictures_download_in_progress, progress, max))
                .setSmallIcon(R.drawable.ic_boat_blue_24dp)
                .setOngoing(true)
                .setProgress(max, progress, false);

        notificationManager.notify(DOWNLOADING_PICTURES_NOTIFICATION_ID, notificationBuilder.build());
    }

    private void showDownloadCompleteNotification(int successfullyDownloadedPictures, int picturesCount) {
        notificationManager.cancel(DOWNLOADING_PICTURES_NOTIFICATION_ID);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getBaseContext(), getString(R.string.notification_channel_id_download_pictures))
                .setContentTitle(getString(R.string.notification_title_pictures_download_completed))
                .setContentText(getString(R.string.notification_text_pictures_download_completed, successfullyDownloadedPictures, picturesCount))
                .setOngoing(false)
                .setSmallIcon(R.drawable.ic_boat_blue_24dp);

        notificationManager.notify(DOWNLOADING_PICTURES_NOTIFICATION_ID, notificationBuilder.build());
    }
}