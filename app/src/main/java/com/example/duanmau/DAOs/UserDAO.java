package com.example.duanmau.DAOs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.duanmau.Model.Firebase_CallBack;
import com.example.duanmau.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDAO {
    Context context;
    Fragment fragment;
    DatabaseReference dataUser;
    FirebaseUser currentUser;

    public UserDAO(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.dataUser = FirebaseDatabase.getInstance().getReference("user");

    }

    public UserDAO(Context context) {
        this.context = context;
        this.dataUser = FirebaseDatabase.getInstance().getReference("user");
    }

    public void update(final User user, ImageView imgAvatar) {
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference("user/" + user.getIdUser());
        imgAvatar.setDrawingCacheEnabled(true);
        imgAvatar.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgAvatar.getDrawable()).getBitmap();
        ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bAOS);
        byte[] data = bAOS.toByteArray();
        final UploadTask uploadTask = storageReference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("url", uri + "");
                        user.setUriUser(uri + "");
                        dataUser.child(user.getIdUser()).setValue(user);
                    }
                });
            }
        });

    }

    public void insert(final String uID, final User user, CircleImageView avatar) {
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference("user/" + uID);
        user.setIdUser(uID);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        //Upload Avatar
        avatar.setDrawingCacheEnabled(true);
        avatar.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bAOS);
        byte[] data = bAOS.toByteArray();
        final UploadTask uploadTask = storageReference.putBytes(data);
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
                        user.setUriUser(String.valueOf(uri));
                        dataUser.child(uID).setValue(user);

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(user.getDisplayName())
                                .setPhotoUri(Uri.parse(user.getUriUser()))
                                .build();

                        currentUser.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                        }
                                    }
                                });
                    }
                });
            }
        });

    }


    public void delete(String idUser) {
        dataUser.child(idUser).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Xóa tài khoản thành công!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updatePass(final String uID, final String pass) {
        dataUser.child(uID).child("password").setValue(pass).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void getData(final Firebase_CallBack firebase_callBack) {
        final List<User> userList = new ArrayList<>();

        dataUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    userList.add(user);
                }
                firebase_callBack.getUser(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", "Không lấy được dữ liệu");
            }
        });

    }
}
