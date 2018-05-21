package com.example.george_pc.todoapp.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.george_pc.todoapp.Adapters.DashboardAdapter;
import com.example.george_pc.todoapp.R;
import com.example.george_pc.todoapp.models.ToDoDatabase;
import com.example.george_pc.todoapp.models.ToDoItem;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DashboardFragment extends Fragment {
    List<ToDoItem> itemlist;
    public RecyclerView recyclerView;
    public DashboardAdapter dashboardAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dashboard_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ToDoDatabase.returnDatabase(getContext()); // provide the context for the databaseobject

        recyclerView = (RecyclerView) view.findViewById(R.id.dashboard_recyclerview);

        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        try {
            dashboardAdapter = new DashboardAdapter((DashboardActivity) getActivity());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(dashboardAdapter);

    }

}