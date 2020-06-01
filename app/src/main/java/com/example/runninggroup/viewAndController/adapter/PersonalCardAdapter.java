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
import com.example.runninggroup.util.CharacterUtil;
import com.example.runninggroup.viewAndController.helper.GroupTaskHelper;
import com.example.runninggroup.viewAndController.helper.PersonalCardHelper;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

public class PersonalCardAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    public List<PersonalCardHelper> mList;
    HashMap<Integer,Drawable> mDrawable;

    public PersonalCardAdapter(LayoutInflater inflater, List<PersonalCardHelper> list) {
        mInflater = inflater;
        mList = list;
        mDrawable = new HashMap<>(list.size());
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<list.size();i++){
                    Drawable drawable = DaoUser.getImg(DaoUser.getCardImgName(mList.get(i).getUsername(), mList.get(i).getBegin_time()));
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
            public TextView act_type;
            public TextView act_time;
            public TextView act_length;
            public TextView act_score;

        }
        //判断converView是否为空
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.helper_personcard,null);
            viewHolder=new ViewHolder();
            viewHolder.img=convertView.findViewById(R.id.act_img);
            viewHolder.act_type=convertView.findViewById(R.id.act_type);
            viewHolder.act_time=convertView.findViewById(R.id.act_time);
            viewHolder.act_length=convertView.findViewById(R.id.act_length);
            viewHolder.act_score=convertView.findViewById(R.id.act_score);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        //赋值
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if(mDrawable.get(position) == null) {
                    Drawable drawable = DaoUser.getImg(DaoUser.getCardImgName(mList.get(position).getUsername(), mList.get(position).getBegin_time()));
                    mDrawable.put(position,drawable);
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
        viewHolder.act_type.setText(mList.get(position).getAct_type());
        viewHolder.act_length.setText(mList.get(position).getLength()+"");
        viewHolder.act_time.setText(CharacterUtil.getTimeLength(mList.get(position).getBegin_time(),mList.get(position).getEnd_time()));
        viewHolder.act_score.setText(mList.get(position).getScore()+"");

        ;







        return convertView;

    }
}
