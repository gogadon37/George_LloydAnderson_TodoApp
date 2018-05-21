package com.example.george_pc.todoapp.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao // Define the class as a data access object

public interface DatabaseAccessObject {

 @Insert
 public void addItem(ToDoItem toDoItem);  // Method to insert a ToDoItem object into the database

 @Query("select * from Items")
  public List<ToDoItem> getItems();

 @Delete
 public  void deleteitem (ToDoItem toDoItem);

 @Update
 public  void updateitem (ToDoItem toDoItem);

}
