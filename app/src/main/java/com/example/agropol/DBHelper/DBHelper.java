package com.example.agropol.DBHelper;

import android.annotation.SuppressLint;
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
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS pracownik " +
                "( ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ," +
                " Login VARCHAR(30) NOT NULL , " +
                "Haslo VARCHAR(30) NOT NULL , " +
                "Imie VARCHAR(30) NOT NULL , " +
                "Nazwisko VARCHAR(30) NOT NULL , " +
                "Tel INTEGER(9) NOT NULL);");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS klient " +
                "( ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , " +
                "Imie VARCHAR(30) NOT NULL , " +
                "Nazwisko VARCHAR(30) NOT NULL , " +
                "Adres_Email TEXT NOT NULL , " +
                "Tel INTEGER(9) NOT NULL);");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS sadzonki " +
                "( ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , " +
                "Gatunek VARCHAR(30) NOT NULL , " +
                "Odmiana VARCHAR(30) NOT NULL , " +
                "Ilosc INTEGER NOT NULL , " +
                "Cena FLOAT NOT NULL );");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS zamowienie " +
                "( ID INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT , " +
                "IDKlienta INTEGER NOT NULL , " +
                "Cena FLOAT NOT NULL , " +
                "Data_zamowienia DATE NOT NULL  );");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS szczegoly_zamowienia " +
                "( IDZamowienia INTEGER NOT NULL , " +
                "IDSadzonki INTEGER NOT NULL , " +
                "Ilość INTEGER NOT NULL );");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS reklamacja " +
                "( ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , " +
                "IDKlienta INTEGER NOT NULL , " +
                "IDZamowienia INTEGER NOT NULL , " +
                "IDPracownika INTEGER , " +
                "Tresc TEXT NOT NULL , " +
                "Stan TEXT NOT NULL );");

        //sqLiteDatabase.execSQL("INSERT INTO pracownik (Login, Haslo, Imie, Nazwisko, Tel) VALUES ('login', 'haslo', 'stefan', 'czarnecki', '123456789');");

    }

    public void onAlter()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("Alter Table sadzonki ADD obrazek INTEGER");
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

    public boolean setData(String sql) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(sql, null);
            return true;
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            return false;
        }
    }
    public void showAllColumnsName()//tymczasowa klasa
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor result=db.rawQuery("select * from pracownik",null);
        System.out.println("pracownik");
        for(int i=0;i<result.getColumnCount();i++)
            System.out.print(result.getColumnName(i)+" ");
        System.out.println("\nklient");
        result=db.rawQuery("select * from klient",null);
        for(int i=0;i<result.getColumnCount();i++)
            System.out.print(result.getColumnName(i)+" ");
        System.out.println("\nsadzonki");
        result=db.rawQuery("select * from sadzonki",null);
        for(int i=0;i<result.getColumnCount();i++)
            System.out.print(result.getColumnName(i)+" ");
        System.out.println("\nzamowienie");
        result=db.rawQuery("select * from zamowienie",null);
        for(int i=0;i<result.getColumnCount();i++)
            System.out.print(result.getColumnName(i)+" ");
        System.out.println("\nszczegoly_zamowienia");
        result=db.rawQuery("select * from szczegoly_zamowienia",null);
        for(int i=0;i<result.getColumnCount();i++)
            System.out.print(result.getColumnName(i)+" ");
        System.out.println("\nreklamacja");
        result=db.rawQuery("select * from reklamacja",null);
        for(int i=0;i<result.getColumnCount();i++)
            System.out.print(result.getColumnName(i)+" ");
        System.out.println();
    }
}
