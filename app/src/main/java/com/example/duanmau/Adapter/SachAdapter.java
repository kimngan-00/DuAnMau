package com.example.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duanmau.Model.Sach;
import com.example.duanmau.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SachAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Sach> sachList;

    public SachAdapter(Context context, int layout, List<Sach> sachList) {
        this.context = context;
        this.layout = layout;
        this.sachList = sachList;
    }


    @Override
    public int getCount() {
        return sachList.size();
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
        TextView txtTenSach,txtTheLoai,txtTacGia,txtDonGia,txtSoLuong;
        ImageView imgSach;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            viewHolder=new ViewHolder();

            viewHolder.txtTenSach     =(TextView) convertView.findViewById(R.id.raw_sach_txtTenSach);
            viewHolder.txtTheLoai     =(TextView) convertView.findViewById(R.id.raw_sach_txtTheLoai);
            viewHolder.txtTacGia      =(TextView) convertView.findViewById(R.id.raw_sach_txtTacGia);
            viewHolder.txtDonGia      =(TextView) convertView.findViewById(R.id.raw_sach_txtDonGia);
            viewHolder.txtSoLuong     =(TextView) convertView.findViewById(R.id.raw_sach_txtSoLuong);
            viewHolder.imgSach        =(ImageView) convertView.findViewById(R.id.raw_sach_ivIcon);

            convertView.setTag(viewHolder);
        } else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        final Sach sach=sachList.get(position);

        Picasso.get().load(sach.getUriImgSach()).into(viewHolder.imgSach);
        viewHolder.txtTenSach.setText(sach.getTenSach());
        viewHolder.txtTheLoai.setText(sach.getTheLoai());
        viewHolder.txtTacGia.setText("Tác giả: "+sach.getTacGia());
        viewHolder.txtDonGia.setText("Giá tiền: "+ sach.getGiaTien());
        viewHolder.txtSoLuong.setText("Số lượng: "+sach.getSoLuong());
        return convertView;
    }
}
