package com.example.duanmau.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.duanmau.Activity.LoginActivity;
import com.example.duanmau.Activity.SignUpActivity;
import com.example.duanmau.Adapter.NhanVienAdapter;
import com.example.duanmau.DAOs.UserDAO;
import com.example.duanmau.Model.Firebase_CallBack;
import com.example.duanmau.Model.User;
import com.example.duanmau.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NhanVienFragment extends Fragment {
    View view;
    private ListView lvNhanVien;
    private FloatingActionButton FAB_themNhanVien;
    private UserDAO userDAO;
    private NhanVienAdapter nhanVienAdapter;
    private FirebaseAuth mAuth;


    public NhanVienFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nhan_vien, container, false);
        init();
        userDAO = new UserDAO(getContext(), this);
        userDAO.getData(new Firebase_CallBack() {
            @Override
            public void getUser(final List<User> userList) {
                nhanVienAdapter = new NhanVienAdapter(getContext(), R.layout.raw_user_list, userList);
                lvNhanVien.setAdapter(nhanVienAdapter);

                lvNhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final String email, password;
                        final User user = userList.get(position);
                        email = user.getEmail();
                        password = user.getPassword();

                        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getActivity());
                        dialogDelete.setMessage("Bạn muốn xóa nhân viên " + user.getDisplayName() + "?");
                        dialogDelete.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userDAO.delete(user.getIdUser());
                                deleteNhanVien(email, password);
                            }
                        });

                        dialogDelete.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialogDelete.show();

                    }
                });
                super.getUser(userList);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void init() {
        lvNhanVien = (ListView) view.findViewById(R.id.fNhanVien_lvNhanVien);
        FAB_themNhanVien = (FloatingActionButton) view.findViewById(R.id.fNhanVien_FAB_Add);

        FAB_themNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    private void deleteNhanVien(String email, String password) {
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), "Đã xóa nhân viên thành công", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // ...
                        }

                    }
                });

        //Dang nhap lai tai khoan admin
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential("ngan@gmail.com", "123456");

        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });

    }

}
