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

    /**
     * Save the given Post's picture in full-res to device's storage
     *
     * @return true if the picture has been successfully downloaded, false otherwise
     */
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

    /**
     * Returns whether the given Post's full-res picture already exists on the device's storage
     */
    public static boolean postPictureExistsOnDisk(Post post) {
        return post != null && getPostPictureFile(post).exists();
    }

    /**
     * Returns a File representing the given Posts's full-res picture
     */
    public static File getPostPictureFile(Post post) {
        return new File(BOASTAGRAM_DOWNLOAD_FOLDER, post.getId() + ".jpg");
    }
}
