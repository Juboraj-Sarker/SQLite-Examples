package com.juborajsarker.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.juborajsarker.sqlite.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(User.CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);
        onCreate(db);
    }

    public long insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(User.COLUMN_ID, user.getId());
        values.put(User.COLUMN_NAME, user.getName());
        values.put(User.COLUMN_EMAIL, user.getEmail());
        values.put(User.COLUMN_PASSWORD, user.getPassword());
        values.put(User.COLUMN_PHONE, user.getPhone());
        long id = db.insert(User.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public User getUserById(long id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(User.TABLE_NAME,
                new String[]{User.COLUMN_ID, User.COLUMN_NAME, User.COLUMN_EMAIL, User.COLUMN_PASSWORD, User.COLUMN_PHONE},
                User.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(
                cursor.getInt(cursor.getColumnIndex(User.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(User.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(User.COLUMN_EMAIL)),
                cursor.getString(cursor.getColumnIndex(User.COLUMN_PASSWORD)),
                cursor.getString(cursor.getColumnIndex(User.COLUMN_PHONE)));

        cursor.close();

        return user;
    }


    public List<User> getUserByEmailAndPassword(String email, String password) {

        List<User> users = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + User.TABLE_NAME + " WHERE "
                + User.COLUMN_EMAIL + " = '" + email + "'" + " AND "
                + User.COLUMN_PASSWORD + " = '" + password + "'" ;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                User user = new User();
                user.setId(c.getInt((c.getColumnIndex(User.COLUMN_ID))));
                user.setName((c.getString(c.getColumnIndex(User.COLUMN_NAME))));
                user.setEmail(c.getString(c.getColumnIndex(User.COLUMN_EMAIL)));
                user.setPassword(c.getString(c.getColumnIndex(User.COLUMN_PASSWORD)));
                user.setPhone(c.getString(c.getColumnIndex(User.COLUMN_PHONE)));
                users.add(user);
            } while (c.moveToNext());
        }

        return users;
    }



    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + User.TABLE_NAME + " ORDER BY " +
                User.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(User.COLUMN_ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(User.COLUMN_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(User.COLUMN_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(User.COLUMN_PASSWORD)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(User.COLUMN_PHONE)));

                users.add(user);
            } while (cursor.moveToNext());
        }
        db.close();

        return users;
    }

    public int getUsersCount() {
        String countQuery = "SELECT  * FROM " + User.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User.COLUMN_NAME, user.getName());
        values.put(User.COLUMN_EMAIL, user.getEmail());
        values.put(User.COLUMN_PASSWORD, user.getPassword());
        values.put(User.COLUMN_PHONE, user.getPhone());

        return db.update(User.TABLE_NAME, values, User.COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(User.TABLE_NAME, User.COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
}
