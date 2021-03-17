package com.onedaydent.onedaydent.Common;

import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class SmsAuth extends AsyncTask<String, String , String> {

    private String auth;
    private String number;

    public SmsAuth(String number){
        this.number = number;
    }

    public void sendAuthkey(){
        Random random = new Random();
        String auth = "";
        for(int i = 0;i < 4;i++){
            auth += random.nextInt(10);
        }
        this.auth = auth;
        execute("");
    }

    public String getAuth() {
        return auth;
    }

    public void authExpiration(){
        this.auth = "";
    }

    @Override
    protected String doInBackground(String... strings) {
        URL url;
        HttpURLConnection conn = null;
        try{
            url = new URL("http://117.52.172.59:8080/onedayapp/sms.do?msg=" + auth + "&number=" + number);
            conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setUseCaches(false);
            if(conn.getResponseCode() != 200){
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                conn.disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
