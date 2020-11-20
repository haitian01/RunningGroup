package com.example.runninggroup.viewAndController.adapter;

import android.app.Activity;
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

public class UserAdapter extends BaseAdapter {
    List<User> mList;
    Activity mActivity;

    public UserAdapter(List<User> list, Activity activity) {
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
        ViewHolder viewHolder = new ViewHolder();
        User user = mList.get(position);
        convertView = mActivity.getLayoutInflater().inflate(R.layout.helper_user, null);
        viewHolder.img = convertView.findViewById(R.id.img);
        viewHolder.msg = convertView.findViewById(R.id.msg);

        viewHolder.msg.setText(mList.get(position).getUsername() + "(" + mList.get(position).getRegisterNum() + ")");
        viewHolder.img.setImageResource(user.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
        setImg(viewHolder, user.getId());
        return convertView;
    }
    class ViewHolder {

        ImageView img;
        TextView msg;

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
