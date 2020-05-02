package com.example.duanmau.Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanmau.Adapter.HoaDonChiTietAdapter;
import com.example.duanmau.DAOs.HDCT_DAO;
import com.example.duanmau.DAOs.HoaDonDAO;
import com.example.duanmau.DAOs.SachDAO;
import com.example.duanmau.Model.Firebase_CallBack;
import com.example.duanmau.Model.HoaDon;
import com.example.duanmau.Model.HoaDonChiTiet;
import com.example.duanmau.Model.Sach;
import com.example.duanmau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HoaDonChiTietFragment extends Fragment {
    View view;
    private TextView txtTongTien;
    private FloatingActionButton FAB_ThemHDCT;
    private ListView lvHDCT;
    private EditText d_edSoLuong;
    private AutoCompleteTextView d_aCTenSach;
    private Button d_btnThem, d_btnHuy, d_btnLamMoi;
    private Button btnThem, btnHuy, btnLamMoi;
    private HoaDonDAO hoaDonDAO;
    private HDCT_DAO hdctDao;
    private SachDAO sachDAO;
    private HoaDon hoaDon;
    private HoaDonChiTiet hoaDonChiTiet;
    private String idHD;
    private ArrayAdapter<String> aC_adapter;
    private long tongTienHD = 0;
    private int soLuongBanDau;
    private HoaDonChiTietAdapter hoaDonChiTietAdapter;
    DatabaseReference dataHoaDon;
    private FirebaseUser currentUser;


    public HoaDonChiTietFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hoa_don_chi_tiet, container, false);
        dataHoaDon = FirebaseDatabase.getInstance().getReference("hoadon");
        hoaDonDAO = new HoaDonDAO(getActivity(), this);
        hdctDao = new HDCT_DAO(getActivity(), this);
        sachDAO = new SachDAO(getActivity(), this);
        hoaDon = new HoaDon();
        hoaDonChiTiet = new HoaDonChiTiet();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        init();

        hdctDao.getData(new Firebase_CallBack() {
            final List<HoaDonChiTiet> hoaDonChiTietList1 = new ArrayList<>();

            @Override
            public void getHDCT(List<HoaDonChiTiet> hoaDonChiTietList) {
                hoaDonChiTietList1.clear();
                for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietList) {
                    if (hoaDonChiTiet.getIdHoaDon().equalsIgnoreCase(idHD)) {
                        hoaDonChiTietList1.add(hoaDonChiTiet);
                        hoaDonChiTietAdapter = new HoaDonChiTietAdapter(getContext(), R.layout.raw_hd_chi_tiet, hoaDonChiTietList1);
                        lvHDCT.setAdapter(hoaDonChiTietAdapter);
                    }
                }

                super.getHDCT(hoaDonChiTietList);
            }
        });

        return view;
    }

    public void init() {
        //anhxa
        txtTongTien = view.findViewById(R.id.fTaoHoaDon_txtTongTien);
        FAB_ThemHDCT = view.findViewById(R.id.fTaoHoaDon_FAB_Add);
        lvHDCT = view.findViewById(R.id.fTaoHoaDon_lvChiTietHD);
        btnThem = view.findViewById(R.id.fTaoHoaDon_btnAdd);
        btnHuy = view.findViewById(R.id.fTaoHoaDon_btnCancel);
        btnLamMoi = view.findViewById(R.id.fTaoHoaDon_btnNew);

        //thông tin hóa đơn cơ bản
        idHD = dataHoaDon.push().getKey();

        //sự kiện onClick
        FAB_ThemHDCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHDCT();
            }
        });
        btnLamMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new HoaDonFragment())
                        .commit();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoaDon.setNguoiXuat(currentUser.getDisplayName());
                hoaDon.setTongTien(tongTienHD);
                hoaDonDAO.insertHD(idHD, hoaDon);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new HoaDonFragment())
                        .commit();
            }
        });


    }

    public void addHDCT() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_them_hdct);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //anhxa
        d_edSoLuong = dialog.findViewById(R.id.dialog_themHDCT_edtSoLuong);
        d_aCTenSach = dialog.findViewById(R.id.dialog_themHDCT_aCTenSach);
        d_btnHuy = dialog.findViewById(R.id.dialog_ThemHDCT_btnCancel);
        d_btnLamMoi = dialog.findViewById(R.id.dialog_ThemHDCT_btnNew);
        d_btnThem = dialog.findViewById(R.id.dialog_ThemHDCT_btnAdd);


        //autocompleteTextView
        sachDAO.getData(new Firebase_CallBack() {
            final List<String> d_tenSachList = new ArrayList<>();

            @Override
            public void getSach(List<Sach> sachList) {
                for (Sach sach : sachList) {
                    d_tenSachList.add(sach.getTenSach());
                }
                aC_adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, d_tenSachList);
                d_aCTenSach.setAdapter(aC_adapter);
                d_aCTenSach.setThreshold(1);
            }
        });

        d_aCTenSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String tenSach = d_aCTenSach.getText().toString();
                sachDAO.getData(new Firebase_CallBack() {
                    @Override
                    public void getSach(List<Sach> sachList) {
                        super.getSach(sachList);
                        for (Sach sach : sachList) {
                            if (tenSach.equalsIgnoreCase(sach.getTenSach())) {
                                hoaDonChiTiet.setDonGia(sach.getGiaTien());
                                hoaDonChiTiet.setIdSach(sach.getId());
                                soLuongBanDau = sach.getSoLuong();
                            }
                        }
                    }
                });
            }
        });

        //onClick sự kiện
        d_btnLamMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d_edSoLuong.setText("");
                d_aCTenSach.setText("");
            }
        });

        d_btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        d_btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = Integer.parseInt(d_edSoLuong.getText().toString());
                String tenSach = d_aCTenSach.getText().toString();
                if (soLuong < 0 || tenSach.isEmpty()) {
                    Toast.makeText(getActivity(), "Xin mời nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    hoaDonChiTiet.setSoluong(soLuong);
                    hoaDonChiTiet.setTenSach(tenSach);
                    if (soLuong > soLuongBanDau) {
                        Toast.makeText(getActivity(), "Số lượng sách trong kho chỉ còn " + soLuongBanDau, Toast.LENGTH_SHORT).show();
                    } else {
                        hdctDao.insertHDCT(idHD, soLuongBanDau, hoaDonChiTiet);
                        tongTienHD += hoaDonChiTiet.getThanhTien();
                        txtTongTien.setText(tongTienHD + "");
                        dialog.dismiss();
                    }
                }
            }
        });


        dialog.show();
    }
}
