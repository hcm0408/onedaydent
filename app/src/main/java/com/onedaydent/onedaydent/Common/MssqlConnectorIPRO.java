package com.onedaydent.onedaydent.Common;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.onedaydent.onedaydent.Login.Domain.MemberVO;
import com.onedaydent.onedaydent.Main.Domain.PaymentVO;
import com.onedaydent.onedaydent.Main.Domain.ReservationVO;
import com.onedaydent.onedaydent.Main.Domain.TreatDetailListVO;
import com.onedaydent.onedaydent.Main.Domain.TreatListVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
*   IPRO 연동
*
* */

public class MssqlConnectorIPRO {

    private Connection conn;
    private Activity activity;
    private final String dbIp = "106.245.224.218:51433";    // 뒤에 :1443 은 입력하지 않는다.
    private final String dbName = "dentop";
    private final String dbUser = "ps";
    private final String dbUserPass = "rlaqkq";

    private String TAG = "TAG";

    public MssqlConnectorIPRO(Activity activity) {
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

    /* 예약 리스트 받아오기 */
    public ArrayList<ReservationVO> getReservationList(String id){
        ArrayList<ReservationVO> list = new ArrayList<ReservationVO>();
        if(!id.equals("")){
            try{
                String query = String.format("SELECT a.MDate as reserv_Date, a.MTime as reserv_Time, a.TxName as reserv_TxName, a.MNext as reserv_Next FROM (SELECT * FROM(SELECT TOP 1 dbo.fn_gfnDateTypeTrans(AppDate, '-') as MDate,'Y' as MType,'다음 방문일' as TxName, AppTime as MTime, 1 as MNext FROM Apptime AS a WHERE id = ? AND a.Valid = 'Y' AND DATEDIFF(dd, getDate(), AppDate) >= 0 ORDER BY AppDate, AppTime ASC) b UNION ALL SELECT dbo.fn_gfnDateTypeTrans(a.C45, '-') as MDate, CASE WHEN SUBSTRING(b.C79, 1, 4) = '2000' THEN 'Y' ELSE 'N' END AS MType, c.C54 as TxName, '' as MTime, 2 as MNext FROM TX0124 AS a LEFT JOIN TX1686 AS b ON a.C34 = b.C34 AND a.C76 = 'Y' AND b.C14 = 'Y' AND a.C21 = ? AND a.C85 = '2000' LEFT JOIN TX3766 AS c ON a.C58 = c.C39 AND a.C23 = c.C44 WHERE a.C85 NOT IN (SELECT Code FROM EtcCode x WHERE x.SystemID = 'TX' AND x.AddId = '00000000' AND Desc1 <> 'Y') AND b.C79 IS NOT NULL AND c.C41 = 'Y' AND(c.C54 != '처방전' AND c.C54 != '임시치아' AND c.C54 != '기타' AND c.C54 != 'Exam && Preventive' AND c.C54 != 'Endo' AND c.C54 !=  'Perio' AND c.C54 !=  'Cosmetic' AND c.C54 != 'Implant' AND c.C54 != 'MISC' AND c.C54 != 'Operative'  AND c.C54 != '검진'  AND c.C54 != '응급내원'  AND c.C54 != 'Oral surgery')) a WHERE MType = 'Y' GROUP BY MNext, MDate, MTime, TxName ORDER BY MNext, a.MDate ASC;");
                if( tryConnect(true) ){
                    PreparedStatement preStmt = conn.prepareStatement(query);
                    preStmt.setString(1, id);
                    preStmt.setString(2, id);
                    ResultSet rs = preStmt.executeQuery();
                    while(rs.next()){
                        ReservationVO dto = new ReservationVO();
                        dto.setReserv_Date(rs.getString("reserv_Date"));
                        dto.setReserv_TxName(rs.getString("reserv_TxName"));
                        dto.setReserv_Time(rs.getString("reserv_Time"));
                        dto.setReserv_Next(rs.getString("reserv_Next"));
                        list.add(dto);
                    }
                }
//                else
//                    showToast( "네트워크가 불안정하여 서비스를 이용하실수 없습니다.");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<PaymentVO> getPaymentList(String id){
        ArrayList<PaymentVO> list = new ArrayList<PaymentVO>();
        if(!id.equals("")){
            try {
                String query = String.format("SELECT dbo.fn_gfnDateTypeTrans(PayDate, '-') as PayDate, payMisu, payCash, payCard, payOnline, payType FROM (SELECT TOP 1 a.receiptdate as PayDate, [dbo].[fn_patientMisu](a.id, '99999999') as payMisu, 0 as payCash, 0 as payCard, 0 as payOnline, 'A' as payType FROM dbo.Receipt AS a WHERE a.ID = ? AND a.Valid = 'Y' AND [dbo].[fn_patientMisu](a.id, '99999999') != 0 ORDER BY a.ReceiptDate DESC) as a" +
                        " UNION ALL SELECT dbo.fn_gfnDateTypeTrans(PayDate, '-') as PayDate, payMisu, payCash, payCard, payOnline, payType FROM (SELECT a.receiptdate as PayDate, 0 as PayMisu, a.cashprice as payCash,0 as payCard,0 as payOnline, 'B' as payType FROM dbo.Receipt AS a WHERE a.ID = ? AND a.Valid = 'Y' AND a.cashprice != 0) as b" +
                        " UNION ALL SELECT dbo.fn_gfnDateTypeTrans(PayDate, '-') as PayDate, payMisu, payCash, payCard, payOnline, payType FROM (SELECT a.receiptdate as PayDate, 0 as PayMisu, 0 as payCash, a.CardPrice as payCard, 0 as payOnline, 'C' as payType FROM dbo.Receipt AS a WHERE a.ID = ? AND a.Valid = 'Y' AND a.CardPrice != 0) as b" +
                        " UNION ALL SELECT dbo.fn_gfnDateTypeTrans(PayDate, '-') as PayDate, payMisu, payCash, payCard, payOnline, payType FROM (SELECT a.receiptdate as PayDate, 0 as PayMisu, 0 as payCash, 0 as payCard, a.OnlinePrice as payOnline, 'D' as payType FROM dbo.Receipt AS a WHERE a.ID = ? AND a.Valid = 'Y' AND a.OnlinePrice != 0 ) as b ORDER BY PayDate DESC");
                if (tryConnect(true)) {
                    PreparedStatement preStmt = conn.prepareStatement(query);
                    preStmt.setString(1, id);
                    preStmt.setString(2, id);
                    preStmt.setString(3, id);
                    preStmt.setString(4, id);
                    ResultSet rs = preStmt.executeQuery();
                    while (rs.next()) {
                        PaymentVO dto = new PaymentVO();
                        dto.setPayDate(rs.getString("payDate"));
                        dto.setPayCash(rs.getInt("payCash"));
                        dto.setPayCard(rs.getInt("payCard"));
                        dto.setPayOnline(rs.getInt("payOnline"));
                        dto.setPayMisu(rs.getInt("payMisu"));
                        dto.setPayType(rs.getString("payType"));
                        list.add(dto);
                    }
                }
//                else
//                    showToast("네트워크가 불안정하여 서비스를 이용하실수 없습니다.");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<TreatListVO> getTreatList(String id){
        ArrayList<TreatListVO> list = new ArrayList<TreatListVO>();
        if(!id.equals("")) {
            try {
                String query = String.format("SELECT TL_Listkey, TL_Masterkey, TL_TxName, TL_Doctor, TL_Date, TL_TxDetail, TL_Result FROM ( SELECT c.C39 as TL_Listkey, a.C34 as TL_Masterkey, c.C54 as TL_TxName, a.C23 as TL_Doctor, dbo.fn_gfnDateTypeTrans(a.C45, '-') as TL_Date, b.C11 as TL_TxDetail, CASE WHEN b.C11 = '2주 체크' OR  b.C11 = '보철 장착(인레이)' OR  b.C11 = '보철 장착 (크라운)' OR  b.C11 = '사랑니 발치' OR  b.C11 = '레진충전' OR  b.C11 = '마무리' OR  b.C11 = '마무리 체크' OR  b.C11 = '검진/스케일링' OR  b.C11 = '치주처치' OR  b.C11 = '치근발치' OR  b.C11 = '단순발치' OR  b.C11 = '수술발치' OR b.C11 = '검진' OR b.C11 = '3단계 보험 임플란트' OR b.C11 LIKE '%%틀니완성%%' OR b.C11 = '신경치료 마무리' OR b.C11 = '치아 보강' OR  b.C11 = '임플란트 제거 간단' OR  b.C11 = '임플란트 제거 복잡' OR  b.C11 = '기존 보철물 제거' OR  b.C11 = 'screw 제거' OR  b.C11 = '하루완성 신경치료' THEN 1 ELSE 0 END as TL_Result, row_number() over(partition by C39, C54 ORDER BY a.C34 DESC, C39, C54, C45) as ccount FROM  TX0124 AS a LEFT JOIN  TX1686 AS b ON a.C34 = b.C34 AND a.C76 = 'Y' AND b.C14 = 'Y' AND a.C21 = ? AND a.C85 = '2000' LEFT JOIN  TX3766 AS c ON a.C58 = c.C39 AND a.C23 = c.C44 WHERE  a.C85 NOT IN (SELECT Code FROM EtcCode x WHERE x.SystemID = 'TX' AND x.AddId = '00000000' AND Desc1 <> 'Y') AND  b.C79 IS NOT NULL AND  c.C41 = 'Y' AND  (c.C54 != '처방전' AND c.C54 != '임시치아' AND c.C54 != '기타' AND c.C54 != '구강검사' AND c.C54 != 'Exam && Preventive' AND c.C54 != 'Endo' AND c.C54 != 'Perio' AND c.C54 != 'Cosmetic' AND c.C54 != 'Implant' AND c.C54 != 'MISC' AND c.C54 != 'Operative' AND c.C54 != '검진' AND c.C54 != '응급내원' AND c.C54 != 'Oral surgery') ) a WHERE ccount = 1 ORDER BY TL_Result, TL_Date DESC;");
                if (tryConnect(true)) {
                    PreparedStatement preStmt = conn.prepareStatement(query);
                    preStmt.setString(1, id);
                    ResultSet rs = preStmt.executeQuery();
                    while (rs.next()) {
                        TreatListVO vo = new TreatListVO();
                        vo.setTL_Listkey(rs.getString("TL_Listkey"));
                        vo.setTL_Masterkey(rs.getString("TL_Masterkey"));
                        vo.setTL_TxName(rs.getString("TL_TxName"));
                        vo.setTL_Doctor(rs.getString("TL_Doctor"));
                        vo.setTL_TxDetail(rs.getString("TL_TxDetail"));
                        vo.setTL_Result(rs.getString("TL_Result"));
                        vo.setTL_Date(rs.getString("TL_Date"));
                        list.add(vo);
                    }
                }
//                else
//                    showToast("네트워크가 불안정하여 서비스를 이용하실수 없습니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<TreatDetailListVO> getTreatDetailList(TreatListVO vo){
        ArrayList<TreatDetailListVO> list = new ArrayList<TreatDetailListVO>();
        try{
            String query = String.format("SELECT C39 as TDL_Listkey, C77 as TDL_Detailkey, C54 as TDL_TxName, C44 as TDL_Doctor, C85 as TDL_Order FROM TX3766 where C77 = ? AND C44 = ? AND C41 = 'Y' ORDER BY C85 DESC;");
            if( tryConnect(true) ){
                PreparedStatement preStmt = conn.prepareStatement(query);
                preStmt.setString(1, vo.getTL_Listkey());
                preStmt.setString(2, vo.getTL_Doctor());
                ResultSet rs = preStmt.executeQuery();
                while(rs.next()){
                    TreatDetailListVO item = new TreatDetailListVO();
                    item.setTDL_Listkey(rs.getString("TDL_Listkey"));
                    item.setTDL_Detailkey(rs.getString("TDL_Detailkey"));
                    item.setTDL_TxName(rs.getString("TDL_TxName"));
                    item.setTDL_Order(rs.getInt("TDL_Order"));
                    item.setTDL_Date("");
                    item.setTDL_Doctor("");
                    item.setTDL_Depth(1);
                    item.setTDL_Result(0);
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

    public ArrayList<TreatDetailListVO> getMyTreatDetailList(TreatListVO vo, String id){
        ArrayList<TreatDetailListVO> list = new ArrayList<TreatDetailListVO>();
        try{
            String query = String.format("SELECT a.C37 as TDL_Listkey, a.C58 as TDL_Detailkey, b.C11 as TDL_TxName, dbo.fn_gfnDateTypeTrans(a.C45, '-') as TDL_Date, (SELECT Name FROM Doctor WHERE DoctorID = a.C23) as TDL_Doctor, b.C47 as TDL_Depth FROM TX0124 AS a LEFT JOIN TX1686 AS b ON a.C34 = b.C34 AND a.C76 = 'Y' AND b.C14 = 'Y' AND a.C21 = ? AND a.C85 = '2000' WHERE a.C58 = ? AND a.C21 = ? AND a.C76 = 'Y' ORDER BY a.C79 DESC;");
            if( tryConnect(true) ){
                PreparedStatement preStmt = conn.prepareStatement(query);
                preStmt.setString(1, id);
                preStmt.setString(2, vo.getTL_Listkey());
                preStmt.setString(3, id);
                ResultSet rs = preStmt.executeQuery();
                while(rs.next()){
                    TreatDetailListVO item = new TreatDetailListVO();
                    item.setTDL_Listkey(rs.getString("TDL_Listkey"));
                    item.setTDL_Detailkey(rs.getString("TDL_Detailkey"));
                    item.setTDL_Date(rs.getString("TDL_Date"));
                    item.setTDL_Doctor(rs.getString("TDL_Doctor"));
                    item.setTDL_TxName(rs.getString("TDL_TxName"));
                    item.setTDL_Order(0);
                    item.setTDL_Result(1);
                    item.setTDL_Depth(rs.getInt("TDL_Depth"));
                    list.add(item);
                }
            }
            Log.d(TAG, "getMyTreatDetailList: " + list.toString());
//            else
//                showToast( "네트워크가 불안정하여 서비스를 이용하실수 없습니다.");
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public MemberVO getIsMember(String number){
        MemberVO item = new MemberVO();
        try{
            String query = String.format("SELECT ID, ChartID, Name FROM Patient WHERE CellPhone = ? AND Valid = 'Y';");
            if( tryConnect(true) ){
                PreparedStatement preStmt = conn.prepareStatement(query);
                preStmt.setString(1, number);
                ResultSet rs = preStmt.executeQuery();
                while(rs.next()){
                    item.setLM_patientNo(rs.getString("ID"));
                    item.setLM_chartID(rs.getString("ChartID"));
                    item.setLM_name(rs.getString("Name"));
                }
            }
//            else
//                showToast( "네트워크가 불안정하여 서비스를 이용하실수 없습니다.");
        }catch(Exception e){
            e.printStackTrace();
        }
        return item;
    }

    private void showToast(String text){
        Toast.makeText(activity.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}
