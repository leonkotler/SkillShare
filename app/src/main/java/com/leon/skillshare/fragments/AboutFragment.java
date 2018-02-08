package com.leon.skillshare.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leon.skillshare.R;


public class AboutFragment extends Fragment {

    TextView aboutText;
    TextView helpText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about,container,false);

        aboutText = view.findViewById(R.id.fragment_about_about_text);
        aboutText.setText("This is the about section...");

        helpText = view.findViewById(R.id.fragment_about_help_text);
        helpText.setText("This is the help section...");

        return view;
    }
}
