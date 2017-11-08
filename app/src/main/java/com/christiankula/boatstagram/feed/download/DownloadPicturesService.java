package com.christiankula.boatstagram.feed.download;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.christiankula.boatstagram.R;
import com.christiankula.boatstagram.feed.rest.models.Post;
import com.christiankula.boatstagram.utils.FileUtils;

import org.parceler.Parcels;

import java.util.List;


/**
 * Service in charge of downloading pictures to the device's storage of a list of {@link Post} passed in a Intent.
 * <br/>
 * <br/>
 * It also displays a notification keeping track of the download progress.
 */
public class DownloadPicturesService extends IntentService {

    private static final String TAG = DownloadPicturesService.class.getSimpleName();

    public static final int DOWNLOADING_PICTURES_NOTIFICATION_ID = 31;

    public static final String POSTS_EXTRA = "POSTS";

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

                if (FileUtils.savePostPictureToDisk(post)) {
                    successfullyDownloadedPictures++;
                }

                showProgressNotification(i + 1, posts.size());
            }

            showDownloadCompleteNotification(successfullyDownloadedPictures, posts.size());
        }
    }

    /**
     * Displays download progress in a notification
     *
     * @param progress currently downloading picture
     * @param max      total of pictures to download
     */
    private void showProgressNotification(int progress, int max) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getBaseContext(), getString(R.string.notification_channel_id_download_pictures))
                .setContentTitle(getString(R.string.notification_title_pictures_download_in_progress, max))
                .setContentText(getString(R.string.notification_text_pictures_download_in_progress, progress, max))
                .setSmallIcon(R.drawable.ic_boat_blue_24dp)
                .setOngoing(true)
                .setProgress(max, progress, false);

        notificationManager.notify(DOWNLOADING_PICTURES_NOTIFICATION_ID, notificationBuilder.build());
    }

    /**
     * Displays a download complete notification with a short rapport of how many pictures have been successfully
     * downloaded out of the total of pictures
     *
     * @param successfullyDownloadedPictures currently downloading picture
     * @param picturesCount                  total of pictures to download
     */
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
