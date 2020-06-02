package com.example.runninggroup.viewAndController.Echart;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;

public class EchartOptionUtil {
    public static GsonOption getLineChartOptions(Object[] xAxis, Object[] yAxis) {
        GsonOption option = new GsonOption();
        option.title("跑团参与度：积分值");
//        option.legend("运动总时长");
        option.tooltip().trigger(Trigger.axis);

        ValueAxis valueAxis = new ValueAxis();
        option.yAxis(valueAxis);

        CategoryAxis categorxAxis = new CategoryAxis();
        categorxAxis.axisLine().onZero(false);
        categorxAxis.boundaryGap(true);
        categorxAxis.data(xAxis);
        option.xAxis(categorxAxis);

        Line line = new Line();
        line.smooth(false).name("跑团参与度").data(yAxis).itemStyle().normal().lineStyle().shadowColor("rgba(0,255,255,0.4)");
        option.series(line);
        return option;
    }
    public static GsonOption getBarChartOptions(Object[] xAxis, Object[] yAxis) {
        GsonOption option = new GsonOption();
        option.title("跑步里程：米");
//        option.legend("每日跑步总时长");
        option.tooltip().trigger(Trigger.axis);

        ValueAxis valueAxis = new ValueAxis();
        option.yAxis(valueAxis);

        CategoryAxis categorxAxis = new CategoryAxis();
        categorxAxis.axisLine().onZero(false);
        categorxAxis.boundaryGap(true);
        categorxAxis.data(xAxis);
        option.xAxis(categorxAxis);

        Bar bar = new Bar();
        bar.name("跑步时长").data(yAxis).itemStyle().normal().barBorderColor("rgba(84, 255, 129, 0)");
        //Pie.smooth(false).name("销量").data(yAxis).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
        option.series(bar);
        return option;
    }
}
