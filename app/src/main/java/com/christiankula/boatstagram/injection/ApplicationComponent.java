package com.christiankula.boatstagram.injection;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        FeedModule.class,
        PostDetailsModule.class})
interface ApplicationComponent  extends BoastagramComponent{

}
