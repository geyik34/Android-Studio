package com.geyik26.bussiness;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "userDatas";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "users";

    public static final String ROW_ID = "id";
    public static final String ROW_NAME = "name";
    public static final String ROW_TCKN = "tckn";
    public static final String ROW_GENDER = "gender";
    public static final String ROW_BIRTHDATE = "birthdate";

    int id;

    public DataBase(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ROW_NAME + " TEXT NOT NULL, " + ROW_GENDER + " TEXT NOT NULL, " + ROW_BIRTHDATE +" TEXT NOT NULL, "
                +ROW_TCKN + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void VeriEkle(String ad, String tckn, String gender, String birthdate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ROW_NAME, ad.trim());
        cv.put(ROW_BIRTHDATE, birthdate.trim());
        cv.put(ROW_GENDER,gender.trim());
        cv.put(ROW_TCKN,tckn.trim());

        db.insert(TABLE_NAME,null,cv);
        db.close();
    }

    public List<String> VeriListeleID(){
        List<String> veriler = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String [] sutunlar = {ROW_ID,ROW_NAME,ROW_TCKN,ROW_GENDER,ROW_BIRTHDATE};
        Cursor cursor = db.query(TABLE_NAME,sutunlar,null,null,null,null,null);

        List<String> bosluk = new ArrayList<>();
        String s = "";

        while (cursor.moveToNext()){
            veriler.add(cursor.getString(0));
        }
        return veriler;
    }

    public List<String> VeriListeleAD() {
            List<String> verilerAd = new ArrayList<String>();
            SQLiteDatabase dbAd = this.getWritableDatabase();
            String[] sutunlarAd = {ROW_ID, ROW_NAME, ROW_TCKN, ROW_GENDER, ROW_BIRTHDATE};
            Cursor cursorAd = dbAd.query(TABLE_NAME, sutunlarAd, null, null, null, null, null);

            while (cursorAd.moveToNext()) {
                verilerAd.add(cursorAd.getString(1));
            }
            return verilerAd;
        }

    public List<String> VeriListeleTC() {
        List<String> verilerTC = new ArrayList<String>();
        SQLiteDatabase dbTC = this.getWritableDatabase();
        String[] sutunlarTC = {ROW_ID, ROW_NAME, ROW_TCKN, ROW_GENDER, ROW_BIRTHDATE};
        Cursor cursorAd = dbTC.query(TABLE_NAME, sutunlarTC, null, null, null, null, null);

        while (cursorAd.moveToNext()) {
            verilerTC.add(cursorAd.getString(2));
        }
        return verilerTC;
    }

    public boolean updateData(String ad,String birthdate,String gender,String tckn,String id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ROW_NAME, ad.trim());
        cv.put(ROW_BIRTHDATE, birthdate.trim());
        cv.put(ROW_GENDER,gender.trim());
        cv.put(ROW_TCKN,tckn.trim());
        cv.put(ROW_ID,id.trim());

        db.update(TABLE_NAME,cv,"id = ?",new String[]{id});
        return true;
    }

    public  void ClearTable()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db !=null)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ROW_NAME + " TEXT NOT NULL, " + ROW_GENDER + " TEXT NOT NULL, " + ROW_BIRTHDATE +" TEXT NOT NULL, "
                    +ROW_TCKN + " TEXT NOT NULL)");
        }
    }

    public void VeriSilID(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,ROW_ID + "-" + id,null);
        db.close();
    }

    public String GetInfo(String id){
        String query = "select " + ROW_NAME + " from "+ TABLE_NAME + " where id = "+ Integer.parseInt(id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            String returnValue = cursor.getString(cursor.getColumnIndex(ROW_NAME));
            return returnValue;
        }else
            return null;
    }

    public String GetInfoTC(String id){
        String query = "select " + ROW_TCKN + " from "+ TABLE_NAME + " where id = "+ Integer.parseInt(id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            String returnValue = cursor.getString(cursor.getColumnIndex(ROW_TCKN));
            return returnValue;
        }else
            return null;
    }
}
