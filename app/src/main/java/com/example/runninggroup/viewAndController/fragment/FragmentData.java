package com.example.runninggroup.viewAndController.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.runninggroup.R;
import com.example.runninggroup.viewAndController.Echart.EchartOptionUtil;
import com.example.runninggroup.viewAndController.Echart.EchartView;
import com.example.runninggroup.viewAndController.Echart.EchartView2;
import com.example.runninggroup.viewAndController.TimeAndData.GetData;

public class FragmentData extends Fragment {
    View view;
    private EchartView barChart;
    private EchartView2 lineChart;
    Object[] scoreData;
    Object[] months;
    Object[] runData;
    String username;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_data,container,false);
        initView();
        barChart.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //最好在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GetData getData = new GetData();
                        getData.username = username;
                        runData = getData.getRunData();
                    }
                });
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshBarChart(runData);
            }
        });

        lineChart.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //最好在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GetData getData = new GetData();
                        getData.username = username;
                        months = getData.getGroudMonth();
                        scoreData = getData.getGroudScore();
                    }
                });
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshLinkChart(months, scoreData);
            }
        });
        return view;
    }

    public void initView(){
        username = getActivity().getIntent().getStringExtra("username");
        barChart = view.findViewById(R.id.chart01);
        barChart.getSettings().setUseWideViewPort(true);
        barChart.getSettings().setLoadWithOverviewMode(true);
        lineChart = view.findViewById(R.id.chart02);
        lineChart.getSettings().setUseWideViewPort(true);
        lineChart.getSettings().setLoadWithOverviewMode(true);

    }



    //先写在这
    private void refreshBarChart(Object[] runData){
        Object[] x = new Object[]{
                "Mon", "Tue", "Wed", "Thu", "Fri", "Sta", "Sun"
        };
        Object[] y =  runData;
        barChart.refreshEchartsWithOption(EchartOptionUtil.getBarChartOptions(x, y));
    }
    private void refreshLinkChart(Object[] Month, Object[] groudData){
        Object[] x = Month;
        Object[] y =  groudData;
        lineChart.refreshEchartsWithOption(EchartOptionUtil.getLineChartOptions(x, y));
    }
}
