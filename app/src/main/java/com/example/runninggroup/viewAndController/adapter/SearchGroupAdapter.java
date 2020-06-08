package com.example.runninggroup.viewAndController.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.viewAndController.helper.GroupHelper;

import java.util.HashMap;
import java.util.List;

public class SearchGroupAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    public List<GroupHelper> mList;
    public SearchGroupAdapter(LayoutInflater inflater, List<GroupHelper> list) {
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
            public TextView name;
            public TextView num;
            public TextView slogan;

        }
        //判断converView是否为空
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.helper_groupsearch,null);
            viewHolder=new ViewHolder();
            viewHolder.name=convertView.findViewById(R.id.name);
            viewHolder.num=convertView.findViewById(R.id.num);
            viewHolder.slogan=convertView.findViewById(R.id.slogan);

            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        //赋值
        viewHolder.name.setText(mList.get(position).getGroupName());
        viewHolder.num.setText(mList.get(position).getNumbers()+"");
        viewHolder.slogan.setText(mList.get(position).getSlogan());






        return convertView;

    }
}
