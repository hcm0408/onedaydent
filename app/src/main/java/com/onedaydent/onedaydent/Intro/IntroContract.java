package com.onedaydent.onedaydent.Intro;

import com.onedaydent.onedaydent.BasePresenter;
import com.onedaydent.onedaydent.BaseView;

public interface IntroContract {

    interface View extends BaseView<Presenter>{
        void viewInit();
    }

    interface Presenter extends BasePresenter{
        void openPhoneInquryMenu();
        void openLogin();
        boolean isLogin();
        void moveMain();
    }
}
