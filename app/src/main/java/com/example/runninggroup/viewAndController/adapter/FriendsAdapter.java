package com.example.runninggroup.viewAndController.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runninggroup.dao.FileDao;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.R;
import com.example.runninggroup.util.ImgNameUtil;

import java.util.List;

public class FriendsAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    public List<FriendRelation> mList;
    public Activity mActivity;
    public FriendsAdapter(LayoutInflater inflater, List<FriendRelation> list, Activity activity) {

        mInflater = inflater;
        mList = list;
        mActivity = activity;
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

        //判断converView是否为空
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.helper_friend,null);
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


        viewHolder.img.setImageResource(mList.get(position).getFriend().getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
        setImg(viewHolder, ImgNameUtil.getUserHeadImgName(mList.get(position).getFriend().getId()));
        viewHolder.name.setText(mList.get(position).getAlias() != null ? mList.get(position).getAlias() : mList.get(position).getFriend().getUsername());
        viewHolder.group.setText(mList.get(position).getFriend().getTeam() == null ? "无" : mList.get(position).getFriend().getTeam().getTeamName());







        return convertView;

    }

    class ViewHolder{
        public ImageView img;
        public TextView name;
        public TextView group;
        public TextView length;
        public TextView score;
    }

    public void setImg (ViewHolder viewHolder, String imgName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable drawable = FileDao.getImg(imgName);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (drawable != null)
                        viewHolder.img.setImageDrawable(drawable);
                    }
                });

            }
        }).start();
    }


}
