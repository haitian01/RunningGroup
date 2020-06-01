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
import com.example.runninggroup.viewAndController.helper.FriendsHelper;
import com.example.runninggroup.viewAndController.helper.MemberManageHelper;

import java.util.HashMap;
import java.util.List;

public class MemberManageAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    public List<MemberManageHelper> mList;
    HashMap<Integer, Drawable> mDrawable;
    public MemberManageAdapter(LayoutInflater inflater, List<MemberManageHelper> list) {
        mInflater = inflater;
        mList = list;
        mDrawable = new HashMap<>(list.size());
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<list.size();i++){
                    Drawable drawable =  DaoUser.getImg(DaoUser.getUserHeadImgName(mList.get(i).getUsername()));
                    if(drawable != null) mDrawable.put(i,drawable);
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
            public TextView title;

        }
        //判断converView是否为空
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.helper_membermanage,null);
            viewHolder=new ViewHolder();
            viewHolder.img=convertView.findViewById(R.id.membermanage_img);
            viewHolder.name=convertView.findViewById(R.id.membermanage_name);
            viewHolder.title=convertView.findViewById(R.id.membermanage_title);

            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        //赋值
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if(mDrawable.get(position) == null){
                    Drawable drawable =  DaoUser.getImg(DaoUser.getUserHeadImgName(mList.get(position).getUsername()));
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
        viewHolder.name.setText(mList.get(position).getUsername());
        viewHolder.title.setText((mList.get(position).getAdmin()==1) ? "管理员":"成员");







        return convertView;

    }
}
