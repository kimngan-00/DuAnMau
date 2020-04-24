package com.example.duanmau.DAOs;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duanmau.Fragment.TheLoaiFragment;
import com.example.duanmau.Model.Firebase_CallBack;
import com.example.duanmau.Model.TheLoai;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TheLoaiDAO {
    DatabaseReference dataTheLoai;
    Context context;
    String id;
    Fragment fragment;

    public TheLoaiDAO() {
    }

    public TheLoaiDAO(Context context, Fragment fragment) {
        this.dataTheLoai = FirebaseDatabase.getInstance().getReference("theloai");
        this.context = context;
        this.fragment = fragment;
    }

    public void insert(TheLoai theLoai) {
        String idTheLoai = dataTheLoai.push().getKey();
        theLoai.setId(idTheLoai);
        dataTheLoai.child(idTheLoai).setValue(theLoai, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void delete(String id) {
        dataTheLoai.child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void update(final String id, TheLoai theLoai) {
        dataTheLoai.child(id).setValue(theLoai, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    Toast.makeText(context, "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, "Cập Nhật Không Thành Công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData(final Firebase_CallBack firebase_callBack) {
        final List<TheLoai> theLoaiList = new ArrayList<>();
        dataTheLoai.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                theLoaiList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    TheLoai theLoai = ds.getValue(TheLoai.class);
                    theLoaiList.add(theLoai);
                }
                firebase_callBack.getTheLoai(theLoaiList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
