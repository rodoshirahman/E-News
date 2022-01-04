package com.example.dell.e_news;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dell on 2/6/2017.
 */

public class MyDBFunctions extends SQLiteOpenHelper {

    private static final String DATABSE_NAME = "mydb";
    private static final String TABLE_NAME = "mytab";

    private static final String TAB_ID = "id";
    private static final String TAB_NAME = "name";
    private static final String TAB_EMAIL = "email";
    private static final String TAB_OCC = "occupation";
    private static final String TAB_LOC = "location";
    private static final String TAB_BDAY = "bday";
    private static final String TAB_Pass = "password";




    MyDBFunctions(Context c){
        super(c, DATABSE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String s = "CREATE TABLE "+TABLE_NAME+" ("+TAB_ID+" INTEGER PRIMARY KEY, "+TAB_NAME+" TEXT, "+TAB_EMAIL+" TEXT, "+TAB_LOC+" TEXT, "+TAB_BDAY+" TEXT)";
        db.execSQL(s);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    // ---- ---- adding data to database --- -----

    void addingDataToTable(DataTemp dt){

        SQLiteDatabase sqd  = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TAB_NAME, dt.getName());
        cv.put(TAB_EMAIL, dt.getEmail());
        cv.put(TAB_LOC, dt.getLocation());
        cv.put(TAB_BDAY, dt.getBday());
        cv.put(TAB_OCC, dt.getOcc());
        cv.put(TAB_Pass, dt.getPass());

        sqd.insert(TABLE_NAME, null, cv);
        sqd.close();

    }


    // --- ---- showing data ------ ----

    String[] my_data() {

        SQLiteDatabase sq = this.getReadableDatabase();

        String q = "SELECT * FROM "+TABLE_NAME;

        Cursor c = sq.rawQuery(q, null);

        String[] recvied_data = new String[c.getCount()];

        c.moveToFirst();

        if(c.moveToFirst()){
            int counter = 0 ;
            do {
                recvied_data[counter] = c.getString(c.getColumnIndex(TAB_NAME+""));// +"\nEmail: "+
                        //c.getString(c.getColumnIndex(TAB_EMAIL+""));
                counter ++;

            } while(c.moveToNext());

        }

        return recvied_data;
    }


    String fetch_day(int id) {

        SQLiteDatabase sq = this.getReadableDatabase();

        String q = "SELECT "+TAB_EMAIL+" FROM "+TABLE_NAME+" WHERE "+TAB_ID+" = "+id;

        Cursor c = sq.rawQuery(q, null);
        String s = "";

        c.moveToFirst();

        if(c.moveToFirst()) {
            s = c.getString(c.getColumnIndex(TAB_EMAIL+""));
        }

        return s;

    }


    int update_birthday(int id, String bday) {

        SQLiteDatabase sq = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TAB_EMAIL, bday);

        return sq.update(TABLE_NAME, cv, TAB_ID+" = ? ", new String[]{id+""});

    }


    int delete_bday(String bday){

        SQLiteDatabase s = this.getWritableDatabase();

        return s.delete(TABLE_NAME, TAB_EMAIL+" = ?", new String[] {bday});

    }


}
