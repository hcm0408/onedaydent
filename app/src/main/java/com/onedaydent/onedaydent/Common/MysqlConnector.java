package com.onedaydent.onedaydent.Common;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.onedaydent.onedaydent.Login.Domain.MemberVO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MysqlConnector extends AsyncTask<String, String, String> {

    private URL URL;
    private StringBuffer result = new StringBuffer();
    private HttpURLConnection conn = null;
    private InputStreamReader in = null;
    private BufferedReader br = null;
    private String TAG = "TAG";

    @Override
    protected String doInBackground(String... strings) {
        String result = "";
        switch(strings[0]){
            case "saveMemberInfo":
                saveMemberInfo(strings);
                break;
            case "saveToken":
                saveToken(strings);
                break;
            case "updateAlert":
                updateAlert(strings);
                break;
            case "app_login":
                loginLog(strings);
                break;
            case "getDoctor":
                result = getDoctor(strings);
                break;
            case "getNotice":
                result = getNotice(strings);
        }
        return result;
    }

    private String saveMemberInfo(String... strings){
        try{
            Gson gson = new Gson();
            MemberVO vo = new MemberVO();
            vo.setLM_patientNo(strings[1]);
            vo.setLM_name(strings[2]);
            vo.setLM_chartID(strings[3]);
            vo.setLM_token(strings[4]);
            if(strings[5].equals("true")){
                vo.setLM_push(1);
            }else{
                vo.setLM_push(0);
            }
            if(strings[6].equals("true")){
                vo.setLM_event(1);
            }else{
                vo.setLM_event(0);
            }
            String json = gson.toJson(vo);
            String url = "http://58.151.55.205:8080/crm/saveMemberInfo";
            URL = new URL(url);
            conn = (HttpURLConnection)URL.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.flush();
            int res = conn.getResponseCode();
            if(res == 200){
                return  "true";
            }else{
                return "false";
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally{
            try{
                conn.disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "false";
    }

    private String saveToken(String... strings){
        try{
            Gson gson = new Gson();
            MemberVO vo = new MemberVO();
            vo.setLM_patientNo(strings[1]);
            vo.setLM_token(strings[2]);
            String json = gson.toJson(vo);
            String url = "http://http://www.onedaysister.com:8080/crm/saveToken";
            URL = new URL(url);
            conn = (HttpURLConnection)URL.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.flush();
            int res = conn.getResponseCode();
            if(res == 200){
                return  "true";
            }else{
                return "false";
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally{
            try{
                conn.disconnect();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "false";
    }

    private String updateAlert(String... strings){
        try{
            Gson gson = new Gson();
            MemberVO vo = new MemberVO();
            if(strings[1].equals("push")){
                vo.setLM_name("LM_push");
                vo.setLM_push(Integer.parseInt(strings[2]));
            }else{
                vo.setLM_name("LM_event");
                vo.setLM_event(Integer.parseInt(strings[2]));
            }
            vo.setLM_patientNo(strings[3]);
            String json = gson.toJson(vo);
            String url = "http://58.151.55.205:8080/crm/updateAlert";
            URL = new URL(url);
            conn = (HttpURLConnection)URL.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.flush();
            int res = conn.getResponseCode();
            if(res == 200){
                conn.disconnect();
                return  "true";
            }else{
                conn.disconnect();
                return "false";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "false";
    }

    private String loginLog(String... strings){
        try{
            URL url = new URL("http://117.52.172.59:8080/onedayapp/app_loginlog.do");
            conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setUseCaches(false);
            int res = conn.getResponseCode();
            if(res == 200){
                conn.disconnect();
                return  "true";
            }else{
                conn.disconnect();
                return "false";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "false";
    }

    private String getDoctor(String... strings){
        try{
            URL url = new URL("http://58.151.55.205:8080/crm/appgetdoctors");
            conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setUseCaches(false);
            int res = conn.getResponseCode();
            if(res == 200){
                in = new InputStreamReader(conn.getInputStream());
                br = new BufferedReader(in);
                String str = "";
                while((str = br.readLine()) != null){
                    result.append(str);
                }
                conn.disconnect();
                return result.toString();
            }else{
                conn.disconnect();
                return "false";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "false";
    }

    private String getNotice(String... strings){
        try{
            URL url = new URL("http://58.151.55.205:8080/crm/getnotice");
            conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setUseCaches(false);
            int res = conn.getResponseCode();
            Log.d(TAG, "getDoctor: " + res);
            if(res == 200){
                in = new InputStreamReader(conn.getInputStream());
                br = new BufferedReader(in);
                String str = "";
                while((str = br.readLine()) != null){
                    result.append(str);
                }
                conn.disconnect();
                return result.toString();
            }else{
                conn.disconnect();
                return "false";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "false";
    }

    @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
}
