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
        exec(sqLiteDatabase,"CREATE TABLE `agropol`.`sadzonki` ( `ID` INT NOT NULL AUTO_INCREMENT , `Gatunek` VARCHAR(30) NOT NULL , `Odmiana` VARCHAR(30) NOT NULL , `Ilosc` INT NOT NULL , `Cena` FLOAT NOT NULL , PRIMARY KEY (`ID`))");
    }

    private void exec(SQLiteDatabase sqLiteDatabase, String s) {
        sqLiteDatabase.execSQL(s);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
