package com.example.runninggroup.viewAndController;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.runninggroup.R;
import com.example.runninggroup.viewAndController.Echart.EchartOptionUtil;
import com.example.runninggroup.viewAndController.Echart.EchartView;
import com.example.runninggroup.viewAndController.TimeAndData.GetTime;

public class TestActivity extends AppCompatActivity {
    public static final String TAG = "MyActivityTest";
    private EchartView barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        barChart = findViewById(R.id.chart01);
        barChart.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //最好在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                refreshEChart();
            }
        });
    }

    private void refreshEChart(){
        Object[] x = new Object[]{
                "Mon", "Tue", "Wed", "Thu", "Fri"
        };
        Object[] y = new Object[]{
                820, 932, 901, 934, 1290
        };
        barChart.refreshEchartsWithOption(EchartOptionUtil.getBarChartOptions(x, y));
    }
}
