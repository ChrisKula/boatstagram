package com.christiankula.boatstagram.utils;

import android.os.Environment;

import com.christiankula.boatstagram.feed.rest.models.Post;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class FileUtils {

    private static final long CONNECTION_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(2);
    private static final long READ_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(2);

    private static final File BOASTAGRAM_DOWNLOAD_FOLDER = new File(Environment.getExternalStorageDirectory(),
            "Boastagram/Downloads/");

    private FileUtils() {

    }

    public static boolean savePostPictureToDisk(Post post) {
        if (post != null) {
            try {
                File pictureFile = getPostPictureFile(post);

                org.apache.commons.io.FileUtils.copyURLToFile(new URL(post.getDisplaySrc()), pictureFile, (int) CONNECTION_TIMEOUT_MS, (int) READ_TIMEOUT_MS);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean postPictureExistsOnDisk(Post post) {
        return post != null && getPostPictureFile(post).exists();
    }

    public static File getPostPictureFile(Post post) {
        return new File(BOASTAGRAM_DOWNLOAD_FOLDER, post.getId() + ".jpg");
    }
}
