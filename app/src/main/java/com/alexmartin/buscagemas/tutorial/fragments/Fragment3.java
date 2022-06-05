package com.alexmartin.buscagemas.tutorial.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alexmartin.buscagemas.MainActivity;
import com.alexmartin.buscagemas.R;

public class Fragment3 extends Fragment {
    private View v;


    public Fragment3() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.fragment_3, container, false);



        // Inflate the layout for this fragment
        return v;
    }
}