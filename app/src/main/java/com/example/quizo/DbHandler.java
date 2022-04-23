package com.example.quizo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHandler extends SQLiteOpenHelper {
    private Context context;
    public DbHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "CREATE TABLE Player (sno INTEGER primary key, name varchar, score integer)";
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String drop = String.valueOf("DROP TABLE IF EXISTS");
        sqLiteDatabase.execSQL(drop, new String[]{"Player"});
        onCreate(sqLiteDatabase);
    }
    public void addPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
            values.put("name", player.getName());
            values.put("score", player.getScore());
            long k = db.insert("Player", null, values);
            db.close();



    }
    public Boolean checkIfPlayerExist(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("player", new String[]{"sno", "name", "score"}, "name=?", new String[]{name}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }
    public ArrayList<String[]> getAllEmployee(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Player",null);
        ArrayList<String[]> list = new ArrayList<String[]>();
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(1);
                String score = String.valueOf(cursor.getInt(2));
                list.add(new String[]{name, score});
                cursor.moveToNext();
            }
        }
        return list;
    }
}
