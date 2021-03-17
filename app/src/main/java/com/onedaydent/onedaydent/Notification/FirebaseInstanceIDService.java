package com.onedaydent.onedaydent.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.onedaydent.onedaydent.Common.DB;
import com.onedaydent.onedaydent.Common.DBHelper;
import com.onedaydent.onedaydent.Notification.Domain.NotificationVO;
import com.onedaydent.onedaydent.R;
import com.onedaydent.onedaydent.Welcome.WelcomeActivity;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import me.leolin.shortcutbadger.ShortcutBadger;

public class FirebaseInstanceIDService extends FirebaseMessagingService {

    private String TAG = "TAG";
    private DBHelper helper;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        sendNotification(remoteMessage);
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Intent intent = new Intent("saveToken");
        intent.putExtra("token", s.toString());
        Log.d(TAG, "onNewToken: " + s.toString());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        String message = remoteMessage.getData().get("message");
        Gson gson = new Gson();
        NotificationVO vo = gson.fromJson(message, NotificationVO.class);

        if(DB.getInstance().getDbHelper() != null){
            DB.getInstance().getDbHelper().insertNotification(vo);
        }else{
            this.helper = new DBHelper(getApplication(), "DB", null, 1);
            helper.insertNotification(vo);
        }

        PendingIntent pendingIntent = null;
        NotificationCompat.Builder notificationBuilder = null;
        Intent intent = null;

        SharedPreferences pref = getSharedPreferences("badge", MODE_PRIVATE);
        int badgeCount = pref.getInt("badgeCount", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("badgeCount", badgeCount+1);
        editor.commit();
        badgeCount = pref.getInt("badgeCount", 0);

        Intent badge = new Intent("badgeCount");
        badge.putExtra("badge", badgeCount);
        LocalBroadcastManager.getInstance(this).sendBroadcast(badge);

        /* FCM 메세지 수신시 뱃지 기능 */
        Intent badgeIntent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        badgeIntent.putExtra("badge_count", badgeCount);
        badgeIntent.putExtra("badge_count_package_name", "com.onedaydent.onedaydent");
        badgeIntent.putExtra("badge_count_class_name", "com.onedaydent.onedaydent.Welcome.WelcomeActivity");
        getApplicationContext().sendBroadcast(badgeIntent);

        /**
         * 오레오 버전부터는 Notification Channel이 없으면 푸시가 생성되지 않는 현상이 있습니다.
         * **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channel = getString(R.string.default_notification_channel_id);
            String channel_nm = getString(R.string.default_notification_channel_name);

            NotificationManager notichannel = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channelMessage = new NotificationChannel(channel, channel_nm,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channelMessage.setDescription("채널에 대한 설명.");
            channelMessage.enableLights(true);
            channelMessage.enableVibration(true);
            channelMessage.setShowBadge(true);
            channelMessage.setVibrationPattern(new long[]{100, 200, 100, 200});
            notichannel.createNotificationChannel(channelMessage);

            notificationBuilder =
                    new NotificationCompat.Builder(this, channel)
                            .setSmallIcon(R.mipmap.icon)
                            .setContentTitle(vo.getTitle())
                            .setContentText(vo.getContent())
                            .setChannelId(channel)
                            .setAutoCancel(true)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(vo.getContent()))
                            .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        } else {
            notificationBuilder =
                    new NotificationCompat.Builder(this, "")
                            .setSmallIcon(R.mipmap.icon)
                            .setContentTitle(vo.getTitle())
                            .setContentText(vo.getContent())
                            .setAutoCancel(true)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(vo.getContent()))
                            .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        }

        // 이미지 경로가 존재할시
        if(!vo.getImgURL().equals("")){
            try {
                URL url = new URL(vo.getImgURL());
                //아이콘 처리
                Bitmap bigIcon = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                notificationBuilder.setLargeIcon(bigIcon);
                notificationBuilder.setStyle(
                        new NotificationCompat.BigPictureStyle()
                                .bigPicture(bigIcon)
                                .setBigContentTitle(vo.getTitle())
                                .setSummaryText(vo.getContent()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(!vo.getURL().equals("")){
            intent = new Intent(this, UrlLoadActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("URL", vo.getURL());
            intent.putExtras(bundle);
        }else{
            intent = new Intent(this, WelcomeActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(9999, notificationBuilder.build());
    }

    public static String getLauncherClassName(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                return resolveInfo.activityInfo.name;
            }
        }
        return null;
    }
}
