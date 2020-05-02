package com.example.duanmau.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.duanmau.Adapter.DoanhSoAdapter;
import com.example.duanmau.DAOs.HDCT_DAO;
import com.example.duanmau.DAOs.SachDAO;
import com.example.duanmau.Model.Firebase_CallBack;
import com.example.duanmau.Model.HoaDonChiTiet;
import com.example.duanmau.Model.Sach;
import com.example.duanmau.Model.ThongKe;
import com.example.duanmau.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThongKeDoanhSoFragment extends Fragment {
    View view;
    private ListView lvSach;
    private HDCT_DAO hdctDao;
    private List<ThongKe> thongKeList;
    private SachDAO sachDAO;
    private DoanhSoAdapter doanhSoAdapter;

    public ThongKeDoanhSoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thong_ke_doanh_so, container, false);
        hdctDao = new HDCT_DAO(getContext(), this);
        sachDAO = new SachDAO(getContext(), this);
        thongKeList = new ArrayList<>();
        init();

        return view;
    }

    private void init() {
        lvSach = view.findViewById(R.id.fDoanhSo_lvSach);

        sachDAO.getData(new Firebase_CallBack(){
            @Override
            public void getSach(final List<Sach> sachList) {
                        hdctDao.getData(new Firebase_CallBack() {
            Set<String> idSachHSet = new HashSet<>();
            @Override
            public void getHDCT(List<HoaDonChiTiet> hoaDonChiTietList) {
                for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietList) {
                    idSachHSet.add(hoaDonChiTiet.getIdSach());
                }

                for (final String idSach : idSachHSet) {
                    final ThongKe thongKe = new ThongKe();
                    for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietList) {
                        if (hoaDonChiTiet.getIdSach().equalsIgnoreCase(idSach)) {
                            thongKe.setIdSach(idSach);
                            thongKe.setSoLuongBanRa(thongKe.getSoLuongBanRa() + hoaDonChiTiet.getSoluong());
                            for (Sach sach:sachList){
                                if(idSach.equalsIgnoreCase(sach.getId())){
                                    thongKe.setTacGia(sach.getTacGia());
                                    thongKe.setUriImgSach(sach.getUriImgSach());
                                    thongKe.setTenSach(sach.getTenSach());
                                }
                            }
                        }
                    }
                    thongKeList.add( thongKe);

                    Collections.sort(thongKeList, new Comparator<ThongKe>() {
                        @Override
                        public int compare(ThongKe o1, ThongKe o2) {
                            if (o1.getSoLuongBanRa() > o2.getSoLuongBanRa()) {
                                return -1;
                            } else
                                return 0;
                        }
                    });
                }

                if(thongKeList.size()<=10){
                    doanhSoAdapter = new DoanhSoAdapter(getContext(),R.layout.raw_top10_doanhso,thongKeList);
                    lvSach.setAdapter(doanhSoAdapter);
                }else {
                    List<ThongKe> thongKeList2 = new ArrayList<>();
                    for (int i=0;i<10;i++){
                        thongKeList2.add(thongKeList.get(i));
                        doanhSoAdapter = new DoanhSoAdapter(getContext(),R.layout.raw_top10_doanhso,thongKeList2);
                        lvSach.setAdapter(doanhSoAdapter);
                    }

                }
                super.getSach(sachList);
            }
        });

            }
        });

    }

}
