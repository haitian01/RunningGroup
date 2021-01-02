package com.example.runninggroup.viewAndController.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.util.StringUtil;
import com.example.runninggroup.util.TimerUtil;

public class FragmentCardMessage extends Fragment {
    private View mView;
    private TextView distanceTxt, energyTxt, timeTxt, speedTxt1, speedTxt2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_card_message, container, false);
        initView();
        initData();
        return mView;
    }

    private void initData() {
        if (Cache.act != null) {
            distanceTxt.setText(Cache.act.getRunLen() + "");
            energyTxt.setText(StringUtil.formatDouble4(Cache.act.getRunLen() * 60));
            timeTxt.setText(Cache.act.getTotalTime() == null ? StringUtil.getTime(Cache.act.getEndTime().getTime() - Cache.act.getBeginTime().getTime()) : Cache.act.getTotalTime());
            speedTxt1.setText(StringUtil.getSpeed1(Cache.act.getEndTime().getTime() - Cache.act.getBeginTime().getTime(), Cache.act.getRunLen()));
            speedTxt2.setText(StringUtil.getSpeed(Cache.act.getEndTime().getTime() - Cache.act.getBeginTime().getTime(), Cache.act.getRunLen()));
        }
    }

    private void initView() {
        distanceTxt = mView.findViewById(R.id.tvDistance);
        energyTxt = mView.findViewById(R.id.tvCalorie);
        timeTxt = mView.findViewById(R.id.tvDuration);
        speedTxt1 = mView.findViewById(R.id.tvSpeed); //速度（公里/小时）
        speedTxt2 = mView.findViewById(R.id.tvDistribution); //配速
    }
}
