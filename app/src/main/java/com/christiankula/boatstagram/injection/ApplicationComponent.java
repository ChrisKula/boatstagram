package com.christiankula.boatstagram.injection;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Application-wide Dagger component
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        FeedModule.class,
        PostDetailsModule.class})
interface ApplicationComponent extends BoatstagramComponent {

}
