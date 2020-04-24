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
public class ThongKeNgayFragment extends Fragment {
View view;

    public ThongKeNgayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_thong_ke_ngay, container, false);

        // Inflate the layout for this fragment
        return view;
    }

}
