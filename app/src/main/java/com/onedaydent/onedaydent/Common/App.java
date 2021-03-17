package com.onedaydent.onedaydent.Common;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NotoSans-Light.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NotoSans-Bold.ttf"));
    }
}
