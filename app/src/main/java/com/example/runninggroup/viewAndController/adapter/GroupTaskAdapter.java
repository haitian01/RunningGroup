package com.example.runninggroup.viewAndController.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runninggroup.R;
import com.example.runninggroup.viewAndController.helper.FriendsHelper;
import com.example.runninggroup.viewAndController.helper.GroupTaskHelper;

import java.util.List;

public class GroupTaskAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    public List<GroupTaskHelper> mList;

    public GroupTaskAdapter(LayoutInflater inflater, List<GroupTaskHelper> list) {
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
            public TextView release_name;
            public TextView task_msg;
            public TextView task_time;

        }
        //判断converView是否为空
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.helper_grouptask,null);
            viewHolder=new ViewHolder();
            viewHolder.img=convertView.findViewById(R.id.img);
            viewHolder.release_name=convertView.findViewById(R.id.release_name);
            viewHolder.task_msg=convertView.findViewById(R.id.task_msg);
            viewHolder.task_time=convertView.findViewById(R.id.task_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        //赋值
        viewHolder.release_name.setText(mList.get(position).getRelease_name());
        viewHolder.task_msg.setText(mList.get(position).getTask_msg());
        viewHolder.task_time.setText(mList.get(position).getTask_time()+"");







        return convertView;

    }
}
