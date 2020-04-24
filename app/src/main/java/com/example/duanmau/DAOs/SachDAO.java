package com.example.duanmau.DAOs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duanmau.Fragment.SachFragment;
import com.example.duanmau.Model.Firebase_CallBack;
import com.example.duanmau.Model.Sach;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    DatabaseReference dataSach;
    Context context;
    String idTheLoai;
    Fragment fragment;

    public SachDAO() {
    }

    public SachDAO(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.dataSach = FirebaseDatabase.getInstance().getReference("sach");
    }

    public void insert(final Sach sach, ImageView imageView) {
        if (sach.getTenSach().isEmpty()
                || sach.getTheLoai().isEmpty()
                || sach.getTacGia().isEmpty()
                || sach.getSoLuong() <= 0
                || sach.getGiaTien() <= 0) {
            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            final String id = dataSach.push().getKey();
            final StorageReference storageReference = FirebaseStorage.getInstance()
                    .getReference("Post/" + id);
            sach.setId(id);
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bAOS);
            byte[] data = bAOS.toByteArray();
            UploadTask uploadTask = storageReference.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            sach.setUriImgSach(String.valueOf(uri));
                            dataSach.child(id).setValue(sach);
                        }
                    });
                }
            });

            }
        }


    public void delete(String idTheLoai, String idSach) {
        dataSach.child(idTheLoai).child(idSach).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public void update(String idSach, Sach sach) {
        dataSach.child(idSach).setValue(sach, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    Toast.makeText(context, "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, "Cập Nhật Không Thành Công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateSoLuongSach(String idSach, int soLuong) {
        dataSach.child(idSach).child("soLuong").setValue(soLuong);
    }

    public void getData(final Firebase_CallBack firebase_callBack) {
        final List<Sach> sachList = new ArrayList<>();

        dataSach.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sachList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Sach sach = ds.getValue(Sach.class);
                    sachList.add(sach);
                }
                firebase_callBack.getSach(sachList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    public List<Sach> getDatabyTheLoai(String tenTheLoai) {
//        final List<Sach> sachList = new ArrayList<>();
//
//        DatabaseReference dataTheLoai = FirebaseDatabase.getInstance().getReference("theloai");
//        Query query = dataTheLoai.orderByChild("tenTheLoai").equalTo(tenTheLoai);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    idTheLoai = ds.getKey();
//                }
//
//                dataSach.child(idTheLoai).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        sachList.clear();
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                            Sach sach = ds.getValue(Sach.class);
//                            sachList.add(sach);
//                        }
//                        ((SachFragment) fragment).refresh();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        return sachList;
//    }
}
