package com.example.runninggroup.viewAndController.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.runninggroup.viewAndController.helper.FriendsHelper;
import com.example.runninggroup.R;

import java.util.List;

public class FriendsAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    public List<FriendsHelper> mList;

    public FriendsAdapter(LayoutInflater inflater, List<FriendsHelper> list) {
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
            public TextView group;
            public TextView length;
            public TextView score;
        }
        //判断converView是否为空
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.helper_friendshelper,null);
            viewHolder=new ViewHolder();
            viewHolder.img=convertView.findViewById(R.id.img);
            viewHolder.name=convertView.findViewById(R.id.name);
            viewHolder.group=convertView.findViewById(R.id.group);
            viewHolder.length=convertView.findViewById(R.id.length);
            viewHolder.score=convertView.findViewById(R.id.score);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        //赋值
        viewHolder.img.setImageResource(mList.get(position).getPic());
        viewHolder.name.setText(mList.get(position).getName());
        viewHolder.group.setText(mList.get(position).getGroup());
        viewHolder.length.setText(mList.get(position).getLength()+"");
        viewHolder.score.setText(mList.get(position).getScore()+"");






        return convertView;

    }
}
