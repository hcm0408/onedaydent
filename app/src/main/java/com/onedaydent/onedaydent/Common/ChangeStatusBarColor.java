package com.onedaydent.onedaydent.Common;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class ChangeStatusBarColor {

    public ChangeStatusBarColor(Activity activity, int color){
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(color);
    }
}
