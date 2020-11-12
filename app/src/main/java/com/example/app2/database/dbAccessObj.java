package com.example.app2.database;

import android.content.ContentValues;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import com.example.app2.database.FeedReaderContract.FeedEntry;
import android.database.Cursor;

public class dbAccessObj {
    SQLiteDatabase database;
    DBHelper dbHelper;
    public dbAccessObj(Context context) {
        dbHelper = new DBHelper(context);
    }
    public void openDb() {
        database = dbHelper.getWritableDatabase();
    }

    private void closeDb(){};
    public void createRow(String title, String subtitle){
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TITLE,title);
        values.put(FeedEntry.COLUMN_NAME_SUBTITLE,subtitle);
        database.insert(FeedEntry.TABLE_NAME,null,values);
    }
    public String readRow(){
        Cursor cursor = database.query(FeedEntry.TABLE_NAME,null,null,null,null,null,null);
        //return the result as a string
        cursor.moveToLast();
        int titleIndex = cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TITLE);
        int subtitleIndex = cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_SUBTITLE);

        String title = cursor.getString(titleIndex);
        String subtitle = cursor.getString(subtitleIndex);
        return title +"\n" +subtitle;
    };
    private void updateRow(){};
    private void deleteRow(){};
    public Cursor getRows() {
        Cursor cursor = database.query(FeedEntry.TABLE_NAME,null,null,null,null,null,null);
        return cursor;
    }
    public String  query(String queryParam) {
        String table = FeedEntry.TABLE_NAME;
        String[] columns = {FeedEntry.COLUMN_NAME_TITLE,FeedEntry.COLUMN_NAME_SUBTITLE}; //projection = columns
        String selection = FeedEntry.COLUMN_NAME_TITLE +" =?"; //selection = rows
        String[] selectionArgs = {queryParam};
        String groupBy = null;
        String having = null;
        String orderBy = FeedEntry.COLUMN_NAME_TITLE+" DESC";
        String limit = "10";

        Cursor cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        int subtitleIndex = cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_SUBTITLE);
        cursor.moveToLast();
        String subtitle = cursor.getString(subtitleIndex);
        return subtitle;

    }


}
