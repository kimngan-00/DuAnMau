package com.example.duanmau.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.duanmau.Fragment.CaNhanFragment;
import com.example.duanmau.Fragment.DoiMatKhauFragment;
import com.example.duanmau.Fragment.HoaDonFragment;
import com.example.duanmau.Fragment.NhanVienFragment;
import com.example.duanmau.Fragment.SachFragment;
import com.example.duanmau.Fragment.TheLoaiFragment;
import com.example.duanmau.Fragment.ThongKeFragment;
import com.example.duanmau.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        initView();
    }


    private void initView() {
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Menu drawerMenu = navigationView.getMenu();
        if (currentUser.getEmail().equalsIgnoreCase("ngan@gmail.com")) {
            drawerMenu.findItem(R.id.menu_qlynhanvien).setVisible(true);
        } else drawerMenu.findItem(R.id.menu_qlynhanvien).setVisible(false);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new DoiMatKhauFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_doimk:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new DoiMatKhauFragment()).commit();
                break;

            case R.id.menu_canhan:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new CaNhanFragment()).commit();
                break;

            case R.id.menu_hoadon:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HoaDonFragment()).commit();
                break;

            case R.id.menu_qlynhanvien:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new NhanVienFragment()).commit();
                break;

            case R.id.menu_sach:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new SachFragment()).commit();
                break;

            case R.id.menu_theloai:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new TheLoaiFragment()).commit();
                break;


            case R.id.menu_thongke:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ThongKeFragment()).commit();
                break;

            case R.id.menu_dangsuat:
                Intent intent;
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
