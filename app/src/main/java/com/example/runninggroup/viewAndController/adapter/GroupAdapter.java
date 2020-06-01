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

public class GroupAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    public List<GroupHelper> mList;
    HashMap<Integer,Drawable> mDrawable;
    public GroupAdapter(LayoutInflater inflater, List<GroupHelper> list) {
        mInflater = inflater;
        mList = list;
        mDrawable = new HashMap<>(list.size());
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<list.size();i++){
                    Drawable drawable = DaoUser.getImg(DaoUser.getGroupHeadImgName(mList.get(i).getGroupName()));
                    if(drawable!=null) mDrawable.put(i,drawable);
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
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if(mDrawable.get(position) == null){
                    Drawable drawable =  DaoUser.getImg(DaoUser.getGroupHeadImgName(mList.get(position).getGroupName()));
                    if(drawable != null) mDrawable.put(position,drawable);
                }


            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(mDrawable.get(position) != null){viewHolder.img.setImageDrawable(mDrawable.get(position));}
        else {viewHolder.img.setImageResource(R.mipmap.defaultpic);}
        viewHolder.name.setText(mList.get(position).getGroupName());
        viewHolder.num.setText(mList.get(position).getNumbers()+"");






        return convertView;

    }
}
