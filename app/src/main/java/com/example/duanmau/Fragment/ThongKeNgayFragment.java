package com.example.duanmau.Fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.duanmau.Adapter.DoanhSoAdapter;
import com.example.duanmau.DAOs.HDCT_DAO;
import com.example.duanmau.DAOs.SachDAO;
import com.example.duanmau.Model.Firebase_CallBack;
import com.example.duanmau.Model.HoaDonChiTiet;
import com.example.duanmau.Model.Sach;
import com.example.duanmau.Model.ThongKe;
import com.example.duanmau.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThongKeNgayFragment extends Fragment {
    View view;
    private EditText edtNgayBatDau, edtNgayKetThuc;
    private Button btnThongKe;
    private ListView lvThongKe;
    private HDCT_DAO hdctDao;
    private List<ThongKe> thongKeList;
    private SachDAO sachDAO;
    private DoanhSoAdapter doanhSoAdapter;


    public ThongKeNgayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thong_ke_ngay, container, false);
        sachDAO = new SachDAO(getContext(), this);
        hdctDao = new HDCT_DAO(getContext(), this);
        thongKeList = new ArrayList<>();
        init();
        // Inflate the layout for this fragment
        return view;
    }

    private void init() {
        edtNgayBatDau = (EditText) view.findViewById(R.id.fThongKeNgay_edtNgayBatDau);
        edtNgayKetThuc = (EditText) view.findViewById(R.id.fThongKeNgay_edtNgayKetThuc);
        btnThongKe = (Button) view.findViewById(R.id.fThongKeNgay_btnThongKe);
        lvThongKe = (ListView) view.findViewById(R.id.fThongKeNgay_lvThongKe);

        edtNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgay(edtNgayBatDau);
            }
        });

        edtNgayKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgay(edtNgayKetThuc);
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thongKe();
                
//                if(longDate(edtNgayBatDau.getText().toString()) < longDate(edtNgayKetThuc.getText().toString()) ){
//                    Toast.makeText(getActivity(), "Đúng", Toast.LENGTH_SHORT).show();
//                } else Toast.makeText(getActivity(), "Sai", Toast.LENGTH_SHORT).show();


            }
        });

    }

    private void chonNgay(final EditText edt) {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                calendar.set(year, month, dayOfMonth);
                edt.setText(dateFormat.format(calendar.getTime()));
            }
        }, ngay, thang, nam);
        datePickerDialog.show();
    }

    private long longDate(String ngay) {
        long milliseconds = 0;
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = f.parse(ngay);
            milliseconds = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }

    private void thongKe() {

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
                                if(hoaDonChiTiet.getLongDate()>=longDate(edtNgayBatDau.getText().toString()) &&
                                        hoaDonChiTiet.getLongDate()<=longDate(edtNgayKetThuc.getText().toString()) ){
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
//                        Toast.makeText(getContext(),thongKeList.size()+ "", Toast.LENGTH_SHORT).show();
                        doanhSoAdapter = new DoanhSoAdapter(getContext(),R.layout.raw_top10_doanhso,thongKeList);
                        lvThongKe.setAdapter(doanhSoAdapter);
                        super.getSach(sachList);
                    }
                });

            }
        });
    }
}
