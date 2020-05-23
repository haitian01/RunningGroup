package com.example.runninggroup.viewAndController.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runninggroup.R;
import com.example.runninggroup.viewAndController.helper.GroupTaskHelper;
import com.example.runninggroup.viewAndController.helper.User;

import java.util.List;

public class GroupMemberAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    public List<User> mList;

    public GroupMemberAdapter(LayoutInflater inflater, List<User> list) {
        mInflater = inflater;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ViewHolder内部类
        class ViewHolder{
            public ImageView img;
            public TextView name;
            public TextView sex;

        }
        //判断converView是否为空
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.helper_groupmember,null);
            viewHolder=new ViewHolder();
            viewHolder.img=convertView.findViewById(R.id.img);
            viewHolder.name=convertView.findViewById(R.id.name);
            viewHolder.sex=convertView.findViewById(R.id.sex);

            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        //赋值
        viewHolder.img.setImageResource(mList.get(position).getPic());
        viewHolder.name.setText(mList.get(position).getUsername());
        viewHolder.sex.setText(mList.get(position).getSex());








        return convertView;

    }
}
