package com.example.runninggroup.viewAndController.Echart;

public class EchartRefesh {

    public static void refreshBarChart(Object[] runData, EchartView barChart){
        Object[] x = new Object[]{
                "Mon", "Tue", "Wed", "Thu", "Fri", "Sta", "Sun"
        };
        Object[] y =  runData;
        barChart.refreshEchartsWithOption(EchartOptionUtil.getBarChartOptions(x, y));
    }
    public static void refreshLinkChart(Object[] Month, Object[] groudData, EchartView2 lineChart){
        Object[] x = Month;
        Object[] y =  groudData;
        lineChart.refreshEchartsWithOption(EchartOptionUtil.getLineChartOptions(x, y));
    }
}
