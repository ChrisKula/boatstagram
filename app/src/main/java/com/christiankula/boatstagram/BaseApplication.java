package com.christiankula.boatstagram;

import android.app.Application;

import com.christiankula.boatstagram.injection.ApplicationComponent;
import com.christiankula.boatstagram.injection.ApplicationModule;
import com.christiankula.boatstagram.injection.DaggerApplicationComponent;
import com.christiankula.boatstagram.injection.NetworkModule;

public class BaseApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = initDagger();
    }

    private ApplicationComponent initDagger() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
