package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "todo.db";
    private static final String TABLE_NAME = "todo";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String STARTED = "started";
    private static final String FINISHED = "finished";

    public DbHandler(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , TITLE TEXT , " +
                                "DESCRIPTION TEXT , STARTED TEXT , FINISHED TEXT  ) ");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);


    }
    // Add a single todo
    public void addToDo(ToDo toDo){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE,toDo.getTitle());
        contentValues.put(DESCRIPTION, toDo.getDescription());
        contentValues.put(STARTED,toDo.getStarted());
        contentValues.put(FINISHED,toDo.getFinished());

        //save data to the table
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        // close database
        sqLiteDatabase.close();
    }
    // Count todo table records
    public int countToDo(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME;

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        return cursor.getCount();
    }
    // Get all toDos into a list
    public List<ToDo> getAllToDos(){

        List<ToDo> toDoList = new ArrayList();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do {
                // Create new ToDo object
                ToDo toDo = new ToDo();

                toDo.setId(cursor.getInt(0));
                toDo.setTitle(cursor.getString(1));
                toDo.setDescription(cursor.getString(2));
                toDo.setStarted(cursor.getLong(3));
                toDo.setFinished(cursor.getLong(4));

                toDoList.add(toDo);
            }while (cursor.moveToNext());
        }
        return toDoList;
    }

    // Delete toDo

    public void deleteToDo(int id){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,"id =?",new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }
    // Get a single toDo
    public ToDo getSingleToDo(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,new String[]{ID,TITLE,DESCRIPTION,STARTED, FINISHED},
                ID + "= ?",new String[]{String.valueOf(id)}
                ,null,null,null);

        ToDo toDo;
        if(cursor != null){
            cursor.moveToFirst();
            toDo = new ToDo(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getLong(3),
                    cursor.getLong(4)
            );
            return toDo;
        }
        return null;
    }
    // Update a single todo
    public int updateSingleToDo(ToDo toDo){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE,toDo.getTitle());
        contentValues.put(DESCRIPTION, toDo.getDescription());
        contentValues.put(STARTED,toDo.getStarted());
        contentValues.put(FINISHED,toDo.getFinished());

        int status = sqLiteDatabase.update(TABLE_NAME,contentValues,ID +" =?",
                new String[]{String.valueOf(toDo.getId())});

        sqLiteDatabase.close();
        return status;
    }
}
