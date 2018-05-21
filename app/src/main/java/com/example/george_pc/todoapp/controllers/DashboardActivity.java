package com.example.george_pc.todoapp.controllers;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.george_pc.todoapp.Adapters.DashboardAdapter;
import com.example.george_pc.todoapp.R;
import com.example.george_pc.todoapp.models.ToDoDatabase;
import com.example.george_pc.todoapp.models.ToDoItem;


// The data in the text fields does not need to be saved to the saved instance state bundle
// The varliables are created within the fragment and the fragment is restored when the orientation is changed.
// The orientation issue is usually solved by using the savedInstanceState and restoring the values on oncreate.
// The issue can also be solved by locking the orientation in the manifest file.

public class DashboardActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    DashboardFragment dashboardFragment;
    AddItemFragment Additem;
    MenuItem Done;
    MenuItem Cancel;
    public int CurrentFragment;
    public FloatingActionButton fab;
    EditItemFragment editItemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();

        getSupportActionBar().setHomeButtonEnabled(true);
        invalidateOptionsMenu();


        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  Replace the current fragment with the additem fragment //

                CurrentFragment = 1;
                Additem = new AddItemFragment();
                getSupportActionBar().setTitle("Add Item");
                invalidateOptionsMenu();

                getSupportFragmentManager().beginTransaction().replace(R.id.main_content,Additem, "add").commit();

            }
        });


       if(findViewById(R.id.main_content) !=null){

        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        //---------------------------------------------------------------------------------------------------//
        // ROTATION PROBLEM!!                                                                                //
        //---------------------------------------------------------------------------------------------------//
        // when the app rotates a new fragment is created, this results in multiple fragments being added    //
        // to the container. The solution is to check if the savedInstanceState is not null. This will       //
        // ensure that the dashboard fragement is only created when the app activity is first launched.      //
        //---------------------------------------------------------------------------------------------------//
        ///////////////////////////////////////////////////////////////////////////////////////////////////////

           if (savedInstanceState !=null){

               Additem = (AddItemFragment) fragmentManager.findFragmentByTag("add");
               editItemFragment = (EditItemFragment) fragmentManager.findFragmentByTag("edit");
               dashboardFragment = (DashboardFragment) fragmentManager.findFragmentByTag("dashboard");

               // The value is used to set the actionbar to the correct state.
               CurrentFragment = savedInstanceState.getInt("currentfragment");
               invalidateOptionsMenu();


               if(CurrentFragment != 0){


                   if(CurrentFragment ==1){

                       getSupportActionBar().setTitle("Add Item");

                   }else{
                       getSupportActionBar().setTitle("Edit Item");

                   }
               }

               return;
           }

           //  Create a new Dashboard Fragment nad use the fragment manager to transact it to the
           //  main_content framelayout. (This will display the fragment in the layout)

            CurrentFragment = 0;
            dashboardFragment = new DashboardFragment();
            fragmentManager.beginTransaction().add(R.id.main_content,dashboardFragment, "dashboard").commit();


       }
    }

    public void setEditItemFragment(int position){





        CurrentFragment = 2;
        editItemFragment = new EditItemFragment();
        getSupportActionBar().setTitle("Edit Item");
        invalidateOptionsMenu();

        Bundle b = new Bundle();
        b.putInt("id", position+2);
        editItemFragment.setArguments(b);

        fragmentManager.beginTransaction().replace(R.id.main_content,editItemFragment, "edit").commit();

    }






    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentfragment", CurrentFragment);
        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Done = menu.findItem(R.id.done);
        Cancel = menu.findItem(R.id.Cancel);
        // If the fragment is the dashboard fragment then hide the visible button.
        if(CurrentFragment == 0){
            Done.setVisible(false);
            Cancel.setVisible(false);

        }


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if(id == R.id.done){

            //  AddItemFragment //
            if(CurrentFragment == 1){

                savedata SavedData = new savedata();
                SavedData.execute();
                dashboardFragment = new DashboardFragment();
                getSupportActionBar().setTitle("ToDoApp");
                fragmentManager.beginTransaction().replace(R.id.main_content, dashboardFragment, "dashboard").commit();
                CurrentFragment = 0;

                invalidateOptionsMenu();


                Toast.makeText(this,"Item Added", Toast.LENGTH_SHORT).show();


                // EditItemFragment //
            }else{

                updatedata ud = new updatedata();
                ud.execute();
                dashboardFragment = new DashboardFragment();
                getSupportActionBar().setTitle("ToDoApp");
                fragmentManager.beginTransaction().replace(R.id.main_content, dashboardFragment, "dashboard").commit();
                CurrentFragment = 0;
                invalidateOptionsMenu();

            }


        }else{

            dashboardFragment = new DashboardFragment();
            getSupportActionBar().setTitle("ToDoApp");
            fragmentManager.beginTransaction().replace(R.id.main_content, dashboardFragment, "dashboard").commit();
            CurrentFragment = 0;
            invalidateOptionsMenu();

        }

        return super.onOptionsItemSelected(item);
    }

    // Async Tasks//

    private class savedata extends AsyncTask<Void , Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            ToDoItem toDoItem = new ToDoItem();
            toDoItem.setItem_title(Additem.getTitle());
            toDoItem.setItem_details(Additem.getDescription());
            toDoItem.setItem_complete(false);
            ToDoDatabase.returnDatabase(getApplicationContext()).databaseAccessObject().addItem(toDoItem);

            return null;

        }
    }

    private class updatedata extends AsyncTask<Void , Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            invalidateOptionsMenu();
            ToDoItem toDoItem = new ToDoItem();
            toDoItem.setId(editItemFragment.ID); // by setting the id equal to the one clicked when the method is called it will replace it.
            toDoItem.setItem_title(editItemFragment.getTitle());
            toDoItem.setItem_complete(editItemFragment.getDone());
            toDoItem.setItem_details(editItemFragment.getDescription());

            //update the item
            ToDoDatabase.returnDatabase(getApplicationContext()).databaseAccessObject().updateitem(toDoItem);
            return null;
        }
    }





}


