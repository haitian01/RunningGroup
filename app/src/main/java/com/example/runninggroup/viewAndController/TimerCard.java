package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.example.runninggroup.R;

import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.ActController;
import com.example.runninggroup.pojo.Act;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.ConstantUtil;
import com.example.runninggroup.util.StatusBarUtils;
import com.example.runninggroup.util.StringUtil;
import com.example.runninggroup.util.TimerUtil;
import com.example.runninggroup.view.KyLoadingBuilder;
import com.example.runninggroup.view.WaringDialogWithTwoButton;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TimerCard extends AppCompatActivity implements AMapLocationListener, ActController.ActControllerInterface {
    private ActController mActController = new ActController(this);
    private MapView mMapView = null;
    private TextView timeTxt;
    private TextView speedTxt; //配速
    private TextView distanceTxt;
    private AMap aMap; //地图控制器对象
    private  MyLocationStyle myLocationStyle;//定位
    private double mLatitude; //经度
    private double mLongitude; //经度
    private double distance; //跑步距离
    public AMapLocationClient mLocationClient; //声明mlocationClient对象
    public AMapLocationClientOption mLocationOption; //声明mLocationOption对象
    boolean start = false;
    private long second = 0;
    private Button startBtn, endBtn;
    boolean end = false;
    TimerUtil mTimerUtil;
    private KyLoadingBuilder kyLoadingBuilder;
    private long sTime;
    private long eTime;
    private Timestamp startTime = new Timestamp(System.currentTimeMillis());
    private Timestamp endTime = new Timestamp(System.currentTimeMillis());//结束时间
    private String totalTime = "00:00:00"; // 总时间
    private double runLen = 0;
    private String place = "未记录"; // 地点







    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timercard);


        initView(savedInstanceState);
        initEvent();
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
            //do something.
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    @Override
    public void addActBack(boolean res) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TimerCard.this, res ? "打卡成功" : "打卡失败", Toast.LENGTH_SHORT).show();
                if (res) {
                    try {
                        eTime = System.currentTimeMillis();
                        if (eTime - sTime < ConstantUtil.MAX_KYLOADING_WAIT_TIME) Thread.sleep(ConstantUtil.MAX_KYLOADING_WAIT_TIME - (eTime - sTime));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    kyLoadingBuilder.dismiss();
                    Intent intent = new Intent(TimerCard.this, MainInterface.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void initEvent() {

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = !start;
                if (start) {
                    if (! end) {
                        if (startBtn.getText().toString().equals("开始")) {
                            startTime = new Timestamp(System.currentTimeMillis());
                        }
                        startBtn.setText("暂停");
                        startTimer();
                    }
                }
                else {

                    if (! end) {
                        startBtn.setText("继续");
                        stopTimer();
                    }
                }


            }
        });
        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = new Timestamp(System.currentTimeMillis());
                totalTime = timeTxt.getText().toString();
                runLen = Double.parseDouble(distanceTxt.getText().toString());
                if (runLen == 0) {
                    WaringDialogWithTwoButton waringDialogWithTwoButton = new WaringDialogWithTwoButton(TimerCard.this,"运动里程过短，结束将不计入跑步记录", "继续跑步", "确认结束");
                    waringDialogWithTwoButton.setOnButtonClickListener(new WaringDialogWithTwoButton.OnButtonClickListener() {
                        @Override
                        public void right() {
                            waringDialogWithTwoButton.dismiss();
                            end = true;
                            stopTimer();
                            stopLocation();
                            Intent intent = new Intent(TimerCard.this, MainInterface.class);
                            startActivity(intent);
                        }

                        @Override
                        public void left() {
                            waringDialogWithTwoButton.dismiss();
                        }
                    });
                    waringDialogWithTwoButton.show();
                }
                else {
                    if (Cache.user != null) {
                        end = true;
                        stopTimer();
                        stopLocation();

                        sTime = System.currentTimeMillis();
                        kyLoadingBuilder = new KyLoadingBuilder(TimerCard.this);
                        kyLoadingBuilder.setText("发表中...");
                        kyLoadingBuilder.show();

                        Act act = new Act();
                        User user = new User();
                        user.setId(Cache.user.getId());
                        act.setBeginTime(startTime);
                        act.setEndTime(endTime);
                        act.setRunLen(runLen);
                        act.setPlace(place);
                        act.setTotalTime(timeTxt.getText().toString());
                        act.setUser(user);
                        mActController.addAct(act);
                    }
                }
            }
        });


    }

    private void initView(@Nullable Bundle savedInstanceState) {
        startBtn = findViewById(R.id.startBtn);
        endBtn = findViewById(R.id.endBtn);
        timeTxt = findViewById(R.id.time);
        distanceTxt = findViewById(R.id.distance);
        speedTxt = findViewById(R.id.speed);
        //全屏显示
        StatusBarUtils.setStatusBarFullTransparent(this);




        mMapView =  findViewById(R.id.map);  //获取地图控件引用
        mMapView.onCreate(savedInstanceState); //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView = findViewById(R.id.map); //定义了一个地图view
        mMapView.onCreate(savedInstanceState);// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        //初始化地图控制器对象
        //        AMap aMap;
        if (aMap == null) {
            aMap = mMapView.getMap();
        }


        mLocationClient = new AMapLocationClient(this);

        mLocationOption = new AMapLocationClientOption(); //初始化定位参数

        mLocationClient.setLocationListener(this); //设置定位监听
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setInterval(2000);//设置定位间隔,单位毫秒,默认为2000ms
        mLocationClient.setLocationOption(mLocationOption);//设置定位参数
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mLocationClient.startLocation();





        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。









    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        stopTimer();
        stopLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null && !end) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                place = amapLocation.getStreet()  + amapLocation.getStreetNum();
