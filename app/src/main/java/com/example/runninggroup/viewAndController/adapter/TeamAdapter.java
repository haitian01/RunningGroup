package com.example.runninggroup.viewAndController.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.pojo.Team;
import com.example.runninggroup.request.ImgGet;
import com.example.runninggroup.util.ImgNameUtil;

import java.util.List;

public class TeamAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public LayoutInflater mInflater;
    public List<Team> mList;
    public Activity mActivity;
    private OnItemClickListener mOnItemClickListener;
    public TeamAdapter(LayoutInflater inflater, List<Team> list, Activity activity) {
        mInflater = inflater;
        mList = list;
        mActivity = activity;

    }
    public interface OnItemClickListener {
        void itemOnClick(int position);
        void addOnClick(int position);
    }
    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  mActivity.getLayoutInflater().inflate(R.layout.helper_team, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InnerHolder) {
            InnerHolder viewHolder = (InnerHolder) holder;
            Team team = mList.get(position);
            viewHolder.img.setImageResource(R.drawable.default_team);
            viewHolder.name.setText(team.getTeamName());
            viewHolder.num.setText(team.getTeamSize() + "");
            viewHolder.slogan.setText(team.getTeamSlogan());
            viewHolder.campus.setText(team.getCampus() + " | " + team.getCollege());
            if (Cache.user.getTeam() != null && Cache.user.getTeam().getId() == mList.get(position).getId()) {
                viewHolder.add.setVisibility(View.INVISIBLE);
                viewHolder.state.setVisibility(View.VISIBLE);
            }
            else {
                viewHolder.add.setVisibility(View.VISIBLE);
                viewHolder.state.setVisibility(View.INVISIBLE);
            }
            viewHolder.teamItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) mOnItemClickListener.itemOnClick(position);
                }
            });
            viewHolder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) mOnItemClickListener.addOnClick(position);
                }
            });
            setDrawable(viewHolder, team.getId());
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    //ViewHolder内部类
    class InnerHolder extends RecyclerView.ViewHolder{
        public RelativeLayout teamItem;
        public ImageView img;
        public TextView name;
        public TextView num;
        public TextView campus;
        public TextView slogan;
        public Button add;
        public TextView state;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            initView();
        }
        public void initView() {
            img=itemView.findViewById(R.id.img);
            name=itemView.findViewById(R.id.name);
            num=itemView.findViewById(R.id.num);
            slogan=itemView.findViewById(R.id.slogan);
            campus=itemView.findViewById(R.id.campus);
            add=itemView.findViewById(R.id.add);
            state=itemView.findViewById(R.id.state);
            teamItem = itemView.findViewById(R.id.team_item);
        }
    }
    private void setDrawable (InnerHolder viewHolder, int id) {
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
