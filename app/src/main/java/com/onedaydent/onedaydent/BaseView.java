package com.onedaydent.onedaydent;

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
