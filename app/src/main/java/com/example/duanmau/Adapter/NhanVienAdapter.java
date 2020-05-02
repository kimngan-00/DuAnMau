package com.example.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.duanmau.Model.User;
import com.example.duanmau.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NhanVienAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<User> userList;

    public NhanVienAdapter(Context context, int layout, List<User> userList) {
        this.context = context;
        this.layout = layout;
        this.userList = userList;
    }


    @Override
    public int getCount() {
        return userList.size();
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
        CircleImageView civUser;
        TextView tvDisplayName;
        TextView tvPhoneNumber;
        TextView tvEmail;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            viewHolder = new ViewHolder();
            viewHolder.civUser = (CircleImageView) convertView.findViewById(R.id.raw_user_civAvatar);
            viewHolder.tvDisplayName = (TextView) convertView.findViewById(R.id.raw_user_tvDisplayName);
            viewHolder.tvPhoneNumber = (TextView) convertView.findViewById(R.id.raw_user_tvPhoneNumber);
            viewHolder.tvEmail = (TextView) convertView.findViewById(R.id.raw_user_tvEmail);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        User user = userList.get(position);
        Picasso.get().load(user.getUriUser()).into(viewHolder.civUser);
        viewHolder.tvDisplayName.setText(user.getDisplayName());
        viewHolder.tvEmail.setText(user.getEmail());
        viewHolder.tvPhoneNumber.setText(user.getPhoneNumber());

        return convertView;
    }
}
