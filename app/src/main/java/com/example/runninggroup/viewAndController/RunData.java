package com.example.runninggroup.viewAndController;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.runninggroup.R;
import com.example.runninggroup.view.CircleIndicatorView;
import com.example.runninggroup.view.ScrollChartView;
import com.example.runninggroup.viewAndController.TimeAndData.GetTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class RunData extends AppCompatActivity {

    private ScrollChartView scrollChartView;
    private CircleIndicatorView circleIndicatorView;
    private TextView tvTime;
    private TextView tvData;

    private ScrollChartView.LineType lineType = ScrollChartView.LineType.ARC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_data);
        initView();
        //加个延时设置数据，防止view未绘制完成的情况下就设置数据，正常业务不会出现这种情况，因为会有网络加载数据的过程
        handler.sendEmptyMessageDelayed(0, 1000);
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

        final Button btnback = findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RunData.this, MainInterface.class);
                startActivity(intent);
            }
        });
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            initData();
        }
    };

    private void initData() {
        long timeStamp = GetTime.getDayBegin();
        final List<String> timeList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            int month = GetTime.getMonthByTimeStamp(timeStamp);
            int today = GetTime.getDayByTimeStamp(timeStamp);
            timeList.add(month + "." + today );
            timeStamp = timeStamp - 1000*60*60*24;
        }

        final List<Double> dataList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dataList.add((double) new Random().nextInt(100));
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
        scrollChartView.smoothScrollTo(dataList.size() - 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
