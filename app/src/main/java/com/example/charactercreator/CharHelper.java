package com.example.charactercreator;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CharHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "characters.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "characters";
    public static final String CHARID = "_id";
    public static final String CHARNAME = "charName";
    public static final String CHARRACE = "charRace";
    public static final String CHARCLASS = "charClass";
    private Context context;
    private Characters characters;

    public CharHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        Message.message(context, "Called the Constructor");
    }

    /*private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + CHARID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            CHARNAME + " VARCHAR(255), " + CHARRACE + " VARCHAR(255), " + CHARCLASS + " VARCHAR(255));";*/

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            String createTable = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " charName TEXT, charRace TEXT, charClass TEXT) ";
            db.execSQL(createTable);
            Message.message(context, "onCreate called");
        }catch (SQLException e){
            Message.message(context, ""+e);
        }

    }

    //private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            Message.message(context, "onUpgrade called");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }catch (SQLException e){
            Message.message(context, ""+e);
        }

    }

    public boolean addData(String name, String race, String charClass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(CHARNAME, name);
        cVals.put(CHARRACE, race);
        cVals.put(CHARCLASS, charClass);

        long result = db.insert(TABLE_NAME, null, cVals);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor showData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public Cursor getClassData(String charClass){
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.query(TABLE_NAME, new String [] {"_id", "charName", "charRace", "charClass"}, "charClass = ?", new String[] {charClass}, null, null, null);
        return data;
    }

    public Cursor getRaceData(String race){
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.query(TABLE_NAME, new String [] {"_id", "charName", "charRace", "charClass"}, "charRace = ?", new String[] {race}, null, null, null);
        return data;
    }

    public boolean updateData(String id, String name, String race, String charClass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(CHARID, id);
        cVals.put(CHARNAME, name);
        cVals.put(CHARRACE, race);
        cVals.put(CHARCLASS, charClass);
        db.update(TABLE_NAME, cVals, "_id = ?", new String[] {id});
        return true;

    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "_id = ?", new String[] {id});
    }

}
