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
public class NhanVienFragment extends Fragment {
View view;

    public NhanVienFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_nhan_vien, container, false);

        // Inflate the layout for this fragment
        return view;
    }

}
