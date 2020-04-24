package com.example.duanmau.DAOs;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duanmau.Model.Firebase_CallBack;
import com.example.duanmau.Model.HoaDon;
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

public class HoaDonDAO {
    Context context;
    Fragment fragment;
    DatabaseReference dataHoaDon;


    public HoaDonDAO(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.dataHoaDon = FirebaseDatabase.getInstance().getReference("hoadon");
    }

    public void insertHD( String idHD,final HoaDon hoaDon) {
        hoaDon.setId(idHD);
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        hoaDon.setDate(date.getTime());
        hoaDon.setNgayXuat(dateFormat.format(date));

        dataHoaDon.child(idHD).setValue(hoaDon, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null){
                    Toast.makeText(context, "Thêm hóa đơn thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Thêm hóa đơn thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void update(String id,HoaDon hoaDon){
        dataHoaDon.child(id).setValue(hoaDon);
    }

    public void deleteHD( String idHD){
        dataHoaDon.child(idHD).removeValue();
    }

    public void getData(final Firebase_CallBack firebase_callBack){
        final List<HoaDon> hoaDonList = new ArrayList<>();
        dataHoaDon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hoaDonList.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    HoaDon hoaDon = ds.getValue(HoaDon.class);
                    hoaDonList.add(hoaDon);
                } firebase_callBack.getHoaDon(hoaDonList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
