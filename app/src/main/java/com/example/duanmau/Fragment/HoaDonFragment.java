package com.example.duanmau.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.duanmau.Adapter.HoaDonAdapter;
import com.example.duanmau.DAOs.HoaDonDAO;
import com.example.duanmau.Model.Firebase_CallBack;
import com.example.duanmau.Model.HoaDon;
import com.example.duanmau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HoaDonFragment extends Fragment {
    View view;
    private ListView lvHoaDon;
    private FloatingActionButton FAB_themHoaDon;
    private HoaDonDAO hoaDonDAO;
    private HoaDonAdapter hoaDonAdapter;

    public HoaDonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hoa_don, container, false);
        hoaDonDAO = new HoaDonDAO(getContext(), this);

        init();

        hoaDonDAO.getData(new Firebase_CallBack() {
            @Override
            public void getHoaDon(List<HoaDon> hoaDonList) {
                hoaDonAdapter = new HoaDonAdapter(getContext(), R.layout.raw_hoadon, hoaDonList);
                lvHoaDon.setAdapter(hoaDonAdapter);
                super.getHoaDon(hoaDonList);
            }
        });

        return view;
    }

    public void init() {
        lvHoaDon = (ListView) view.findViewById(R.id.fHoaDon_lvHoaDon);
        FAB_themHoaDon = (FloatingActionButton) view.findViewById(R.id.fHoaDon_FAB_Add);

        FAB_themHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frameLayout, new HoaDonChiTietFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

}
