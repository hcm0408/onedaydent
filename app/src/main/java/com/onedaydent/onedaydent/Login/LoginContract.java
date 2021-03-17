package com.onedaydent.onedaydent.Login;

import com.onedaydent.onedaydent.BasePresenter;
import com.onedaydent.onedaydent.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void viewInit();
        void btnLogin_textChange(String str);
        void showAuthTimer(boolean bool);
        void changeAuthTimer(String str);
        void changeAuthTimerTextColor(boolean bool);
        void changeTimerValid(boolean bool);
    }

    interface Presenter extends BasePresenter{
        void generateAuthKey(String number);
        boolean checkAutkKey(String key);
        boolean checkPhoneNumber(String number);
        void close();
        void moveMain();
        void getMemberInfo(String number);
        boolean isMember(String number);
        void loginAuthexpiration();
        String numberPatternChange(String number);
    }
}
