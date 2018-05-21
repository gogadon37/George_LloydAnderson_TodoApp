package com.example.george_pc.todoapp.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity (tableName = "Items") // Define the table name.

public class ToDoItem {

    @PrimaryKey (autoGenerate = true)           // Define the Id to be the primary key
    private  int Id;
    private  String item_title;
    private  String item_details;
    private  Boolean item_complete;


   // Generate the Getters and Setters for the Variables //

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_details() {
        return item_details;
    }

    public void setItem_details(String item_details) {
        this.item_details = item_details;
    }

    public Boolean getItem_complete() {
        return item_complete;
    }

    public void setItem_complete(Boolean item_complete) {
        this.item_complete = item_complete;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }


} // End of Class
