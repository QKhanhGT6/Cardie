package com.example.cardie;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConnectDatabase extends SQLiteOpenHelper {
    public ConnectDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Cursor ReadTable(String command)
    {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(command, null);
    }
    public void WriteTable(String command)
    {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL(command);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {}
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}