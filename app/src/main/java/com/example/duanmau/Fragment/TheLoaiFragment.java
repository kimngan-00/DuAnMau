package com.example.duanmau.Fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.duanmau.Adapter.TheLoaiAdapter;
import com.example.duanmau.DAOs.TheLoaiDAO;
import com.example.duanmau.Model.Firebase_CallBack;
import com.example.duanmau.Model.TheLoai;
import com.example.duanmau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TheLoaiFragment extends Fragment {
    View view;
    private TheLoaiDAO theLoaiDAO;
    private FloatingActionButton fab_Add;
    private ListView lvTheLoai;
    private TheLoaiAdapter theLoaiAdapter;
    private Button btnAdd, btnNew, btnExit;
    private EditText edTenTheLoai, edViTri;

    public TheLoaiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_the_loai, container, false);
        theLoaiDAO = new TheLoaiDAO(getActivity(), this);

        lvTheLoai = (ListView) view.findViewById(R.id.lv_theloai);
        fab_Add = (FloatingActionButton) view.findViewById(R.id.fTheLoai_FAB_Add);
        fab_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAdd();

            }
        });

        theLoaiDAO.getData(new Firebase_CallBack() {
            @Override
            public void getTheLoai(List<TheLoai> theLoaiList) {

                theLoaiAdapter = new TheLoaiAdapter(getActivity(), R.layout.raw_the_loai, theLoaiList);
                lvTheLoai.setAdapter(theLoaiAdapter);
            }
        });

        return view;
    }


    public void dialogAdd() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_them_the_loai);

        edViTri = (EditText) dialog.findViewById(R.id.dThemTheLoai_edtViTri);
        edTenTheLoai = (EditText) dialog.findViewById(R.id.dThemTheLoai_edtTenTheLoai);
        btnAdd = (Button) dialog.findViewById(R.id.dThemTheLoai_btnAdd);
        btnNew = (Button) dialog.findViewById(R.id.dThemTheLoai_btnNew);
        btnExit = (Button) dialog.findViewById(R.id.dThemTheLoai_btnCancel);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edTenTheLoai.setText("");
                edViTri.setText("");
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTheLoai = edTenTheLoai.getText().toString();
                String viTri = edViTri.getText().toString();
                if (tenTheLoai.isEmpty() || viTri.isEmpty()) {
                    Toast.makeText(getActivity(), "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    TheLoai theLoai = new TheLoai();
                    theLoai.setTenTheLoai(tenTheLoai);
                    theLoai.setViTri(viTri);
                    theLoaiDAO.insert(theLoai);

                    dialog.dismiss();
                }

            }
        });
        dialog.show();
    }


}
