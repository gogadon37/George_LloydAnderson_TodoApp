package com.example.george_pc.todoapp.controllers;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.george_pc.todoapp.Adapters.DashboardAdapter;
import com.example.george_pc.todoapp.R;
import com.example.george_pc.todoapp.models.ToDoDatabase;
import com.example.george_pc.todoapp.models.ToDoItem;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class EditItemFragment  extends Fragment{


    private String Title;
    private String Description;
    public int ID;
    private Boolean Done;
    EditText title;
    CheckBox donecheckbox;
    EditText description;
    List<ToDoItem> TodoList;
    ToDoItem selectedtodo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.additem_fragment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title= (EditText) view.findViewById(R.id.add_fragment_title_et);
        description = (EditText) view.findViewById(R.id.add_fragment_description_et);
        donecheckbox = (CheckBox) view.findViewById(R.id.donecheckbox);

        title.setText("hello");


        try {
            TodoList = new getdata().execute().get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }





        // get the relevent todo object from the list

        for(ToDoItem toDoItem: TodoList){

           int pos= getArguments().getInt("id")-1;
           int id = toDoItem.getId();

            if(id == TodoList.get(pos).getId()){

                System.out.println("Match Found");
                System.out.println(toDoItem.getItem_title());
                selectedtodo = toDoItem;

                System.out.println("-------------------------------");
                System.out.println(selectedtodo.getItem_title());
                System.out.println(selectedtodo.getItem_details());
                break;
            }
        }


        ID = selectedtodo.getId();
        title.setText(selectedtodo.getItem_title());
        description.setText(selectedtodo.getItem_details());
        donecheckbox.setChecked(selectedtodo.getItem_complete());


    }

    public String getTitle() {

        Title = title.getText().toString();
        return Title;
    }

    public String getDescription() {

        Description = description.getText().toString();
        return Description;
    }

    public Boolean getDone() {

        Done = donecheckbox.isChecked();
        return Done;
    }


    private class getdata extends AsyncTask<Void , Void,List<ToDoItem>> {

        @Override
        protected List<ToDoItem> doInBackground(Void... voids) {

            return  ToDoDatabase.returnDatabase(getContext()).databaseAccessObject().getItems();

        }
    }


}
