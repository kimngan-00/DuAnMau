package com.example.duanmau.DAOs;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duanmau.Model.Firebase_CallBack;
import com.example.duanmau.Model.HoaDonChiTiet;
import com.example.duanmau.Model.Sach;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HDCT_DAO {
    Context context;
    Fragment fragment;
    DatabaseReference dataHDCT;
    SachDAO sachDAO;


    public HDCT_DAO(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.dataHDCT = FirebaseDatabase.getInstance().getReference("hoadonchitiet");
    }

    public void insertHDCT(final String idHD,int soLuongBanDau, final HoaDonChiTiet hoaDonChiTiet) {
        //Thành Tiền HDCT
        sachDAO = new SachDAO(context, fragment);
        hoaDonChiTiet.setIdHoaDon(idHD);
        final String idHDCT = dataHDCT.push().getKey();
        hoaDonChiTiet.setThanhTien(hoaDonChiTiet.getDonGia() * hoaDonChiTiet.getSoluong());

        //insert HDCT
        hoaDonChiTiet.setIdHDCT(idHDCT);
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        hoaDonChiTiet.setLongDate(date.getTime());
        sachDAO.updateSoLuongSach(hoaDonChiTiet.getIdSach(), soLuongBanDau - hoaDonChiTiet.getSoluong());
        dataHDCT.child(idHDCT).setValue(hoaDonChiTiet, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    Toast.makeText(context, "Thêm HDCT thành công", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, "Thêm HDCT không thành công", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void deleteHDCT( String idHDCT) {
        dataHDCT.child(idHDCT).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Xóa HDCT thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Xóa HDCT không thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData(final Firebase_CallBack firebase_callBack) {
        final List<HoaDonChiTiet> hoaDonChiTietList = new ArrayList<>();
        dataHDCT.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hoaDonChiTietList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        HoaDonChiTiet hoaDonChiTiet = ds.getValue(HoaDonChiTiet.class);
                        hoaDonChiTietList.add(hoaDonChiTiet);
                }   firebase_callBack.getHDCT(hoaDonChiTietList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
