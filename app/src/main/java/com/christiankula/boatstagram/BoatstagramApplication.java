package com.christiankula.boatstagram;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.christiankula.boatstagram.injection.ApplicationModule;
import com.christiankula.boatstagram.injection.BoatstagramComponent;
import com.christiankula.boatstagram.injection.DaggerApplicationComponent;
import com.christiankula.boatstagram.injection.FeedModule;
import com.christiankula.boatstagram.injection.NetworkModule;
import com.christiankula.boatstagram.injection.PostDetailsModule;

public class BoatstagramApplication extends Application {

    private final BoatstagramComponent component = createComponent();

    @Override
    public void onCreate() {
        super.onCreate();

        initNotificationChannels();
    }

    /**
     * Builds the application component with all relevant modules in order to set up Dagger for dependency injection
     */
    protected BoatstagramComponent createComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule())
                .feedModule(new FeedModule())
                .postDetailsModule(new PostDetailsModule())
                .build();
    }

    public BoatstagramComponent getComponent() {
        return component;
    }

    /**
     * In Android O and onward, notifications channels must be declared in order for notifications to be displayed to
     * the user. This method initializes app's notification channels.
     */
    private void initNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    getString(R.string.notification_channel_id_download_pictures),
                    getString(R.string.notification_channel_name_download_pictures),
                    NotificationManager.IMPORTANCE_LOW);

            mChannel.setDescription(getString(R.string.notification_channel_description_download_pictures));

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }
        }
    }
}
