package com.onedaydent.onedaydent.Common;

import android.content.Context;

public class DBCreater {
    private static final DBCreater ourInstance = new DBCreater();
    private static final String DATABASE_NAME = "DB";

    public static DBCreater getInstance() {
        return ourInstance;
    }

    private DBCreater() {
    }

    public void createDatabase(Context context){
        context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
}
