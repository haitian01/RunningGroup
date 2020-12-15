package com.example.runninggroup.viewAndController.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runninggroup.R;
import com.example.runninggroup.dao.FileDao;
import com.example.runninggroup.pojo.FriendRelation;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.ConstantUtil;
import com.example.runninggroup.util.ImgNameUtil;
import com.example.runninggroup.util.StringUtil;

import java.util.List;

public class FriendRelationAdapter extends BaseAdapter {
    private List<FriendRelation> mFriendRelationList;
    private Activity mActivity;
    private String word;
    public FriendRelationAdapter (List<FriendRelation> friendRelationList, Activity activity, String word){
        mFriendRelationList = friendRelationList;
        mActivity = activity;
        this.word = word;
    }
    @Override
    public int getCount() {
        return mFriendRelationList == null ? 0 : mFriendRelationList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFriendRelationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.helper_friend, parent, false);
        FriendRelation friendRelation = (FriendRelation) getItem(position);
        TextView username = view.findViewById(R.id.name);
        ImageView headImg = view.findViewById(R.id.img);
        /**
         * 下面是适配name，关键字word标蓝
         */
        String name = friendRelation.getAlias() == null ? friendRelation.getFriend().getUsername() + "(" + friendRelation.getFriend().getUsername() + ")" : friendRelation.getAlias() + "(" + friendRelation.getFriend().getUsername() + ")";
        SpannableStringBuilder builder = new SpannableStringBuilder(name);

        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        List<String> wordPosition = StringUtil.getWordPosition(word, name);
        for (String s : wordPosition) {
            String[] split = s.split(",");
            builder.setSpan(new ForegroundColorSpan(R.color.startColor), Integer.parseInt(split[0]), Integer.parseInt(split[1]) + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        username.setText(builder);

        setHeadImg(headImg, friendRelation.getFriend());
        return view;
    }

    public void setHeadImg (ImageView headImg, User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable drawable = null;
                for (int i = 0; i < ConstantUtil.MAX_LOAD_TIME; i++) {
                    if (drawable != null) break;
                    else drawable = FileDao.getImg(ImgNameUtil.getUserHeadImgName(user.getId()));
                }
                Drawable finalDrawable = drawable;
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (finalDrawable != null) headImg.setImageDrawable(finalDrawable);
                        else headImg.setImageResource(user.getSex() == 1 ? R.drawable.default_head_m : R.drawable.default_head_w);
                    }
                });
            }
        }).start();
    }

}
