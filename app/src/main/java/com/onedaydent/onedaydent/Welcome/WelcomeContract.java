package com.onedaydent.onedaydent.Welcome;

import com.onedaydent.onedaydent.BasePresenter;
import com.onedaydent.onedaydent.BaseView;

public interface WelcomeContract {

    interface View extends BaseView<Presenter>{
        void viewInit();
        void setAdapter();
        void addBottomDots(int currentPage);
    }

    interface Presenter extends BasePresenter{
        boolean hasPermissions();
        void requestNecessaryPermissions();
        void saveToken(String token);
        void stopBroadCast();
        void saveLoginLog();
        boolean isFirst();
        void createDatabase();
    }
}
