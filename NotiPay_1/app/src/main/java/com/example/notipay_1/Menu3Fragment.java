package com.example.notipay_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Menu3Fragment extends Fragment {

    public String barcode;
    TextView menu_3;
    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View jinsu = inflater.inflate(R.layout.fragment_menu3, container, false);

        FloatingActionButton fab = (FloatingActionButton)jinsu.findViewById(R.id.FAB);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BarScan.class);
                startActivity(intent);
            }
        });

        return jinsu;

    }
}