package com.onedaydent.onedaydent.Common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.onedaydent.onedaydent.Notification.Domain.NotificationVO;
import com.onedaydent.onedaydent.Main.Domain.PaymentVO;
import com.onedaydent.onedaydent.Main.Domain.ReservationVO;
import com.onedaydent.onedaydent.Main.Domain.TreatListVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DBHelper extends SQLiteOpenHelper {

    public static SQLiteDatabase mDB;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mDB = this.getWritableDatabase();
        onCreate(mDB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + ReservationHelper.getTbName());
        db.execSQL("DROP TABLE IF EXISTS " + PaymentHelper.getTbName());
        db.execSQL("DROP TABLE IF EXISTS " + TreatListHelper.getTbName());
        String sql1 = "CREATE TABLE IF NOT EXISTS " + ReservationHelper.getTbName() + " ( " +
                ReservationHelper.getColAuto() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ReservationHelper.getCol1() + " TEXT, " +
                ReservationHelper.getCol2() + " TEXT, " +
                ReservationHelper.getCol3() + " TEXT, " +
                ReservationHelper.getCol4() + " TEXT );";
        String sql2 = "CREATE TABLE IF NOT EXISTS " + PaymentHelper.getTbName() + " ( " +
                PaymentHelper.getColAuto() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PaymentHelper.getCol1() + " INTEGER, " +
                PaymentHelper.getCol2() + " INTEGER, " +
                PaymentHelper.getCol3() + " INTEGER, " +
                PaymentHelper.getCol4() + " INTEGER, " +
                PaymentHelper.getCol5() + " TEXT, " +
                PaymentHelper.getCol6() + " TEXT );";
        String sql3 = "CREATE TABLE IF NOT EXISTS " + TreatListHelper.getTbName() + " ( " +
                TreatListHelper.getColAuto() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TreatListHelper.getCol1() + " TEXT, " +
                TreatListHelper.getCol2() + " TEXT, " +
                TreatListHelper.getCol3() + " TEXT, " +
                TreatListHelper.getCol4() + " TEXT, " +
                TreatListHelper.getCol5() + " TEXT, " +
                TreatListHelper.getCol6() + " TEXT, " +
                TreatListHelper.getCol7() + " TEXT );";
        String sql4 = "CREATE TABLE IF NOT EXISTS " + NotificationHelper.getTbName() + " ( " +
                NotificationHelper.getColAuto() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NotificationHelper.getCol1() + " TEXT, " +
                NotificationHelper.getCol2() + " TEXT, " +
                NotificationHelper.getCol3() + " TEXT, " +
                NotificationHelper.getCol4() + " TEXT, " +
                NotificationHelper.getCol5() + " TEXT, " +
                NotificationHelper.getCol6() + " TEXT, " +
                NotificationHelper.getCol7() + " INTEGER );";
        try{
            db.execSQL(sql1);
            db.execSQL(sql2);
            db.execSQL(sql3);
            db.execSQL(sql4);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void close(){
        mDB.close();
    }

    public long insertReserv(ArrayList<ReservationVO> items){
        long result = 0;
        for(ReservationVO item : items){
            ContentValues values = new ContentValues();
            values.put(ReservationHelper.getCol1(), item.getReserv_Date());
            values.put(ReservationHelper.getCol2(), item.getReserv_Time());
            values.put(ReservationHelper.getCol3(), item.getReserv_TxName());
            values.put(ReservationHelper.getCol4(), item.getReserv_Next());
            result = mDB.insert(ReservationHelper.getTbName(), null, values);
        }
        return result;
    }

    public long insertPay(ArrayList<PaymentVO> items){
        long result = 0;
        for(PaymentVO item : items){
            ContentValues values = new ContentValues();
            values.put(PaymentHelper.getCol1(), item.getPayCash());
            values.put(PaymentHelper.getCol2(), item.getPayCard());
            values.put(PaymentHelper.getCol3(), item.getPayOnline());
            values.put(PaymentHelper.getCol4(), item.getPayMisu());
            values.put(PaymentHelper.getCol5(), item.getPayType());
            values.put(PaymentHelper.getCol6(), item.getPayDate());
            result = mDB.insert(PaymentHelper.getTbName(), null, values);
        }
        return result;
    }

    public long insertTreatList(ArrayList<TreatListVO> items){
        long result = 0;
        for(TreatListVO item : items){
            ContentValues values = new ContentValues();
            values.put(TreatListHelper.getCol1(), item.getTL_Listkey());
            values.put(TreatListHelper.getCol2(), item.getTL_Masterkey());
            values.put(TreatListHelper.getCol3(), item.getTL_TxName());
            values.put(TreatListHelper.getCol4(), item.getTL_Doctor());
            values.put(TreatListHelper.getCol5(), item.getTL_Date());
            values.put(TreatListHelper.getCol6(), item.getTL_TxDetail());
            values.put(TreatListHelper.getCol7(), item.getTL_Result());
            result = mDB.insert(TreatListHelper.getTbName(), null, values);
        }
        return result;
    }

    public long insertNotification(NotificationVO item){
        long result = 0;
        ContentValues values = new ContentValues();
        item.setDate(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
        item.setIsRead(0);
        values.put(NotificationHelper.getCol1(), item.getTitle());
        values.put(NotificationHelper.getCol2(), item.getContent());
        values.put(NotificationHelper.getCol3(), item.getImgURL());
        values.put(NotificationHelper.getCol4(), item.getURL());
        values.put(NotificationHelper.getCol5(), item.getType());
        values.put(NotificationHelper.getCol6(), item.getDate());
        values.put(NotificationHelper.getCol7(), item.getIsRead());
        result = mDB.insert(NotificationHelper.getTbName(), null, values);
        return result;
    }

    public int getNotReadCount(){
        int result = 0;
        Cursor cursor = mDB.rawQuery("SELECT COUNT(*) FROM " + NotificationHelper.getTbName() + " WHERE NOTI_isRead = 0;", null);
        while(cursor.moveToNext()){
            result = cursor.getInt(cursor.getColumnIndexOrThrow("COUNT(*)"));
        }
        return result;
    }

    public long updateNotification(){
        long result = 0;
        ContentValues values = new ContentValues();
        values.put(NotificationHelper.getCol7(), 1);
        result = mDB.update(NotificationHelper.getTbName(), values, null, null);
        return result;
    }

    public Cursor getAllData(String TB_NAME){
        return mDB.rawQuery("SELECT * FROM " + TB_NAME + " ORDER BY ID ASC;", null);
    }

    public Cursor getAllDataDESC(String TB_NAME){
        return mDB.rawQuery("SELECT * FROM " + TB_NAME + " ORDER BY ID DESC;", null);
    }

 }
