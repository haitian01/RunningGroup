package com.example.runninggroup.viewAndController.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.runninggroup.R;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.User;

public class PersonSearchAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    User mUser;
    public PersonSearchAdapter(LayoutInflater inflater, User user) {
        mInflater = inflater;
        mUser = user;
    }
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        class ViewHolder {
            RelativeLayout rela;
            ImageView mImageView,mImageView1;
            TextView mTextView;
        }
        ViewHolder viewHolder1;
        switch (position) {

            case 0:
                convertView = mInflater.inflate(R.layout.helper_person_search, null);
                viewHolder1 = new ViewHolder();
                viewHolder1.mImageView = convertView.findViewById(R.id.img);
                viewHolder1.mTextView = convertView.findViewById(R.id.msg);
                convertView.setTag(viewHolder1);
                Team team = mUser.getTeam();
                viewHolder1.mTextView.setText("添加手机联系人");


                break;

            case 1:
                convertView = mInflater.inflate(R.layout.helper_person_search, null);
                viewHolder1 = new ViewHolder();
                viewHolder1.mImageView = convertView.findViewById(R.id.img);
                viewHolder1.mTextView = convertView.findViewById(R.id.msg);
                convertView.setTag(viewHolder1);
                viewHolder1.mTextView.setText("扫一扫加好友");

                break;



        }
        return convertView;
    }



}
