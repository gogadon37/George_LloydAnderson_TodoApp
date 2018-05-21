package com.example.george_pc.todoapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.george_pc.todoapp.R;
import com.example.george_pc.todoapp.controllers.DashboardActivity;
import com.example.george_pc.todoapp.models.ToDoDatabase;
import com.example.george_pc.todoapp.models.ToDoItem;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.Viewholder>{

    Context c;
    getdata GetData;
    DashboardActivity dashboardActivityy;


    List<ToDoItem> TodoList;


    // get the todo list which has been passed as a parameter

        public DashboardAdapter(DashboardActivity dashboardActivity) throws ExecutionException, InterruptedException {

        TodoList = new getdata().execute().get();
        GetData = new getdata();
        dashboardActivityy = dashboardActivity;
    }

    @NonNull
    @Override
    public DashboardAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // inflate the view and return a new viewholder with the inflated view passed as a parameter.
        View AdapterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_list, parent,false);
        c = AdapterView.getContext();
        return new Viewholder(AdapterView);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardAdapter.Viewholder holder, final int position) {

        final DashboardAdapter.Viewholder viewholder = holder;
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                   TodoList.get(position).setItem_complete(true);

                }else{

                    TodoList.get(position).setItem_complete(false);
                }

                new updatedata(position).execute(); // Call the update data method //
            }
        });

        holder.Itemtextview.setText(TodoList.get(position).getItem_title());
        holder.checkBox.setChecked(TodoList.get(position).getItem_complete());
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {


            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(c);
                alertDialog.setTitle("Delete Entry?");
                alertDialog.setMessage("Are you sure you want to delete this entry");
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                    }
                });

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new delete(position).execute();
                        try {
                            TodoList = new getdata().execute().get(); // refresh the todolist
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        notifyDataSetChanged();
                        getdata g = new getdata();
                        try {
                            g.execute().get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                });


              alertDialog.show();

                return true;
            }
        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // send the user to the edit activity.
              android.support.v4.app.FragmentManager fragmentManager =  dashboardActivityy.getSupportFragmentManager();

              dashboardActivityy.setEditItemFragment(position-1);


            }
        });
    }

    @Override
    public int getItemCount() {
        return TodoList.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder{

        TextView Itemtextview;
        LinearLayout linearLayout;
        CheckBox checkBox;

       public Viewholder(View itemView) {
           super(itemView);

         Itemtextview  = (TextView) itemView.findViewById(R.id.recyler_text);
         linearLayout = (LinearLayout) itemView.findViewById(R.id.listitemlinearlayout);
         checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
       }

   }




    private class getdata extends AsyncTask<Void , Void,List<ToDoItem>> {

        @Override
        protected List<ToDoItem> doInBackground(Void... voids) {

            return  ToDoDatabase.returnDatabase(c).databaseAccessObject().getItems();

        }
    }

    private class updatedata extends AsyncTask<Void , Void,Void> {

        int Position;

        public updatedata(int position) {

            Position = position;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ToDoItem toDoItem = new ToDoItem();
            toDoItem.setId(TodoList.get(Position).getId()); // by setting the id equal to the one clicked when the method is called it will replace it.
            toDoItem.setItem_title(TodoList.get(Position).getItem_title());
            toDoItem.setItem_details(TodoList.get(Position).getItem_details());
            toDoItem.setItem_complete(TodoList.get(Position).getItem_complete());

            //update the item
            ToDoDatabase.returnDatabase(c).databaseAccessObject().updateitem(toDoItem);
            return null;
        }
    }




    private class delete extends AsyncTask<Void , Void,Void> {

            int Position;

        public delete(int position) {

            Position = position;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ToDoDatabase.returnDatabase(c).databaseAccessObject().deleteitem(TodoList.get(Position));

            return null;
        }
    }

}

