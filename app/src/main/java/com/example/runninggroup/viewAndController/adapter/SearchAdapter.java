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

public class SearchAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    String msg;
    public SearchAdapter(LayoutInflater inflater, String msg) {
        mInflater = inflater;
       this.msg = msg;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        class ViewHolder {
            ImageView mImageView;
            TextView mTextView;
        }
        ViewHolder viewHolder1;
        switch (position) {
            case 0:

                convertView = mInflater.inflate(R.layout.helper_friendmessage, null);
                viewHolder1 = new ViewHolder();
                viewHolder1.mImageView = convertView.findViewById(R.id.img);
                viewHolder1.mTextView = convertView.findViewById(R.id.msg);
                convertView.setTag(viewHolder1);
                viewHolder1.mTextView.setText("找跑友：" + msg);
                viewHolder1.mImageView.setImageResource(R.drawable.img_group);
                break;
            case 1:
                convertView = mInflater.inflate(R.layout.helper_friendmessage, null);
                viewHolder1 = new ViewHolder();
                viewHolder1.mImageView = convertView.findViewById(R.id.img);
                viewHolder1.mTextView = convertView.findViewById(R.id.msg);
                convertView.setTag(viewHolder1);
                viewHolder1.mTextView.setText("找跑团：" + msg);
                viewHolder1.mImageView.setImageResource(R.drawable.img_group);

                break;




        }
        return convertView;
    }



}
