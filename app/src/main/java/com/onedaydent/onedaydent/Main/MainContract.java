package com.onedaydent.onedaydent.Main;

import com.onedaydent.onedaydent.BasePresenter;
import com.onedaydent.onedaydent.BaseView;
import com.onedaydent.onedaydent.Common.DBHelper;
import com.onedaydent.onedaydent.Main.Domain.PaymentVO;

import java.util.ArrayList;

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void viewInit();
        void showBadge(int bool);
        void setBadge(int count);
        void closeDrawerLayout();
    }

    interface Presenter extends BasePresenter{
        boolean isLogin();
        void getData();
        void setText(ArrayList<PaymentVO> items);
        void logout();
        void openFcm();
        void openSetting();
        void phoneCall();
        void openKakao();
        void openFaq();
        void openTimetable();
        void openLocation();
        void openDocInfo();
        void openNotice();
        void openPanorama();
        void openYoutube();
        void openNaver();
        void openInsta();
        void openFacebook();
        void openToothpaste();
        void resume();
        DBHelper getHelper();
    }
}
