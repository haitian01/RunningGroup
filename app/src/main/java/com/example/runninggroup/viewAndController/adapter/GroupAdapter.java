package com.example.runninggroup.viewAndController.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runninggroup.R;
import com.example.runninggroup.viewAndController.helper.GroupHelper;

import java.util.List;

public class GroupAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    public List<GroupHelper> mList;

    public GroupAdapter(LayoutInflater inflater, List<GroupHelper> list) {
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
            public TextView num;

        }
        //判断converView是否为空
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.helper_groupshelper,null);
            viewHolder=new ViewHolder();
            viewHolder.img=convertView.findViewById(R.id.img);
            viewHolder.name=convertView.findViewById(R.id.name);
            viewHolder.num=convertView.findViewById(R.id.num);

            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        //赋值
        Drawable drawable = mList.get(position).getLogo();
        if(drawable != null) viewHolder.img.setImageDrawable(drawable);
        else viewHolder.img.setImageResource(R.mipmap.defaultpic);
        viewHolder.name.setText(mList.get(position).getGroupName());
        viewHolder.num.setText(mList.get(position).getNumbers()+"");






        return convertView;

    }
}
