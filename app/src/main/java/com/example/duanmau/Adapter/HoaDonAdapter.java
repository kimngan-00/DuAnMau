package com.example.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.duanmau.Model.HoaDon;
import com.example.duanmau.Model.Sach;
import com.example.duanmau.R;

import java.util.List;

public class HoaDonAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<HoaDon> hoaDonList;

    public HoaDonAdapter(Context context, int layout, List<HoaDon> hoaDonList) {
        this.context = context;
        this.layout = layout;
        this.hoaDonList = hoaDonList;
    }

    @Override
    public int getCount() {
        return hoaDonList.size();
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
        TextView maHD;
        TextView ngayXuat;
        TextView tongTien;
        TextView nguoiXuat;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            viewHolder = new ViewHolder();

            viewHolder.maHD = (TextView) convertView.findViewById(R.id.raw_hoadon_txtMaHD);
            viewHolder.ngayXuat = (TextView) convertView.findViewById(R.id.raw_hoadon_txtNgayXuat);
            viewHolder.tongTien = (TextView) convertView.findViewById(R.id.raw_hoadon_txtTongTien);
            viewHolder.nguoiXuat = (TextView) convertView.findViewById(R.id.raw_hoadon_txtNguoiXuat);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HoaDon hoaDon = hoaDonList.get(position);

        viewHolder.maHD.setText(hoaDon.getId());
        viewHolder.ngayXuat.setText(hoaDon.getNgayXuat());
        viewHolder.tongTien.setText(String.valueOf(hoaDon.getTongTien()));
        viewHolder.nguoiXuat.setText(hoaDon.getNguoiXuat());

        return convertView;
    }
}
