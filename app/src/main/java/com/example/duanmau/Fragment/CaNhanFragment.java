package com.example.duanmau.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.duanmau.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaNhanFragment extends Fragment {
View view;

    public CaNhanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_ca_nhan, container, false);
        // Inflate the layout for this fragment


        return view;
    }

}
