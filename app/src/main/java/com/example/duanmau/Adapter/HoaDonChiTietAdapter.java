package com.example.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.duanmau.Model.HoaDonChiTiet;
import com.example.duanmau.Model.Sach;
import com.example.duanmau.R;

import java.util.List;

public class HoaDonChiTietAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<HoaDonChiTiet> hoaDonChiTietList;

    public HoaDonChiTietAdapter(Context context, int layout, List<HoaDonChiTiet> hoaDonChiTietList) {
        this.context = context;
        this.layout = layout;
        this.hoaDonChiTietList = hoaDonChiTietList;
    }

    @Override
    public int getCount() {
        return hoaDonChiTietList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView txtTenSach;
        TextView txtDonGia;
        TextView txtSoLuong;
        TextView txtThanhTien;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder = new ViewHolder();

            viewHolder.txtDonGia = (TextView) convertView.findViewById(R.id.raw_hdct_txtDonGia);
            viewHolder.txtTenSach = (TextView) convertView.findViewById(R.id.raw_hdct_txtTenSach);
            viewHolder.txtSoLuong = (TextView) convertView.findViewById(R.id.raw_hdct_txtSoLuong);
            viewHolder.txtThanhTien = (TextView) convertView.findViewById(R.id.raw_hdct_txtThanhTien);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietList.get(position);
        viewHolder.txtTenSach.setText(hoaDonChiTiet.getTenSach());
        viewHolder.txtDonGia.setText(String.valueOf(hoaDonChiTiet.getDonGia()));
        viewHolder.txtThanhTien.setText(String.valueOf(hoaDonChiTiet.getThanhTien()));
        viewHolder.txtSoLuong.setText(String.valueOf(hoaDonChiTiet.getSoluong()));

        return convertView;
    }
}
