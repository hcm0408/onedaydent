package com.onedaydent.onedaydent.Setting;

import com.onedaydent.onedaydent.BasePresenter;
import com.onedaydent.onedaydent.BaseView;

public interface SettingContract {

    interface View extends BaseView<Presenter> {
        void viewInit();
    }

    interface Presenter extends BasePresenter{
        void finish();
        void settingPush(boolean bool, String id);
        void settingEvent(boolean bool, String id);
    }
}
