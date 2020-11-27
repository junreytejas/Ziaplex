package com.example.ziaplex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION= 1;
    private static final String DATABASE_NAME="Usermanager.db";
    private static final String TABLE_USER="User";
    private static final String COLUMN_USER_ID="user_id";
    private static final String COLUMN_FULL_NAME="full_name";
    private static final String COLUMN_USERNAME="user_name";
    private static final String COLUMN_USER_EMAIL="user_email";
    private static final String COLUMN_USER_PASS="user_password";
    private static final String COLUMN_USER_TYPE="user_type";

    private String CREATE_USER_TABLE = "CREATE TABLE "+ TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_FULL_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_PASS + " TEXT,"
            + COLUMN_USER_TYPE + " TEXT"
            + ")";

    private String DROP_USER_TABLE = " DROP TABLE IF EXISTS " + TABLE_USER;

    DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_FULL_NAME, user.getFullname());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASS, user.getPassword());
        values.put(COLUMN_USER_TYPE, user.getType());
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    boolean checkEmail(String email){
        String[] column = {COLUMN_USER_ID};

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER, column, selection, selectionArgs, null,null,null);
        int cursorCount = cursor.getCount();
        db.close();
        if (cursorCount >0){
            return true;
        }
        return false;
    }

    boolean checkUser(String email, String password){
        int cursorCount;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT user_id FROM User WHERE user_name = ? AND user_password = ?", new String[] {email, password});
        cursorCount = cursor.getCount();
        db.close();

        Log.d("SQL", DatabaseUtils.dumpCursorToString(cursor));
        Log.d("SQL", Integer.toString(cursorCount));

        if (cursorCount>0){
            return true;
        }
        return false;
    }

    boolean ifAdmin(String email, String password){
        String type = null;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT user_type FROM User WHERE user_name = ? AND user_password = ?", new String[] {email, password});

        if (cursor.moveToFirst()){
            type = cursor.getString(0);
        }
        db.close();

        Log.d("SQL", DatabaseUtils.dumpCursorToString(cursor));
        Log.d("SQL", type);

        if (type.contentEquals("admin")){
            return true;
        }
        return false;
    }

}
