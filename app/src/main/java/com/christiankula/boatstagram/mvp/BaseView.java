package com.christiankula.boatstagram.mvp;

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