//                amapLocation.getStreet();//街道信息
//                amapLocation.getStreetNum();//街道门牌号信息
                //首次定位
                if (mLongitude == 0 && mLatitude == 0) {
                    mLatitude =  amapLocation.getLatitude();
                    mLongitude = amapLocation.getLongitude();
                    return;
                }
                double latitude = amapLocation.getLatitude();//获取纬度
                double longitude = amapLocation.getLongitude(); // 获取经度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息

                double mDistance = AMapUtils.calculateLineDistance(new LatLng(latitude, longitude), new LatLng(mLatitude,  mLongitude));//与上一次的距离差

                //只有处于开始状态的时候，才增加距离，更新配速和里程
                if (start) {
                    distance += mDistance;
                    if (distance != 0) distanceTxt.setText((distance/1000 + "").substring(0, (distance/1000 + "").lastIndexOf(".") + 3)); // distance != 0设置，单位公里
                    if (mDistance == 0) speedTxt.setText("- -"); // 距离差是0则配速不记录
                    else speedTxt.setText(StringUtil.getSpeed(2000l, mDistance/1000)); //实时配速
                    drawLines(mLatitude, mLongitude, latitude, longitude); // 画轨迹
                }
                //赋值给mL
                mLatitude =  amapLocation.getLatitude();
                mLongitude = amapLocation.getLongitude();

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    /**
     * 停止定位
     * @return
     */
    public void stopLocation () {
        if (null != mLocationClient) {
            mLocationClient.stopLocation();
            mLocationClient.unRegisterLocationListener(this);
            mLocationClient.onDestroy();
            mLocationClient = null;
        }
    }

    /**
     * 开启计时
     */
    public void startTimer () {
        mTimerUtil = new TimerUtil(this, second);
        mTimerUtil.start();
    }

    /**
     * 停止计时
     */
    public void stopTimer () {
        if (mTimerUtil != null) {
            second = mTimerUtil.getSecond();
            mTimerUtil.stopThread();
            mTimerUtil = null;
        }
    }


    /**
     *  划线
     * @param m1 上一个点的la
     * @param m2 上一个点的lon
     * @param m3 这个
     * @param m4
     */
    public void drawLines(double m1, double m2, double m3, double m4) {

        PolylineOptions options = new PolylineOptions();
        //上一个点的经纬度
        options.add(new LatLng(m1, m2));
        //当前的经纬度
        options.add(new LatLng(m3, m4));
        options.width(10).geodesic(true).color(Color.GREEN);
        aMap.addPolyline(options);


    }

}
