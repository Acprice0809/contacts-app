package com.example.contactsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class  DatabaseControl {

    SQLiteDatabase database;
    DatabaseHelper helper;

    public DatabaseControl(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void open() {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public boolean insert(String name, String relationship, Integer age) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("relationship", relationship);
        values.put("age", age);
        return database.insert("contact", null, values) > 0;
    }

    public void delete(String n) {
        database.delete("contact","name =\""+n+"\"",null);
    }

    public int getAge(String name) {
        String query = "select age from contact where name=\""+name+"\"";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        int age = cursor.getInt(0);
        cursor.close();
        return age;
    }

    public String getRelationship(String name) {
        String query = "select relationship from contact where name=\""+name+"\"";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        String relationship = cursor.getString(0);
        cursor.close();
        return relationship;
    }

    public String getName(String name) {
        String query = "select name from contact where name=\""+name+"\"";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        String Name = cursor.getString(0);
        cursor.close();
        return Name;
    }

    public String[] getAllRelationshipsArray () {
        String query = "select relationship from contact";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        ArrayList<String> list = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            String name = cursor.getString(0);
            list.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        String[] array = new String[list.size()];
        return list.toArray(array);
    }


}







