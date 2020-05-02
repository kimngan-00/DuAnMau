package com.example.duanmau.Fragment;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duanmau.DAOs.UserDAO;
import com.example.duanmau.Model.Firebase_CallBack;
import com.example.duanmau.Model.User;
import com.example.duanmau.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaNhanFragment extends Fragment {
    private View view;
    private TextView tvDisplayName, tvBirthDay, tvEmail, tvPhoneNumber;
    private Button btnUpdate, d_btnUpdate;
    private EditText d_edtDisplayName, d_edtBirthDay, d_edtPhoneNumber;
    private UserDAO userDAO;
    FirebaseUser currentUser;
    private CircleImageView civUser, d_civUser;
    private int CHOOSE_IMAGE_POST = 1;
    private User update;


    public CaNhanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ca_nhan, container, false);
        userDAO = new UserDAO(getContext(), this);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        // Inflate the layout for this fragment
        init();



        return view;
    }

    public void init() {
        tvDisplayName = (TextView) view.findViewById(R.id.fCaNhan_tvDisplayName);
        tvBirthDay = (TextView) view.findViewById(R.id.fCaNhan_tvBirthDay);
        tvEmail = (TextView) view.findViewById(R.id.fCaNhan_tvEmail);
        tvPhoneNumber = (TextView) view.findViewById(R.id.fCaNhan_tvPhoneNumber);
        btnUpdate = (Button) view.findViewById(R.id.fCaNhan_btnUpdate);
        civUser = (CircleImageView) view.findViewById(R.id.fCaNhan_civUser);

        userDAO.getData(new Firebase_CallBack() {
            @Override
            public void getUser(List<User> userList) {
                for (User user : userList) {
                    if (currentUser.getEmail().equalsIgnoreCase(user.getEmail())) {
                        Picasso.get().load(user.getUriUser()).into(civUser);
                        tvEmail.setText(user.getEmail());
                        tvBirthDay.setText(user.getBirthDay());
                        tvDisplayName.setText(user.getDisplayName());
                        tvPhoneNumber.setText(user.getPhoneNumber());
                        update = user;
                        break;
                    }
                }
                super.getUser(userList);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUpdate();
            }
        });
    }

    public void dialogUpdate() {
        final Dialog dialogUpdate = new Dialog(getContext());
        dialogUpdate.setContentView(R.layout.dialog_update_profile);
        dialogUpdate.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Anh xa
        d_edtBirthDay = (EditText) dialogUpdate.findViewById(R.id.dUpdateProfile_edtBirthDay);
        d_edtDisplayName = (EditText) dialogUpdate.findViewById(R.id.dUpdateProfile_edtDisplayName);
        d_edtPhoneNumber = (EditText) dialogUpdate.findViewById(R.id.dUpdateProfile_edtPhoneNumber);
        d_btnUpdate = (Button) dialogUpdate.findViewById(R.id.dUpdateProfile_btnUpdate);
        d_civUser = (CircleImageView) dialogUpdate.findViewById(R.id.dUpdateProfile_civUser);

        //Set thong tin ca nhan
        Picasso.get().load(currentUser.getPhotoUrl()).into(d_civUser);
        d_edtPhoneNumber.setText(tvPhoneNumber.getText().toString());
        d_edtDisplayName.setText(tvDisplayName.getText().toString());
        d_edtBirthDay.setText(tvBirthDay.getText().toString());

        //edit thong tin

        d_civUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        d_edtBirthDay.setKeyListener(null);
        d_edtBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgay();
            }
        });

        d_btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPhoneNumber(d_edtPhoneNumber.getText().toString())){
                    final String uID = currentUser.getUid();
                    update.setPhoneNumber(d_edtPhoneNumber.getText().toString());
                    update.setDisplayName(d_edtDisplayName.getText().toString());
                    update.setBirthDay(d_edtBirthDay.getText().toString());
                    update.setEmail(currentUser.getEmail());
                    userDAO.update(update,d_civUser);
                    dialogUpdate.dismiss();

                } else {
                    Toast.makeText(getActivity(), "Xin mời nhập đúng số điện thoại", Toast.LENGTH_SHORT).show();
                }
                 }
        });
        dialogUpdate.show();
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent, "Chọn hình đại diện!"), CHOOSE_IMAGE_POST);
    }

    public void chonNgay() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                calendar.set(year, month, dayOfMonth);
                d_edtBirthDay.setText(dateFormat.format(calendar.getTime()));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CHOOSE_IMAGE_POST && data != null) {
            d_civUser.setImageURI(data.getData());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

}
