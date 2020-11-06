package com.example.runninggroup.viewAndController.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.request.ImgGet;
import com.example.runninggroup.util.ImgNameUtil;
import com.example.runninggroup.viewAndController.TeamIntroduction;
import com.example.runninggroup.viewAndController.helper.GroupHelper;

import java.util.HashMap;
import java.util.List;

public class TeamAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    public List<Team> mList;
    public Activity mActivity;
    public TeamAdapter(LayoutInflater inflater, List<Team> list, Activity activity) {
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

        //判断converView是否为空
        ViewHolder viewHolder;
//        if (convertView==null){
            convertView=mInflater.inflate(R.layout.helper_group,null);
            viewHolder=new ViewHolder();
            viewHolder.img=convertView.findViewById(R.id.img);
            viewHolder.name=convertView.findViewById(R.id.name);
            viewHolder.num=convertView.findViewById(R.id.num);
            viewHolder.slogan=convertView.findViewById(R.id.slogan);
            viewHolder.campus=convertView.findViewById(R.id.campus);
            viewHolder.add=convertView.findViewById(R.id.add);
            viewHolder.state=convertView.findViewById(R.id.state);

            convertView.setTag(viewHolder);
//        }else {
            //viewHolder= (ViewHolder) convertView.getTag();
//        }


        Team team = mList.get(position);
        viewHolder.img.setImageResource(R.drawable.default_team);
        viewHolder.name.setText(team.getTeamName());
        viewHolder.num.setText(team.getTeamSize() + "");
        viewHolder.slogan.setText(team.getTeamSlogan());
        viewHolder.campus.setText(team.getCampus() + " | " + team.getCollege());
        if (Cache.user.getTeam() != null && Cache.user.getTeam().getId() == mList.get(position).getId()) viewHolder.add.setVisibility(View.INVISIBLE);
        else viewHolder.add.setVisibility(View.VISIBLE);
        convertView.setOnClickListener(new View.OnClickListener() {
            Team mTeam = team;
            @Override
            public void onClick(View v) {
                Cache.team = mTeam;
                Toast.makeText(mActivity, Cache.team.getUser().getId() + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mActivity, TeamIntroduction.class);
                mActivity.startActivity(intent);
            }
        });
        setDrawable(viewHolder, team.getId());







        return convertView;

    }
    //ViewHolder内部类
    class ViewHolder{
        public ImageView img;
        public TextView name;
        public TextView num;
        public TextView campus;
        public TextView slogan;
        public Button add;
        public TextView state;

    }
    private void setDrawable (ViewHolder viewHolder, int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable drawable = ImgGet.getImg(ImgNameUtil.getGroupHeadImgName(id));
                if (drawable != null) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewHolder.img.setImageDrawable(drawable);
                        }
                    });
                }
            }
        }).start();
    }
}
