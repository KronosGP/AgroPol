package com.example.agropol.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static String nazwaDB="AgroPol.db";
    private static int ver=1;

    public DBHelper(@Nullable Context context) {
        super(context,nazwaDB , null, ver);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE `agropol`.`pracownik` " +
                "( `ID` INT NOT NULL PRIMARY KEY AUTOINCREMENT ," +
                " `Login` VARCHAR(30) NOT NULL , " +
                "`Haslo` VARCHAR(30) NOT NULL , " +
                "`Imie` VARCHAR(30) NOT NULL , " +
                "`Nazwisko` VARCHAR(30) NOT NULL , " +
                "`Tel` INT(9) NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE `agropol`.`klient` " +
                "( `ID` INT NOT NULL PRIMARY KEY AUTOINCREMENT , " +
                "`Imie` VARCHAR(30) NOT NULL , " +
                "`Nazwisko` VARCHAR(30) NOT NULL , " +
                "`Adres_Email` TEXT NOT NULL , " +
                "`Tel` INT(9) NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE `agropol`.`sadzonki` " +
                "( `ID` INT NOT NULL PRIMARY KEY AUTOINCREMENT , " +
                "`Gatunek` VARCHAR(30) NOT NULL , " +
                "`Odmiana` VARCHAR(30) NOT NULL , " +
                "`Ilosc` INT NOT NULL , " +
                "`Cena` FLOAT NOT NULL )");

        sqLiteDatabase.execSQL("CREATE TABLE `agropol`.`zamowienie` " +
                "( `ID` INT NOT NULL  PRIMARY KEY AUTOINCREMENT , " +
                "`IDKlienta` INT NOT NULL , " +
                "`Cena` FLOAT NOT NULL , " +
                "`Data_zamowienia` DATE NOT NULL  )");

        sqLiteDatabase.execSQL("CREATE TABLE `agropol`.`szczegoly_zamowienia` " +
                "( `IDZamowienia` INT NOT NULL , " +
                "`IDSadzonki` INT NOT NULL , " +
                "`Ilość` INT NOT NULL )");

        sqLiteDatabase.execSQL("CREATE TABLE `agropol`.`reklamacja` " +
                "( `ID` INT NOT NULL PRIMARY KEY AUTOINCREMENT , " +
                "`IDKlienta` INT NOT NULL , " +
                "`IDZamowienia` INT NOT NULL , " +
                "`IDPracownika` INT , " +
                "`Tresc` TEXT NOT NULL , " +
                "`Stan` TEXT NOT NULL )");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
