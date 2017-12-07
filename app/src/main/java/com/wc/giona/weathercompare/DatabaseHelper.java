package com.wc.giona.weathercompare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "feedback";
    public static final String APIXU_TABLE = "apixu";
    public static final String OWM_TABLE = "owm";
    public static final String WU_TABLE = "wu";
    public static final String COL_1 = "id";
    public static final String COL_2 = "value";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();

    }


    public void onCreate(SQLiteDatabase db) {
        String apixuCreate = "CREATE TABLE IF NOT EXISTS "+ APIXU_TABLE + "("+ COL_1 +" integer PRIMARY KEY,"+ COL_2 +" integer)";
        String owmCreate = "CREATE TABLE IF NOT EXISTS "+ OWM_TABLE + "("+ COL_1 +" integer PRIMARY KEY,"+ COL_2 +" integer)";
        String wuCreate = "CREATE TABLE IF NOT EXISTS "+ WU_TABLE + "("+ COL_1 +" integer PRIMARY KEY,"+ COL_2 +" integer)";
        db.execSQL(apixuCreate);
        db.execSQL(owmCreate);
        db.execSQL(wuCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String apixuCheck = "DROP TABLE IF EXISTS "+ APIXU_TABLE;
        String owmCheck = "DROP TABLE IF EXISTS " + OWM_TABLE;
        String wuCheck = "DROP TABLE IF EXISTS " + WU_TABLE ;
        db.execSQL(apixuCheck);
        db.execSQL(owmCheck);
        db.execSQL(wuCheck);
        onCreate(db);
    }

    public boolean insertData(Integer[] newFeedValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvApixu = new ContentValues();
        ContentValues cvOwm = new ContentValues();
        ContentValues cvWu = new ContentValues();

        cvApixu.put(COL_2,newFeedValues[0]);
        cvOwm.put(COL_2,newFeedValues[1]);
        cvWu.put(COL_2,newFeedValues[2]);

        long apixuResult = db.insert(APIXU_TABLE,null,cvApixu);
        long owmResult = db.insert(OWM_TABLE,null,cvOwm);
        long wuResult = db.insert(WU_TABLE,null,cvWu);
        if (apixuResult==-1 && owmResult==-1 && wuResult==-1)
            return false;
        else return true;
    }

    public String[] fetchData() {
        String apixuFetch = "SELECT  AVG(" + COL_2 + ") FROM " + APIXU_TABLE;
        String owmFetch = "SELECT  AVG(" + COL_2 + ") FROM " + OWM_TABLE;
        String wuFetch = "SELECT  AVG(" + COL_2 + ") FROM " + WU_TABLE;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursorApixu      = db.rawQuery(apixuFetch, null);
        Cursor cursorOwm      = db.rawQuery(owmFetch, null);
        Cursor cursorWu      = db.rawQuery(wuFetch, null);

        String[] feedValues = new String[3];
        //apixu= [0] | owm=[1] | Wu= [2]

        StringBuffer bufferApixu = new StringBuffer();
        while (cursorApixu.moveToNext()) {
            bufferApixu.append(cursorApixu.getString(0));
        }
        feedValues[0] = bufferApixu.toString();

        StringBuffer bufferOwm = new StringBuffer();
        while (cursorOwm.moveToNext()) {
            bufferOwm.append(cursorOwm.getString(0));
        }
        feedValues[1] = bufferOwm.toString();

        StringBuffer bufferWu = new StringBuffer();
        while (cursorWu.moveToNext()) {
            bufferWu.append(cursorWu.getString(0));
        }
        feedValues[2] = bufferWu.toString();


        return feedValues;
    }
}