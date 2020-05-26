package com.example.runninggroup.viewAndController.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runninggroup.R;
import com.example.runninggroup.viewAndController.helper.DynamicHelper;
import com.example.runninggroup.viewAndController.helper.GroupTaskHelper;

import java.util.List;

public class DynamicAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    public List<DynamicHelper> mList;

    public DynamicAdapter(LayoutInflater inflater, List<DynamicHelper> list) {
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
            public TextView msg;
            public TextView time;

        }
        //判断converView是否为空
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.helper_dynamic,null);
            viewHolder=new ViewHolder();
            viewHolder.img=convertView.findViewById(R.id.dynamic_img);
            viewHolder.msg=convertView.findViewById(R.id.dynamic_msg);
            viewHolder.time=convertView.findViewById(R.id.dynamic_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        //赋值
        viewHolder.img.setImageResource(R.mipmap.defaultpic);
        viewHolder.msg.setText(mList.get(position).getDynamic_msg());
        viewHolder.time.setText(mList.get(position).getDynamic_time());







        return convertView;

    }
}
