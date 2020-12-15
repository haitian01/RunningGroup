package com.example.runninggroup.viewAndController.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runninggroup.R;
import com.example.runninggroup.controller.FileController;
import com.example.runninggroup.pojo.TeamNotice;
import com.example.runninggroup.util.ImgNameUtil;
import com.example.runninggroup.viewAndController.TeamNoticeActivity;


import java.net.InterfaceAddress;
import java.text.SimpleDateFormat;
import java.util.List;

public class TeamNoticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TeamNotice> mTeamNoticeList;
    private Activity mActivity;
    private OnItemClickListener mOnItemClickListener;
    //管理员发布公告
    private static final int ZERO = 0;
    //打卡数据
    private static final int FIRST = 1;
    //积分数据
    private static final int SECOND = 2;
    public TeamNoticeAdapter (List<TeamNotice> teamNoticeList, Activity activity) {
        mTeamNoticeList = teamNoticeList;
        mActivity = activity;
    }
    public interface OnItemClickListener {
        //管理员发布公告被点击
        void zeroClick(int position);
    }
    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ZERO) {
            view = mActivity.getLayoutInflater().inflate(R.layout.helper_team_notice_zero, parent, false);
            return new ZeroHolder(view);
        }
        return new ZeroHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TeamNotice teamNotice = mTeamNoticeList.get(position);
        if (holder instanceof ZeroHolder) {
            ((ZeroHolder) holder).mTeamNotice = teamNotice;
            ((ZeroHolder) holder).noticeMsg.setText(teamNotice.getNoticeMsg());
            ((ZeroHolder) holder).noticeUserName.setText(teamNotice.getUser().getUsername());

            //ZERO类型公告被点击
            ((ZeroHolder) holder).noticeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) mOnItemClickListener.zeroClick(position);
                }
            });
            //设置时间
            ((ZeroHolder) holder).noticeTime.setText(new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(teamNotice.getCreateTime()));
            if (teamNotice.getImgNum() == 0) ((ZeroHolder) holder).noticeImg.setVisibility(View.GONE);
            else {
                ((ZeroHolder) holder).setImg();
            }
        }

    }

    @Override
    public int getItemCount() {
        return mTeamNoticeList == null ? 0 : mTeamNoticeList.size();
    }




    @Override
    public int getItemViewType(int position) {
        TeamNotice teamNotice = mTeamNoticeList.get(position);
        Integer type = teamNotice.getNoticeType();
        if (type == 0) return ZERO;
        else return type == 1 ? FIRST : SECOND;
    }

    class ZeroHolder extends RecyclerView.ViewHolder implements FileController.FileControllerInterface{
        private TeamNotice mTeamNotice;
        private RelativeLayout noticeItem;
        private TextView noticeMsg, noticeUserName, noticeTime;
        private ImageView noticeImg;
        private FileController mFileController = new FileController(this);
        public ZeroHolder(@NonNull View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            noticeMsg = itemView.findViewById(R.id.notice_msg);
            noticeUserName = itemView.findViewById(R.id.notice_username);
            noticeTime = itemView.findViewById(R.id.notice_time);
            noticeImg = itemView.findViewById(R.id.notice_img);
            noticeItem = itemView.findViewById(R.id.notice_item);
        }

        /**
         * 获得公告图片
         */

        public void setImg () {
            if (mTeamNotice != null) {
                String imgName = ImgNameUtil.getNoticeImgName(mTeamNotice.getId(), 0);
                mFileController.getImg(imgName);
            }
        }

        @Override
        public void getImgBack(Drawable drawable) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (drawable != null) noticeImg.setImageDrawable(drawable);
                }
            });
        }
    }
}
