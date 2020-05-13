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
import com.example.runninggroup.viewAndController.EchartOptionUtil;
import com.example.runninggroup.viewAndController.EchartView;
import com.github.abel533.echarts.Chart;

public class FragmentData extends Fragment {
    View view;
    private EchartView barChart;
    private EchartView lineChart;
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
                refreshBarChart();
            }
        });

        lineChart.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //最好在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                refreshLinkChart();
            }
        });
        return view;
    }

    public void initView(){
        barChart = view.findViewById(R.id.chart01);
        lineChart = view.findViewById(R.id.chart02);
    }

    private void refreshBarChart(){
        Object[] x = new Object[]{
                "Mon", "Tue", "Wed", "Thu", "Fri", "Sta", "Sun"
        };
        Object[] y = new Object[]{
                820, 932, 901, 934, 1290, 600, 300
        };
        barChart.refreshEchartsWithOption(EchartOptionUtil.getBarChartOptions(x, y));
    }
    private void refreshLinkChart(){
        Object[] x = new Object[]{
                "Mon", "Tue", "Wed", "Thu", "Fri"
        };
        Object[] y = new Object[]{
                820, 932, 901, 934, 1290
        };
        barChart.refreshEchartsWithOption(EchartOptionUtil.getLineChartOptions(x, y));
    }
}
