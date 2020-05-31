package com.example.runninggroup.viewAndController.adapter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.viewAndController.helper.DynamicHelper;
import com.example.runninggroup.viewAndController.helper.MomentHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MomentAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    public List<MomentHelper> mList;
    Drawable mDrawable;

    public MomentAdapter(LayoutInflater inflater, List<MomentHelper> list) {
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
            public TextView msg;
            public TextView time;
            public TextView name;

        }
        //判断converView是否为空
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.helper_moment,null);
            viewHolder=new ViewHolder();
            viewHolder.img=convertView.findViewById(R.id.application_img);
            viewHolder.msg=convertView.findViewById(R.id.application_content);
            viewHolder.time=convertView.findViewById(R.id.application_time);
            viewHolder.name=convertView.findViewById(R.id.application_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        //赋值
        Drawable drawable = DaoUser.getImg(DaoUser.getUserHeadImgName(mList.get(position).getFrom_name()));
        viewHolder.img.setImageDrawable(drawable);
        viewHolder.msg.setText(mList.get(position).getContent());
        viewHolder.name.setText(mList.get(position).getFrom_name());
        viewHolder.time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(mList.get(position).getMoment_time())));







        return convertView;

    }
}
