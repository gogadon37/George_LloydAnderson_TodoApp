package com.example.george_pc.todoapp.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.george_pc.todoapp.R;

public class AddItemFragment extends Fragment {

    private String Title;
    private String Description;
    EditText title;
    EditText description;

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
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.donecheckbox);
        checkBox.setVisibility(View.INVISIBLE);

        // get the text from the edittextfields and set them to the variables so the activity can
        // access them.

        description.setText(Description);
        title.setText(Title);

    }


    public String getTitle() {

        Title = title.getText().toString();
        return Title;
    }

    public String getDescription() {

        Description = description.getText().toString();
        return Description;
    }


}
