package com.example.runninggroup.viewAndController.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runninggroup.R;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.request.ImgGet;
import com.example.runninggroup.util.ImgNameUtil;

import java.util.List;
import java.util.zip.Inflater;

public class MemberAdapter extends BaseAdapter {
    List<User> mList;
    Activity mActivity;
    String mSorted;
    public MemberAdapter (List<User> list, Activity activity, String sorted) {
            mList = list;
            mActivity = activity;
            mSorted = sorted;
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
        ViewHolder viewHolder = new ViewHolder();
        User user = mList.get(position);
        convertView = mActivity.getLayoutInflater().inflate(R.layout.helper_member_sort, null);
        viewHolder.ranking = convertView.findViewById(R.id.ranking);
        viewHolder.img = convertView.findViewById(R.id.img);
        viewHolder.username = convertView.findViewById(R.id.username);
        viewHolder.sorted = convertView.findViewById(R.id.sorted);
        viewHolder.img.setImageResource(user.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
        viewHolder.username.setText(user.getUsername());
        int pos = position + 1;
        viewHolder.ranking.setText(pos + "");
        if ("length".equals(mSorted)) viewHolder.sorted.setText(user.getLength() + "米");
        else if ("score".equals(mSorted)) viewHolder.sorted.setText(user.getScore() + "分");
        setImg(viewHolder, user.getId());
        return convertView;
    }
    class ViewHolder {
        TextView ranking;
        ImageView img;
        TextView username;
        TextView sorted;
    }
    public void setImg (ViewHolder viewHolder, int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable drawable = ImgGet.getImg(ImgNameUtil.getUserHeadImgName(id));
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (drawable != null) viewHolder.img.setImageDrawable(drawable);
                    }
                });
            }
        }).start();
    }
}
