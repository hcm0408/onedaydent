package com.onedaydent.onedaydent;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.onedaydent.onedaydent.Login.Domain.MemberVO;

import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest extends Activity {

    private URL URL;
    private StringBuffer result = new StringBuffer();
    private HttpURLConnection conn = null;
    private InputStreamReader in = null;
    private BufferedReader br = null;
    private String TAG = "TAG";

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void saveMemberInfo(){

        String input = "";
            input = "{\"data\" : {\"message\" : {\"title\" : \"" + "제목" + "\",\"type\" : \"type\"}}, \"to\" : \"" + "토큰" + "\"}";
            System.out.print(input);
    }


}