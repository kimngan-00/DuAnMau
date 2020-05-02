package com.example.duanmau.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.duanmau.Activity.LoginActivity;
import com.example.duanmau.DAOs.UserDAO;
import com.example.duanmau.Model.Firebase_CallBack;
import com.example.duanmau.Model.User;
import com.example.duanmau.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoiMatKhauFragment extends Fragment {
    View view;
    private EditText edt_mkCu, edt_mkMoi1, edt_mkMoi2;
    private Button btnDoiMK;
    FirebaseUser currentUser;
    private UserDAO userDAO;

    public DoiMatKhauFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
        userDAO = new UserDAO(getContext(), this);
        init();

        // Inflate the layout for this fragment
        return view;
    }

    private void init() {
        edt_mkCu = (EditText) view.findViewById(R.id.fDoiMK_edtOldPass);
        edt_mkMoi1 = (EditText) view.findViewById(R.id.fDoiMK_edtNewPass1);
        edt_mkMoi2 = (EditText) view.findViewById(R.id.fDoiMK_edtNewPass2);
        btnDoiMK = (Button) view.findViewById(R.id.fDoiMK_btnDoiMK);

        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser = FirebaseAuth.getInstance().getCurrentUser();

                AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(), edt_mkCu.getText().toString());
                currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    String mk_moi = edt_mkMoi2.getText().toString();

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (edt_mkMoi1.getText().toString().equals(mk_moi)) {
                            currentUser.updatePassword(mk_moi)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                userDAO.updatePass(currentUser.getUid(), mk_moi);
                                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(getActivity(), "Xin mời xác nhận lại mật khẩu mới!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }

}
