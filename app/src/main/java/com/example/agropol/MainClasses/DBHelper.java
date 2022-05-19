package com.example.agropol.MainClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
                "Tel VARCHAR(20) NOT NULL," +
                "Address TEXT NOT NULL," +
                "City TEXT NOT NULL);");

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
                "Price Float, " +
                "Date_of_request DATE,  " +
                "Date_of_delivery DATE, " +
                "Reception VARCHAR(30), " +
                "Status TEXT, " +
                "Delivery Float);");

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
                "Status TEXT NOT NULL," +
                "Date_of_Complaint Date NOT NULL );");

        sqLiteDatabase.execSQL("INSERT INTO employee (Login, Password, Name, Surname, Tel) VALUES ('login1', 'haslo1', 'Jan', 'Kowalski', '568-976-345');");
        sqLiteDatabase.execSQL("INSERT INTO employee (Login, Password, Name, Surname, Tel) VALUES ('login2', 'haslo2', 'Marian', 'Wajda', '636-673-245');");
        sqLiteDatabase.execSQL("INSERT INTO client (Login,Password,Name,Surname,Email,Tel,Address,City) VALUES ('klient1','klient1','Kamil','Porada','kamil.porada@wp.pl','508-345-757','Potworów 16','26-414 Potworów'),('klient2','klient2','Adam','Rychlik','rychlicki@wp.pl','918-273-645','Radom','26-600 Radom');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }

    public Cursor getDate(String sql) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor result=db.rawQuery(sql,null);
        result.moveToFirst();
        db.close();
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
        db.close();
    }

    public void delData(String table,String where) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table,where,null);
        db.close();
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
        db.close();
    }
}
