package com.example.agropol.DBHelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.Console;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static String nazwaDB="agroPol.db";
    private static int ver=1;

    public DBHelper(@Nullable Context context) {
        super(context,nazwaDB , null, ver);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS employee " +
                "( ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ," +
                " Login VARCHAR(30) NOT NULL , " +
                "Password VARCHAR(30) NOT NULL , " +
                "Name VARCHAR(30) NOT NULL , " +
                "Surname VARCHAR(30) NOT NULL , " +
                "Tel VARCHAR(20) NOT NULL);");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS client " +
                "( ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , " +
                "Login VARCHAR(30) NOT NULL , " +
                "Password VARCHAR(30) NOT NULL , " +
                "Name VARCHAR(30) NOT NULL , " +
                "Surname VARCHAR(30) NOT NULL , " +
                "Email TEXT NOT NULL , " +
                "Tel VARCHAR(20) NOT NULL);");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS plant " +
                "( ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , " +
                "Species VARCHAR(30) NOT NULL , " +
                "Variety VARCHAR(30) NOT NULL , " +
                "Quantity INTEGER NOT NULL , " +
                "Price FLOAT NOT NULL ," +
                "Image INTEGER NOT NULL );");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS request " +
                "( ID INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT , " +
                "IDClient INTEGER NOT NULL , " +
                "Price Double NOT NULL , " +
                "Date_of_request DATE NOT NULL  );");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS details_request " +
                "( IDRequest INTEGER NOT NULL , " +
                "IDPlant INTEGER NOT NULL , " +
                "Quantity INTEGER NOT NULL );");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS complaint " +
                "( ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , " +
                "IDClient INTEGER NOT NULL , " +
                "IDRequest INTEGER NOT NULL , " +
                "IDEmployee INTEGER , " +
                "Contents TEXT NOT NULL , " +
                "Status TEXT NOT NULL );");

        //sqLiteDatabase.execSQL("INSERT INTO pracownik (Login, Haslo, Imie, Nazwisko, Tel) VALUES ('login', 'haslo', 'stefan', 'czarnecki', '123456789');");

    }

    public void onAlter()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("Drop Table szczegoly_zamowienia");
        db.execSQL("Drop Table sadzonki");
        onCreate(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS pracownik;");
        onCreate(sqLiteDatabase);
    }

    public Cursor getDate(String sql) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor result=db.rawQuery(sql,null);
        result.moveToFirst();
        return result;
    }

    public void setData(String table,String[] col,String[] value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        for(int i=0;i<col.length;i++)
        {
            contentValues.put(col[i],value[i]);
        }
        db.insert(table,null,contentValues);
        System.out.println("wykonano");
    }

    public void delData(String table,String where) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table,where,null);
    }

    public void editData(String table,String where,String[] col,String[] value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        for(int i=0;i<col.length;i++)
        {
            contentValues.put(col[i],value[i]);
        }
        db.update(table,contentValues,where,null);
        System.out.println("wykonano");
    }
    public void showAllColumnsName()//tymczasowa klasa
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor result=db.rawQuery("select * from employee",null);
        System.out.println("employee");
        for(int i=0;i<result.getColumnCount();i++)
            System.out.print(result.getColumnName(i)+" ");
        System.out.println("\nclient");
        result=db.rawQuery("select * from client",null);
        for(int i=0;i<result.getColumnCount();i++)
            System.out.print(result.getColumnName(i)+" ");
        System.out.println("\nplant");
        result=db.rawQuery("select * from plant",null);
        for(int i=0;i<result.getColumnCount();i++)
            System.out.print(result.getColumnName(i)+" ");
        System.out.println("\nrequest");
        result=db.rawQuery("select * from request",null);
        for(int i=0;i<result.getColumnCount();i++)
            System.out.print(result.getColumnName(i)+" ");
        System.out.println("\ndetails_request");
        result=db.rawQuery("select * from details_request",null);
        for(int i=0;i<result.getColumnCount();i++)
            System.out.print(result.getColumnName(i)+" ");
        System.out.println("\ncomplaint");
        result=db.rawQuery("select * from complaint",null);
        for(int i=0;i<result.getColumnCount();i++)
            System.out.print(result.getColumnName(i)+" ");
        System.out.println();
    }
}
