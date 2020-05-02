package com.example.duanmau.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duanmau.DAOs.UserDAO;
import com.example.duanmau.Model.User;
import com.example.duanmau.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {
    private CircleImageView civUser;
    private EditText edtDisplayName, edtEmail, edtPassword, edtBirthDay, edtPhoneNumber;
    private Button btnSignUp;
    private UserDAO userDAO;
    private int CHOOSE_IMAGE_POST = 10;
    private FirebaseAuth mAuth;
    private User user;
    private String email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userDAO = new UserDAO(this);
        user = new User();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        init();
    }

    public void init() {
        civUser = (CircleImageView) findViewById(R.id.acDangNhap_civUser);
        edtDisplayName = (EditText) findViewById(R.id.acDangNhap_edtDisplayName);
        edtEmail = (EditText) findViewById(R.id.acDangNhap_edtEmail);
        edtPassword = (EditText) findViewById(R.id.acDangNhap_edtPass);
        edtBirthDay = (EditText) findViewById(R.id.acDangNhap_edtBirthDay);
        edtPhoneNumber = (EditText) findViewById(R.id.acDangNhap_edtPhoneNumber);
        btnSignUp = (Button) findViewById(R.id.acDangNhap_btnDangNhap);

        edtBirthDay.setKeyListener(null);
        edtBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgay();
            }
        });

        civUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtEmail.getText().toString();
                password = edtPassword.getText().toString();

                user.setBirthDay(edtBirthDay.getText().toString());
                user.setDisplayName(edtDisplayName.getText().toString());
                user.setEmail(edtEmail.getText().toString());
                user.setPassword(edtPassword.getText().toString());
                user.setPhoneNumber(edtPhoneNumber.getText().toString());

                if (email.isEmpty()
                        || password.isEmpty()
                        || user.getBirthDay().isEmpty()
                        || user.getPhoneNumber().isEmpty()
                        || user.getDisplayName().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Xin mời nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                if (checkPhoneNumber(user.getPhoneNumber())) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser currentUser = mAuth.getCurrentUser();
                                        String uID = currentUser.getUid();
                                        userDAO.insert(uID, user, civUser);

                                        Toast.makeText(SignUpActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();

                                        //reAuth
                                        AuthCredential credential = EmailAuthProvider.getCredential("ngan@gmail.com", "123456");
                                        currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                } else {
                    Toast.makeText(SignUpActivity.this, "Xin mời nhập đúng định dạng số điện thoại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void chonNgay() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                calendar.set(year, month, dayOfMonth);
                edtBirthDay.setText(dateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private boolean checkPhoneNumber(String phoneNumber) {
        String phonePattern10 = "0\\d{9}";
        String phonePattern11 = "0\\d{10}";
        if (phoneNumber.matches(phonePattern10) || phoneNumber.matches(phonePattern11)) {
            return true;
        } else return false;

    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent, "Chọn hình đại diện!"), CHOOSE_IMAGE_POST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CHOOSE_IMAGE_POST && data != null) {
            civUser.setImageURI(data.getData());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
