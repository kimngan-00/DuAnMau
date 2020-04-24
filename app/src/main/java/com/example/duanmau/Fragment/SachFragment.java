package com.example.duanmau.Fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duanmau.Adapter.SachAdapter;
import com.example.duanmau.DAOs.SachDAO;
import com.example.duanmau.DAOs.TheLoaiDAO;
import com.example.duanmau.Model.Firebase_CallBack;
import com.example.duanmau.Model.Sach;
import com.example.duanmau.Model.TheLoai;
import com.example.duanmau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SachFragment extends Fragment {
    private SachAdapter sachAdapter;
    View view;
    private Spinner spinner;
    private FloatingActionButton fabAdd;
    private TheLoaiDAO theLoaiDAO;
    private SachDAO sachDAO;
    private ListView lvSach;
    private ArrayAdapter<String> adapterSpinner;
    private EditText edtTenSach, edtTacGia, edtDonGia, edtSoLuong;
    private Button btnAdd, btnNew, btnCancel;
    private Spinner dThemSach_spinnerTheLoai;
    private ImageView imgSach;
    private int CHOOSE_IMAGE_POST = 10;


    public SachFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sach, container, false);

        sachDAO = new SachDAO(getActivity(), this);
        theLoaiDAO = new TheLoaiDAO(getActivity(), this);
        init();
        dataByGenres();

        sachDAO.getData(new Firebase_CallBack() {
            @Override
            public void getSach(List<Sach> sachList) {
                sachAdapter = new SachAdapter(getActivity(), R.layout.raw_sach, sachList);
                lvSach.setAdapter(sachAdapter);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    public void init() {
        lvSach = (ListView) view.findViewById(R.id.lv_sach);
        spinner = (Spinner) view.findViewById(R.id.fSach_Spinner_TheLoai);
        fabAdd = (FloatingActionButton) view.findViewById(R.id.fSach_FAB_Add);


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAdd();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String theLoai = spinner.getSelectedItem().toString();

                sachDAO.getData(new Firebase_CallBack() {
                    @Override
                    public void getSach(List<Sach> sachList) {
                        List<Sach> sachByTheLoaiList = new ArrayList<>();
                        for (Sach sach : sachList) {
                            if (sach.getTheLoai().equalsIgnoreCase(theLoai)) {
                                sachByTheLoaiList.add(sach);
                            }
                        }
                        if (theLoai.equalsIgnoreCase("Xem tất cả")) {
                            sachAdapter = new SachAdapter(getActivity(), R.layout.raw_sach, sachList);
                        } else {
                            sachAdapter = new SachAdapter(getActivity(), R.layout.raw_sach, sachByTheLoaiList);
                        }
                        lvSach.setAdapter(sachAdapter);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void dataByGenres() {
        theLoaiDAO.getData(new Firebase_CallBack() {
            final List<String> tenTheLoaiList = new ArrayList<>();

            @Override
            public void getTheLoai(List<TheLoai> theLoaiList) {
                for (TheLoai theLoai : theLoaiList) {
                    tenTheLoaiList.add(theLoai.getTenTheLoai());
                }
                tenTheLoaiList.add(0, "Xem tất cả");
                adapterSpinner = new ArrayAdapter<>(getActivity(), R.layout.drop_down_spinner, tenTheLoaiList);
                spinner.setAdapter(adapterSpinner);
            }
        });
    }


    public void dialogAdd() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_them_sach);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        imgSach = (ImageView) dialog.findViewById(R.id.dThemSach_imgSach);
        edtTenSach = (EditText) dialog.findViewById(R.id.dThemSach_edtTenSach);
        edtTacGia = (EditText) dialog.findViewById(R.id.dThemSach_edtTacGia);
        edtDonGia = (EditText) dialog.findViewById(R.id.dThemSach_edtDonGia);
        edtSoLuong = (EditText) dialog.findViewById(R.id.dThemSach_edtSoLuong);
        btnAdd = (Button) dialog.findViewById(R.id.dThemSach_btnAdd);
        btnNew = (Button) dialog.findViewById(R.id.dThemSach_btnNew);
        btnCancel = (Button) dialog.findViewById(R.id.dThemSach_btnCancel);
        dThemSach_spinnerTheLoai = (Spinner) dialog.findViewById(R.id.dThemSach_spnTheLoai);

        theLoaiDAO.getData(new Firebase_CallBack() {
            final List<String> d_tenTheLoaiList = new ArrayList<>();

            @Override
            public void getTheLoai(List<TheLoai> theLoaiList) {

                for (TheLoai theLoai : theLoaiList) {
                    d_tenTheLoaiList.add(theLoai.getTenTheLoai());
                }
                adapterSpinner = new ArrayAdapter<>(getActivity(), R.layout.drop_down_spinner, d_tenTheLoaiList);
                dThemSach_spinnerTheLoai.setAdapter(adapterSpinner);
            }
        });

        imgSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTenSach.setText("");
                edtTacGia.setText("");
                edtSoLuong.setText("");
                edtDonGia.setText("");
                imgSach.setImageResource(R.drawable.add_image);

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = edtTenSach.getText().toString();
                String theLoai = dThemSach_spinnerTheLoai.getSelectedItem().toString();
                String tacGia = edtTacGia.getText().toString();
                int soLuong = Integer.parseInt(edtSoLuong.getText().toString());
                int giaTien = Integer.parseInt(edtDonGia.getText().toString());

                if (edtSoLuong.getText().toString().isEmpty()
                        || giaTien < 0
                        || soLuong < 0
                        || tenSach.isEmpty()
                        || theLoai.isEmpty()
                        || tacGia.isEmpty()
                ) {
                    Toast.makeText(getActivity(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Sach sach = new Sach();
                    sach.setSoLuong(soLuong);
                    sach.setGiaTien(giaTien);
                    sach.setTacGia(tacGia);
                    sach.setTenSach(tenSach);
                    sach.setTheLoai(theLoai);
                    sachDAO.insert(sach, imgSach);
                    dialog.dismiss();
                }
            }

        });

        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CHOOSE_IMAGE_POST && data != null) {
            imgSach.setImageURI(data.getData());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent, "Chọn hình sách!"), CHOOSE_IMAGE_POST);
    }


}
