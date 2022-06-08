package com.alexmartin.buscagemas.tutorial.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexmartin.buscagemas.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment8#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment8 extends Fragment {


    public Fragment8() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_8, container, false);
       return v;
    }
}