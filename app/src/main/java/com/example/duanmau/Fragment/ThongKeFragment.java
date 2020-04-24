package com.example.duanmau.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.duanmau.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThongKeFragment extends Fragment {
    View view;
    BottomNavigationView bottomNavigationView;

    public ThongKeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thong_ke, container, false);

        bottomNavigationView = view.findViewById(R.id.bottom_all_thongke);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_thongke_date:
                        getChildFragmentManager().beginTransaction().replace(R.id.frame_all_thongke, new ThongKeNgayFragment()).commit();
                        break;

                    case R.id.bottom_thongke_doanhso:
                        getChildFragmentManager().beginTransaction().replace(R.id.frame_all_thongke, new ThongKeDoanhSoFragment()).commit();
                        break;
                }
                return true;
            }
        });

        return view;
    }

}
