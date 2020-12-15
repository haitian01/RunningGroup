package com.example.runninggroup.viewAndController.adapter;

import android.app.Activity;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.pojo.Act;
import com.example.runninggroup.util.StringUtil;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.InnerHolder> {
    private List<Act> mActs;
    private Activity mActivity;
    private OperateImgOnClickListener mOperateImgOnClickListener;
    private DecimalFormat df = new DecimalFormat("######0.00");
    public CardAdapter (List<Act> acts, Activity activity) {
        mActs = acts;
        mActivity = activity;
    }
    public interface OperateImgOnClickListener {
        void shareImgOnClick(Act act);
    }
    public void setOperateImgOnClickListener (OperateImgOnClickListener operateImgOnClickListener) {
        mOperateImgOnClickListener = operateImgOnClickListener;
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.helper_card_record, parent, false);
        return new InnerHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        Act act = mActs.get(position);
        holder.place.setText(act.getPlace());
        holder.runLen.setText(act.getRunLen() + "");
        holder.username.setText(Cache.user.getUsername());
        /**
         * 获取开始时间，精确到分钟2020-12-02 22：17：24.0
         */
        String time = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(act.getBeginTime());
        holder.beginTime.setText(time);

        /**
         * 获取用时
         * 获取配速
         */
        Long milliSecond = act.getEndTime().getTime() - act.getBeginTime().getTime();
        String runTime = StringUtil.getTime(milliSecond);
        String speed = StringUtil.getSpeed(milliSecond, act.getRunLen());
        holder.speed.setText(speed);
        holder.runTime.setText(runTime);
        holder.energy.setText(act.getRunLen() * 60 + "");



        /**
         * 点击事件，shareImg
         */

        holder.operateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOperateImgOnClickListener != null) mOperateImgOnClickListener.shareImgOnClick(act);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mActs == null ? 0 : mActs.size();
    }

    class InnerHolder extends RecyclerView.ViewHolder {
        private TextView place, runLen, username, beginTime, speed, runTime, energy;
        private ImageView operateImg;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            initView();
        }


        private void initView() {
            place = itemView.findViewById(R.id.place);
            runLen = itemView.findViewById(R.id.run_len);
            username = itemView.findViewById(R.id.username);
            beginTime = itemView.findViewById(R.id.begin_time);
            speed = itemView.findViewById(R.id.speed);
            runTime = itemView.findViewById(R.id.run_time);
            energy = itemView.findViewById(R.id.energy);
            operateImg = itemView.findViewById(R.id.share);
        }
    }
}
