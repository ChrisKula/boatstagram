package com.christiankula.boatstagram;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.christiankula.boatstagram.injection.ApplicationComponent;
import com.christiankula.boatstagram.injection.ApplicationModule;
import com.christiankula.boatstagram.injection.BoatTagFeedModule;
import com.christiankula.boatstagram.injection.DaggerApplicationComponent;
import com.christiankula.boatstagram.injection.NetworkModule;
import com.christiankula.boatstagram.injection.PostDetailModule;

public class BaseApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = initDagger();

        initNotificationChannels();
    }

    private ApplicationComponent initDagger() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule())
                .boatTagFeedModule(new BoatTagFeedModule())
                .postDetailModule(new PostDetailModule())
                .build();
    }

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

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
