package com.example.runninggroup.viewAndController.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.DataRemovalRequest;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.viewAndController.helper.FriendsHelper;
import com.example.runninggroup.viewAndController.helper.GroupTaskHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupTaskAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    public List<GroupTaskHelper> mList;
    HashMap<Integer,Drawable> mDrawable_img;
    HashMap<Integer,Drawable> mDrawable_task_img;


    public GroupTaskAdapter(LayoutInflater inflater, List<GroupTaskHelper> list) {
        mInflater = inflater;
        mList = list;
        mDrawable_img = new HashMap<>(list.size());
        mDrawable_task_img = new HashMap<>(list.size());
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<list.size();i++){
                    String admin = mList.get(i).getRelease_name();
                    long begin_time = mList.get(i).getTask_time();
                    Drawable img = DaoUser.getImg(DaoUser.getUserHeadImgName(admin));
                    Drawable task_img = DaoUser.getImg(DaoUser.getTaskImgName(admin,begin_time));
                    if (img != null) mDrawable_img.put(i,img);
                    if (task_img != null) mDrawable_task_img.put(i,task_img);
                }

            }
        }).start();

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
            public ImageView task_img;
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
            viewHolder.task_img=convertView.findViewById(R.id.task_img);
            viewHolder.release_name=convertView.findViewById(R.id.release_name);
            viewHolder.task_msg=convertView.findViewById(R.id.task_msg);
            viewHolder.task_time=convertView.findViewById(R.id.task_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        //赋值
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String admin = mList.get(position).getRelease_name();
                long begin_time = mList.get(position).getTask_time();

                if(mDrawable_img.get(position) == null) {
                    Drawable img = DaoUser.getImg(DaoUser.getUserHeadImgName(admin));
                    if (img != null){mDrawable_img.put(position,img);}
                }
                if(mDrawable_task_img.get(position) == null) {
                    Drawable task_img = DaoUser.getImg(DaoUser.getTaskImgName(admin,begin_time));
                    if (task_img != null){mDrawable_task_img.put(position,task_img);}
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(mDrawable_img.get(position) != null){viewHolder.img.setImageDrawable(mDrawable_img.get(position));}
        else viewHolder.img.setImageResource(R.mipmap.defaultpic);
        viewHolder.task_img.setImageDrawable(mDrawable_task_img.get(position));
        viewHolder.release_name.setText(mList.get(position).getRelease_name());
        viewHolder.task_time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mList.get(position).getTask_time()));
        viewHolder.task_msg.setText(mList.get(position).getTask_msg());







        return convertView;

    }
}
