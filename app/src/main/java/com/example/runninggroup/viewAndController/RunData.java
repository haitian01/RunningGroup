package com.example.runninggroup.viewAndController;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.ActController;
import com.example.runninggroup.view.CircleIndicatorView;
import com.example.runninggroup.view.ScrollChartView;
import com.example.runninggroup.viewAndController.TimeAndData.GetTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class RunData extends AppCompatActivity implements ActController.ActControllerInterface {

    private ScrollChartView scrollChartView;
    private CircleIndicatorView circleIndicatorView;
    private TextView tvTime;
    private TextView tvData;
    private ActController mActController = new ActController(this);

    private ScrollChartView.LineType lineType = ScrollChartView.LineType.ARC;
    private  final List<Double> dataList = new ArrayList<>(8);
    final LinkedList<String> timeList = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_data);
        initView();
        initData();
    }


    private void initView() {
        scrollChartView = findViewById(R.id.scroll_chart_main);
        circleIndicatorView = findViewById(R.id.civ_main);
        tvTime = findViewById(R.id.tv_time);
        tvData = findViewById(R.id.tv_data);

        final Button btnLine = findViewById(R.id.btn_line);
        btnLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scrollChartView.getLineType() == ScrollChartView.LineType.LINE) {
                    scrollChartView.setLineType(ScrollChartView.LineType.ARC);
                    scrollChartView.invalidateView();
                    btnLine.setText("七天跑步数据");
                } else {
                    scrollChartView.setLineType(ScrollChartView.LineType.LINE);
                    scrollChartView.invalidateView();
                    btnLine.setText("七天跑步数据");
                }
            }
        });

        final ImageView btnback = findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RunData.this, MainInterface.class);
                startActivity(intent);
            }
        });
    }



    private void initData() {
        long timeStamp = GetTime.getDayBegin();
        for (int i = 0; i < 7; i++) {
            int month = GetTime.getMonthByTimeStamp(timeStamp);
            int today = GetTime.getDayByTimeStamp(timeStamp);
            timeList.addFirst(month + "." + today );
            if (Cache.user != null && Cache.user.getId() != null) {
                mActController.selectLenByTime(Cache.user.getId(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeStamp)), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeStamp + 1000*60*60*24)), 6 - i);
            }

            timeStamp = timeStamp - 1000*60*60*24;
        }

        for (int i = 0; i < 7; i++) {
            dataList.add(0.0);
        }

        scrollChartView.setData(timeList, dataList);
        scrollChartView.setOnScaleListener(new ScrollChartView.OnScaleListener() {
            @Override
            public void onScaleChanged(int position) {

                tvTime.setText(timeList.get(position));
                tvData.setText(dataList.get(position) + "km");
                ScrollChartView.Point point = scrollChartView.getList().get(position);
                circleIndicatorView.setCircleY(point.y);
            }
        });

        //滚动到目标position
        scrollChartView.smoothScrollTo(6);
    }

    @Override
    public void selectLenByTimeBack(String res, int position) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (res != null) {
                    System.out.println(Double.parseDouble(res));
                    dataList.set(position, Double.parseDouble(res));
                    scrollChartView.setData(timeList, dataList);
                    scrollChartView.smoothScrollTo(6);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
