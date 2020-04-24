package com.example.duanmau.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duanmau.DAOs.TheLoaiDAO;
import com.example.duanmau.Fragment.TheLoaiFragment;
import com.example.duanmau.Model.TheLoai;

import com.example.duanmau.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class TheLoaiAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<TheLoai> theLoaiList;
    TheLoaiDAO theLoaiDAO;

    public TheLoaiAdapter(Context context, int layout, List<TheLoai> theLoaiList) {
        this.context = context;
        this.layout = layout;
        this.theLoaiList = theLoaiList;
    }

    @Override
    public int getCount() {
        return theLoaiList.size();
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
        TextView tvTenTheLoai;
        TextView tvViTri;
        ImageView imgDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder = new ViewHolder();
            holder.tvViTri = (TextView) convertView.findViewById(R.id.raw_theloai_txtViTri);
            holder.tvTenTheLoai = (TextView) convertView.findViewById(R.id.raw_theloai_txtTenTheLoai);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.raw_theloai_imgDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final TheLoai theLoai = theLoaiList.get(position);

        holder.tvViTri.setText(theLoai.getViTri());
        holder.tvTenTheLoai.setText(theLoai.getTenTheLoai());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDelete(theLoai.getTenTheLoai(), theLoai.getId());
            }
        });
        return convertView;
    }


    public void dialogDelete(String s, final String id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        final DatabaseReference dataSach = FirebaseDatabase.getInstance().getReference("sach").child(id);
        dialog.setMessage("Khi xoá loại sách bất kỳ tất cả các quyển sách liên quan sẽ bị xoá hết!!!");
        dialog.setPositiveButton("XOÁ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                theLoaiDAO.delete(id);
                dataSach.removeValue();
            }
        });
        dialog.setNegativeButton("HUỶ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
}
