package com.onedaydent.onedaydent.Common;

public class DB {
    private static final DB ourInstance = new DB();

    public static DB getInstance() {
        return ourInstance;
    }

    private DBHelper helper = null;

    private DB() {
    }

    public void setDBHelper(DBHelper helper){
        this.helper = helper;
    }

    public DBHelper getDbHelper(){
        return this.helper;
    }
}