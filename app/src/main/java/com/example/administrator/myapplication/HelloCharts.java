package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Administrator on 2017/12/7 0007.
 */

public class HelloCharts extends Activity {

    private LineChartView lineChart;
    Button btnback;

    String[] date = {"10-22","11-22","12-22","1-22","6-22","5-23","5-22","6-22","5-23","5-22"};//X轴的标注
    int[] score= {7,7,7,7,7,7,7,7,7,7};//图表的数据点
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

    //柱状图所需变量*******************
    public final static String[] months = new String[] { "0-10", "11-20", "21-30",
            "31-40", "41-50", "51-60", "61-70", "71-80", "81-90", "91-100", "101-110", "111-120", };

    ColumnChartView columnChart;
    ColumnChartData columnData;
    List<Column> lsColumn = new ArrayList<Column>();
    List<SubcolumnValue> lsValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hellocharts);

        btnback=findViewById(R.id.btnback3);
        lineChart = (LineChartView)findViewById(R.id.line_chart);
        columnChart=findViewById(R.id.columnChartView);
        getAxisXLables1();//获取x轴的标注
        getAxisPoints1();//获取坐标点
        initLineChart();//初始化折线图
        initColumnChart();//初始化柱状图

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ESP8266Wifi.class));
            }
        });



    }

    /**
     * 设置X 轴的显示
     */
    private void getAxisXLables1(){
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }
    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints1() {
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]+(float) Math.random()));
        }
    }

        private void initLineChart(){
            Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
            List<Line> lines = new ArrayList<Line>();
            line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
            line.setCubic(false);//曲线是否平滑，即是曲线还是折线
            line.setFilled(false);//是否填充曲线的面积
            line.setHasLabels(true);//曲线的数据坐标是否加上备注
            //      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
            line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
            line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
            lines.add(line);
            LineChartData data = new LineChartData();
            data.setLines(lines);

            //坐标轴
            Axis axisX = new Axis(); //X轴
            axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
            axisX.setTextColor(Color.WHITE);  //设置字体颜色
            //axisX.setName("date");  //表格名称
            axisX.setTextSize(10);//设置字体大小
            axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
            axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
            data.setAxisXBottom(axisX); //x 轴在底部
            //data.setAxisXTop(axisX);  //x 轴在顶部
            axisX.setHasLines(true); //x 轴分割线

            // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
            Axis axisY = new Axis();  //Y轴
            axisY.setName("");//y轴标注
            axisY.setTextSize(10);//设置字体大小
            data.setAxisYLeft(axisY);  //Y轴设置在左边
            //data.setAxisYRight(axisY);  //y轴设置在右边


            //设置行为属性，支持缩放、滑动以及平移
            lineChart.setInteractive(true);
            lineChart.setZoomType(ZoomType.HORIZONTAL);
            lineChart.setMaxZoom((float) 2);//最大方法比例
            lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
            lineChart.setLineChartData(data);
            lineChart.setVisibility(View.VISIBLE);
            /**注：下面的7，10只是代表一个数字去类比而已
             * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
             */
            Viewport v = new Viewport(lineChart.getMaximumViewport());
            v.left = 0;
            v.right= 7;
            lineChart.setCurrentViewport(v);
        }

         private void initColumnChart(){
             int numSubcolumns = 1;
             int numColumns = months.length;

             List<AxisValue> axisValues = new ArrayList<AxisValue>();
             List<Column> columns = new ArrayList<Column>();
             List<SubcolumnValue> values;
             for (int i = 0; i < numColumns; ++i) {

                 values = new ArrayList<SubcolumnValue>();
                 for (int j = 0; j < numSubcolumns; ++j) {
                     values.add(new SubcolumnValue((float) Math.random() + 30,
                             ChartUtils.pickColor()));
                 }
                 // 点击柱状图就展示数据量
                 axisValues.add(new AxisValue(i).setLabel(months[i]));

                 columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
             }

             columnData = new ColumnChartData(columns);

             columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true)
                     .setTextColor(Color.BLACK));
             columnData.setAxisYLeft(new Axis().setHasLines(true)
                     .setTextColor(Color.BLACK).setMaxLabelChars(2));

             columnChart.setColumnChartData(columnData);

             // Set value touch listener that will trigger changes for chartTop.
             columnChart.setOnValueTouchListener(new ValueTouchListener());

             // Set selection mode to keep selected month column highlighted.
             columnChart.setValueSelectionEnabled(true);

             columnChart.setZoomType(ZoomType.HORIZONTAL);

         }

    /**
     * 柱状图监听器
     *
     * @author 1017
     *
     */
    private class ValueTouchListener implements
            ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex,
                                    SubcolumnValue value) {
            // generateLineData(value.getColor(), 100);
        }

        @Override
        public void onValueDeselected() {

            // generateLineData(ChartUtils.COLOR_GREEN, 0);

        }
    }

    }
