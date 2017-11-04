package com.example.kesar.workshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kesar on 11/3/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "Workshop.db";

    //User Table Name
    protected static final String TABLE_USER = "user";
    //Workshop Table Name
    protected static final String TABLE_WORKSHOP = "workshop";
    //workshop registration table
    protected  static final String TABLE_USER_WORKSHOP = "user_workshop";

    //User Table Columns Name
    protected static final String COLUMN_USER_ID = "user_id";
    protected static final String COLUMN_USER_NAME = "user_name";
    protected static final String COLUMN_USER_EMAIL = "user_email";
    protected static final String COLUMN_USER_PASSWORD = "user_password";

    //Workshop table columns name
    protected static final String COLUMN_WORKSHOP_ID = "workshop_id";
    protected static final String COLUMN_WORKSHOP_NAME = "workshop_name";
    protected static final String COLUMN_WORKSHOP_COLLEGE_NAME = "workshop_college_name";
    protected static final String COLUMN_WORKSHOP_LOCATION = "workshop_location";
    protected static final String COLUMN_WORKSHOP_DATE = "workshop_date";

    //user Workshop table column name
    protected static final String COLUMN_USER_WORKSHOP_ID = "user_id";
    protected static final String COLUMN_WORKSHOP_USER_ID = "workshop_id";

    //CREATE USER table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT, " + COLUMN_USER_EMAIL + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT" + ");";

    //CREATE WORKSHOP table sql query
    private String CREATE_WORKSHOP_TABLE = "CREATE TABLE " + TABLE_WORKSHOP + "(" + COLUMN_WORKSHOP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_WORKSHOP_NAME + " TEXT," + COLUMN_WORKSHOP_COLLEGE_NAME + " TEXT," + COLUMN_WORKSHOP_LOCATION + " TEXT," + COLUMN_WORKSHOP_DATE + " TEXT" + ");";

    //Create user_workshop table sql query
    private String CREATE_USER_WORKSHOP_TABLE = "CREATE TABLE " + TABLE_USER_WORKSHOP + "(" + COLUMN_USER_WORKSHOP_ID + " INTEGER," + COLUMN_WORKSHOP_USER_ID + " INTEGER," + " PRIMARY KEY (" + COLUMN_USER_WORKSHOP_ID + " , " + COLUMN_WORKSHOP_USER_ID + ")" + ");";

    //Drop USER Table SQL Query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS" + TABLE_USER;

    //Drop workshop table sql query
    private String DROP_WORKSHOP_TABLE = "DROP TABLE IF EXISTS" + TABLE_WORKSHOP;

    //Drop user workshop table sql query
    private String DROP_USER_WORKSHOP_TABLE = "DROP TABLE IF EXISTS" + TABLE_USER_WORKSHOP;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_WORKSHOP_TABLE);
        db.execSQL(CREATE_USER_WORKSHOP_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_WORKSHOP_TABLE);
        db.execSQL(DROP_USER_WORKSHOP_TABLE);

        onCreate(db);

    }

    //Method Use to create new user record

    public void addUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);
        db.close();

    }

    //Method use to create new workshop

    public void addWorkshop(Workshop workshop) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_WORKSHOP_NAME, workshop.getWorkshop_name());
        values.put(COLUMN_WORKSHOP_COLLEGE_NAME, workshop.getCollege_name());
        values.put(COLUMN_WORKSHOP_LOCATION, workshop.getLocation());
        values.put(COLUMN_WORKSHOP_DATE, workshop.getDate());

        db.insert(TABLE_WORKSHOP, null, values);
        db.close();

    }

    //methos use to registration of workshops

    public void addUserWorkshop(UserWorkshop userWorkshop) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_WORKSHOP_ID, userWorkshop.getUser_workshop_id());
        values.put(COLUMN_WORKSHOP_USER_ID, userWorkshop.getWorkshop_user_id());

        db.insert(TABLE_USER_WORKSHOP, null, values);
        db.close();

    }

    //Methos use to fetch all user and return the list of all user

    public List<User> getAllUser() {

        String [] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_EMAIL,
                COLUMN_USER_PASSWORD
        };

        String sortOrder = COLUMN_USER_NAME + " ASC";

        List<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, columns, null, null, null, null, sortOrder);

        if (cursor.moveToFirst()) {

            do {

                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));

                //adding user record to list
                userList.add(user);

            } while(cursor.moveToNext());

        }

        cursor.close();
        db.close();

        return userList;

    }

    //Method use to fetch all workshop

    public List<Workshop> getAllWorkshop() {

        String [] columns = {
                COLUMN_WORKSHOP_ID,
                COLUMN_WORKSHOP_NAME,
                COLUMN_WORKSHOP_COLLEGE_NAME,
                COLUMN_WORKSHOP_LOCATION,
                COLUMN_WORKSHOP_DATE
        };

        String sortOrder = COLUMN_WORKSHOP_NAME + " ASC";

        List<Workshop> workshopList = new ArrayList<Workshop>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_WORKSHOP, columns, null, null, null, null, sortOrder);

        if(cursor.moveToFirst()) {

            do {

                Workshop workshop = new Workshop();
                workshop.setWorkshop_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_WORKSHOP_ID))));
                workshop.setWorkshop_name(cursor.getString(cursor.getColumnIndex(COLUMN_WORKSHOP_NAME)));
                workshop.setCollege_name(cursor.getString(cursor.getColumnIndex(COLUMN_WORKSHOP_COLLEGE_NAME)));
                workshop.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_WORKSHOP_LOCATION)));
                workshop.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_WORKSHOP_DATE)));

                workshopList.add(workshop);

            } while(cursor.moveToNext());

        }

        cursor.close();
        db.close();

        return workshopList;

    }

    //methos use to update users record

    public void updateUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        //updating now
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String [] {String.valueOf(user.getId())});
        db.close();

    }

    //method is use to delete user record

    public void deleteUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        //delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[] {String.valueOf(user.getId())});
        db.close();

    }

    //method use to check whether user exists?

    public boolean checkUser(String email) {

        String [] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_EMAIL + " = ?";

        String [] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if(cursorCount > 0) {
            return true;
        }

        return false;

    }

    public boolean checkUser(String email, String password) {

        String [] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        String [] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if(cursorCount > 0) {
            return true;
        }

        return false;

    }

}
