package com.onedaydent.onedaydent.Common;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.onedaydent.onedaydent.Main.Domain.PanoramaVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
*   EasyDent연동
*
* */
public class MssqlConnectorEasydent {

    private Connection conn;
    private Activity activity;
    private String dbIp = "106.245.224.219";    // 뒤에 :1443 은 입력하지 않는다.
    private String dbName = "maru";
    private String dbUser = "hcm";
    private String dbUserPass = "a$*ayy#1957^11bjku$";
    private String TAG = "TAG";

    public MssqlConnectorEasydent(Activity activity) {
        this.activity = activity;
    }

    private boolean tryConnect(boolean showMessage) {
        try {
            if (conn != null && !conn.isClosed())
                return true;
            MssqlConnection connClass = new MssqlConnection();
            conn = connClass.getConnection(dbUser, dbUserPass, dbName, dbIp);
            if (conn == null) {
                if (showMessage)
//                    showToast(connClass.getLastErrMsg());
                    showToast("네트워크가 불안정하여 서비스를 이용하실수 없습니다.");
                return false;
            } else {
                if (conn.isClosed()) {
                    Log.d("TAG", "tryConnect: fail");
                    return false;
                } else {
                    Log.d("TAG", "tryConnect: suc");
                    return true;
                }
            }
        } catch (SQLException e) {
            if (showMessage)
                showToast(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<PanoramaVO> getPanoramaList(String id){
        ArrayList<PanoramaVO> list = new ArrayList<PanoramaVO>();
        try{
            String query = String.format("SELECT chart, C_Date, C_IPName FROM imginfo WHERE chart = ? AND C_Type = 1 ORDER BY C_Date DESC;");
            if( tryConnect(true) ){
                PreparedStatement preStmt = conn.prepareStatement(query);
                preStmt.setString(1, id);
                ResultSet rs = preStmt.executeQuery();
                while(rs.next()){
                    PanoramaVO item = new PanoramaVO();
                    item.setChart(rs.getString("chart").trim());
                    item.setC_Date(rs.getTimestamp("C_Date"));
                    item.setC_IPName(rs.getString("C_IPName"));
                    list.add(item);
                }
            }
//            else
//                showToast( "네트워크가 불안정하여 서비스를 이용하실수 없습니다.");
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }


    private void showToast(String text){
        Toast.makeText(activity.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}
