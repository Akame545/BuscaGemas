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


public class Fragment8 extends Fragment {

    private Button btSalir;
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
        btSalir=v.findViewById(R.id.btnSalir);

        btSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
}