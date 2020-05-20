package com.example.runninggroup.viewAndController.Echart;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.github.abel533.echarts.json.GsonOption;

public class EchartView2 extends WebView {
    private static final String TAG = EchartView2.class.getSimpleName();

    public EchartView2(Context context) {
        this(context, null);
    }

    public EchartView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EchartView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //@SuppressLint("NewApi")

    private void init() {
        if(!isInEditMode()) {
            WebSettings webSettings = getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setSupportZoom(false);
            webSettings.setDisplayZoomControls(false);
            loadUrl("file:///android_asset/echart/myechart1.html");
        }
    }
    public void refreshEchartsWithOption(GsonOption option) {
        if (option == null) {
            return;
        }
        String optionString = option.toString();
        String call = "javascript:loadEcharts('" + optionString + "')";
        loadUrl(call);
    }
}
