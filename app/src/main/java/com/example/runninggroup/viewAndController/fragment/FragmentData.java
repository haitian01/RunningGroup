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
                Object[] runData = {100,200,150,120,80,50,90};//需要一个方法获取
                refreshBarChart(runData);
            }
        });

        lineChart.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //最好在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                Object[] Month = GetData.getGroudMonth();
                Object[] groudData = {4,5,4,4,5};////需要一个方法获取
                refreshLinkChart(Month, groudData);
            }
        });
        return view;
    }

    public void initView(){
        barChart = view.findViewById(R.id.chart01);
        lineChart = view.findViewById(R.id.chart02);

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
