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
import com.example.runninggroup.viewAndController.helper.GroupCallHelper;
import com.example.runninggroup.viewAndController.helper.GroupTaskHelper;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

public class GroupCallAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    public List<GroupCallHelper> mList;
    HashMap<Integer, Drawable> mDrawable;
    HashMap<Integer, Drawable> mDrawable_img;
    public GroupCallAdapter(LayoutInflater inflater, List<GroupCallHelper> list) {
        mInflater = inflater;
        mList = list;
        mDrawable = new HashMap<>(list.size());
        mDrawable_img = new HashMap<>(list.size());
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<list.size();i++){
                    Drawable drawable =  DaoUser.getImg(DaoUser.getCallImgName(mList.get(i).getCallName(),mList.get(i).getCallTime()));
                    Drawable drawable_img =  DaoUser.getImg(DaoUser.getUserHeadImgName(mList.get(i).getCallName()));
                    if(drawable != null) mDrawable.put(i,drawable);
                    if(drawable_img != null) mDrawable_img.put(i,drawable_img);
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
            viewHolder.task_img=convertView.findViewById(R.id.task_img);
            viewHolder.callName=convertView.findViewById(R.id.release_name);
            viewHolder.callMsg=convertView.findViewById(R.id.task_msg);
            viewHolder.callTime=convertView.findViewById(R.id.task_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if(mDrawable.get(position) == null){
                    Drawable drawable =  DaoUser.getImg(DaoUser.getCallImgName(mList.get(position).getCallName(),mList.get(position).getCallTime()));
                    if(drawable != null) mDrawable.put(position,drawable);
                }
                if(mDrawable_img.get(position) == null){
                    Drawable drawable_img =  DaoUser.getImg(DaoUser.getUserHeadImgName(mList.get(position).getCallName()));
                    if(drawable_img != null) mDrawable_img.put(position,drawable_img);
                }


            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(mDrawable_img.get(position) != null) viewHolder.img.setImageDrawable(mDrawable_img.get(position));
        viewHolder.task_img.setImageDrawable(mDrawable.get(position));

        //赋值
        viewHolder.callName.setText(mList.get(position).getCallName());
        viewHolder.callMsg.setText(mList.get(position).getCallMsg());
        viewHolder.callTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mList.get(position).getCallTime()));







        return convertView;

    }
}
