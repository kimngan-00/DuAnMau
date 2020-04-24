package com.example.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duanmau.Model.ThongKe;
import com.example.duanmau.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DoanhSoAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<ThongKe> thongKeList;

    public DoanhSoAdapter(Context context, int layout, List<ThongKe> thongKeList) {
        this.context = context;
        this.layout = layout;
        this.thongKeList = thongKeList;
    }

    @Override
    public int getCount() {
        return thongKeList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        ImageView imgSach;
        TextView txtTenSach;
        TextView txtTacGia;
        TextView txtSoLuongBanRa;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            viewHolder = new ViewHolder();

            viewHolder.imgSach = (ImageView) convertView.findViewById(R.id.raw_doanhso_imgSach);
            viewHolder.txtTacGia = (TextView) convertView.findViewById(R.id.raw_doanhso_txtTacGia);
            viewHolder.txtTenSach = (TextView) convertView.findViewById(R.id.raw_doanhso_txtTenSach);
            viewHolder.txtSoLuongBanRa = (TextView) convertView.findViewById(R.id.raw_doanhso_txtSoLuongBanRa);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ThongKe thongKe = thongKeList.get(position);

        Picasso.get().load(thongKe.getUriImgSach()).into(viewHolder.imgSach);
        viewHolder.txtSoLuongBanRa.setText(String.valueOf(thongKe.getSoLuongBanRa()));
        viewHolder.txtTenSach.setText(thongKe.getTenSach());
        viewHolder.txtTacGia.setText(thongKe.getTacGia());

        return convertView;
    }
}
