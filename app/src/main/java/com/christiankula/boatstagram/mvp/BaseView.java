package com.christiankula.boatstagram.mvp;

/**
 * This interface represents a base view contract that should be extended by an interface implemented by a concrete View
 * for the corresponding {@link BasePresenter}
 */
public interface BaseView<T extends BasePresenter> {

    /**
     * Sets this {@code BaseView}'s {@code BasePresenter}
     */
    void setPresenter(T presenter);
}
