package com.onedaydent.onedaydent.Notification;

import com.onedaydent.onedaydent.BasePresenter;
import com.onedaydent.onedaydent.BaseView;

public interface NotificationContract {

    interface View extends BaseView<Presenter> {
        void viewInit();
    }

    interface Presenter extends BasePresenter{
        void finish();
    }
}
