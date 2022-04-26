package com.example.agropol.DBHelper;

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
        sqLiteDatabase.execSQL("DELETE FROM pracownicy where Login like 'login';");
        sqLiteDatabase.execSQL("INSERT INTO pracownik (Login, Haslo, Imie, Nazwisko, Tel) VALUES ('login', 'haslo', 'stefan', 'czarnecki', '123456789');");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS pracownik;");
        onCreate(sqLiteDatabase);
    }

    public Cursor getData(String log) {

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor result=db.rawQuery(log,null);
        result.moveToFirst();
        return result;
    }
}
