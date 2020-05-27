package com.example.runninggroup.viewAndController.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runninggroup.R;
import com.example.runninggroup.viewAndController.helper.GroupCallHelper;
import com.example.runninggroup.viewAndController.helper.GroupTaskHelper;

import java.text.SimpleDateFormat;
import java.util.List;

public class GroupCallAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    public List<GroupCallHelper> mList;

    public GroupCallAdapter(LayoutInflater inflater, List<GroupCallHelper> list) {
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
            public TextView callName;
            public TextView callMsg;
            public TextView callTime;

        }
        //判断converView是否为空
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.helper_grouptask,null);
            viewHolder=new ViewHolder();
            viewHolder.img=convertView.findViewById(R.id.img);
            viewHolder.callName=convertView.findViewById(R.id.release_name);
            viewHolder.callMsg=convertView.findViewById(R.id.task_msg);
            viewHolder.callTime=convertView.findViewById(R.id.task_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        //赋值
        viewHolder.callName.setText(mList.get(position).getCallName());
        viewHolder.callMsg.setText(mList.get(position).getCallMsg());
        viewHolder.callTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mList.get(position).getCallTime()));







        return convertView;

    }
}
