package com.example.george_pc.todoapp.models;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


// Annotate as a database and pass in the table data entity and the database version number

@Database(entities = {ToDoItem.class}, version = 1)

    public abstract class ToDoDatabase extends RoomDatabase {


    public static ToDoDatabase DatabaseInstance; // Create an DB object to hold an instance of the database.
    public abstract DatabaseAccessObject databaseAccessObject(); // get access to the data acess object.

    // create a method to return the database instance if it has already been created else create the database and return it.

    public static  ToDoDatabase returnDatabase (final Context context){

        if(DatabaseInstance == null){

        DatabaseInstance = Room.databaseBuilder(context, ToDoDatabase.class, "Database").build();

        }


        return DatabaseInstance; // return the database instance
    }



}
