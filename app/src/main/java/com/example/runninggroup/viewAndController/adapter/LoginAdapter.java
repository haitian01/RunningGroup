package com.example.runninggroup.viewAndController.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runninggroup.R;
import com.example.runninggroup.dao.UserDao;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.request.ImgGet;
import com.example.runninggroup.util.ImgNameUtil;
import com.example.runninggroup.util.MyLinkedHashMapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LoginAdapter extends BaseAdapter {
    Set<String> set;
    Activity mActivity;
    List<String> mList = new ArrayList<>();
    public LoginAdapter(Set<String> set, Activity activity) {
           this.set = set;
            mActivity = activity;
        for (String s : set) {
            if (! "registerNum".equals(s) && ! "password".equals(s))
            mList.add(s);
        }
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
        convertView = mActivity.getLayoutInflater().inflate(R.layout.helper_login_user, null);
        viewHolder.img = convertView.findViewById(R.id.img);
        viewHolder.registerNum = convertView.findViewById(R.id.registerNum);
        viewHolder.img.setImageResource(R.drawable.default_head_m);


        viewHolder.registerNum.setText(mList.get(position));
        setImg(viewHolder, mList.get(position));
        return convertView;
    }
    class ViewHolder {
        ImageView img;
        TextView registerNum;
    }
    public void setImg (ViewHolder viewHolder, String registerNum) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                User user = new User();
                user.setRegisterNum(registerNum);
                List<User> userList = UserDao.getUser(user);
                Drawable drawable = userList == null || userList.size() == 0 ? null : ImgGet.getImg(ImgNameUtil.getUserHeadImgName(userList.get(0).getId()));
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       if (drawable != null) viewHolder.img.setImageDrawable(drawable);
                       else {
                           if (userList == null || userList.size() == 0) {
                               viewHolder.img.setImageResource(R.drawable.default_head_m);

                           }else {
                               viewHolder.img.setImageResource(userList.get(0).getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
                           }
                       }
                    }
                });
            }
        }).start();
    }
}
