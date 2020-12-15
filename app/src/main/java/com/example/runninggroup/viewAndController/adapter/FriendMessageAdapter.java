package com.example.runninggroup.viewAndController.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runninggroup.R;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.pojo.User;

public class FriendMessageAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    User mUser;
    public FriendMessageAdapter (LayoutInflater inflater, User user) {
        mInflater = inflater;
        mUser = user;
    }
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        class ViewHolder {
            ImageView mImageView;
            TextView mTextView;
        }
        ViewHolder viewHolder1;
        switch (position) {
            case 0:

                convertView = mInflater.inflate(R.layout.helper_friend_message, null);
                viewHolder1 = new ViewHolder();
                viewHolder1.mImageView = convertView.findViewById(R.id.img);
                viewHolder1.mTextView = convertView.findViewById(R.id.msg);
                convertView.setTag(viewHolder1);
                String sex = mUser.getSex() == 1 ? "男" : "女";
                viewHolder1.mTextView.setText(sex);
                break;
            case 1:
                convertView = mInflater.inflate(R.layout.helper_friend_message, null);
                viewHolder1 = new ViewHolder();
                viewHolder1.mImageView = convertView.findViewById(R.id.img);
                viewHolder1.mTextView = convertView.findViewById(R.id.msg);
                convertView.setTag(viewHolder1);
                Team team = mUser.getTeam();
                viewHolder1.mTextView.setText(team == null ? "尚未加入跑团" : team.getCampus() + " " + team.getCollege() + " | " + team.getTeamName());


                break;

            case 2:
                convertView = mInflater.inflate(R.layout.helper_friend_message, null);
                viewHolder1 = new ViewHolder();
                viewHolder1.mImageView = convertView.findViewById(R.id.img);
                viewHolder1.mTextView = convertView.findViewById(R.id.msg);
                convertView.setTag(viewHolder1);
                viewHolder1.mTextView.setText(mUser.getLength() + "km" + " | " + mUser.getScore() + "分");

                break;



        }
        return convertView;
    }



}
