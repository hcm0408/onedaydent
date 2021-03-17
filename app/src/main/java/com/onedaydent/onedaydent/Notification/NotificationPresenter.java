package com.onedaydent.onedaydent.Notification;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import static com.google.common.base.Preconditions.checkNotNull;

public class NotificationPresenter implements NotificationContract.Presenter  {

    private NotificationContract.View mView;
    private Activity activity;
    private String TAG = "TAG";

    public NotificationPresenter(@NonNull NotificationContract.View view, @NonNull Activity activity, FragmentManager fragmentManager) {
        this.mView = checkNotNull(view);
        this.activity = checkNotNull(activity);
        mView.setPresenter(this);
    }

    @Override
    public void finish() {
        activity.finish();
    }

    @Override
    public void start() {
        mView.viewInit();
    }

}
