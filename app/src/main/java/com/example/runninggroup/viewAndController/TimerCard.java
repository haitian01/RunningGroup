package com.example.runninggroup.viewAndController;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.runninggroup.R;
import com.baidu.mapapi.map.MyLocationConfiguration;

public class TimerCard extends AppCompatActivity implements View.OnClickListener {
    private MapView mMapView;
    private LocationClient locationClient;
    private LocationClientOption locationOption;
    private BaiduMap baiduMap;
    private boolean firstLocation;
    private MyLocationConfiguration config;
    Button mButton,startBtn,stopBtn;
    TextView startText,stopText,lengthText;
    //纬度
    double latitude;
    //经度
    double longitude;
    String addr;    //获取详细地址信息
    String country;    //获取国家
    String province;    //获取省份
    String city;    //获取城市
    String district;    //获取区县
    String street;   //获取街道信息
    String adcode;    //获取adcode
    String town;    //获取乡镇信息
    LatLng mLatLng;
    double distance;
    boolean start = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_timercard);
        initView();
        initvent();
    }

    private void initvent() {
        mButton.setOnClickListener(this);
        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);

    }

    private void initView() {
        mButton = findViewById(R.id.card_personal);
        startBtn = findViewById(R.id.start_run);
        startText = findViewById(R.id.start_position);
        stopText = findViewById(R.id.stop_position);
        lengthText = findViewById(R.id.length);
        stopBtn = findViewById(R.id.stop_run);
        mMapView = findViewById(R.id.map);
        baiduMap = mMapView.getMap();
        // 定位初始化
        locationClient = new LocationClient(this);
        firstLocation =true;
        // 设置定位的相关配置
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        locationClient.setLocOption(option);

        // 设置自定义图标
//        BitmapDescriptor myMarker = BitmapDescriptorFactory
//                .fromResource(R.drawable.navi_map);
//        MyLocationConfigeration config = new MyLocationConfigeration(
//                MyLocationConfigeration.LocationMode.FOLLOWING, true, myMarker);


        locationClient.registerLocationListener(new MyLocationListener());
    }

    @Override
    protected void onStart()
    {
        // 如果要显示位置图标,必须先开启图层定位
        baiduMap.setMyLocationEnabled(true);
        if (!locationClient.isStarted())
        {
            locationClient.start();
        }
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        // 关闭图层定位
        baiduMap.setMyLocationEnabled(false);
        locationClient.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()
        mMapView.onDestroy();
        mMapView = null;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()
        mMapView.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()
        mMapView.onPause();
    }












    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_personal:
                Toast.makeText(this, "纬度："+latitude+"\n"+"经度："+longitude+"\n"+"位置："+addr, Toast.LENGTH_SHORT).show();
                break;
            case R.id.start_run:
                start = true;
                mLatLng = new LatLng(latitude,longitude);
                Toast.makeText(this, "开始计时\n"+"纬度："+latitude+"\n"+"经度："+longitude, Toast.LENGTH_SHORT).show();
                startText.setText(addr);
                break;
            case R.id.stop_run:
                start = false;
                Toast.makeText(this, "结束计时\n"+"纬度："+latitude+"\n"+"经度："+longitude+"\n"+distance, Toast.LENGTH_SHORT).show();
                stopText.setText(addr);
                break;
        }
    }


    //注册监听函数
    public class MyLocationListener extends BDAbstractLocationListener{
        @Override
        public void onReceiveLocation(BDLocation location){
//            // map view 销毁后不在处理新接收的位置
//            if (location == null || mMapView == null)
//                return;
//            // 构造定位数据
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(100).latitude(location.getLatitude())
//                    .longitude(location.getLongitude()).build();
//            // 设置定位数据
//            baiduMap.setMyLocationData(locData);
//
//            // 第一次定位时，将地图位置移动到当前位置
            if (firstLocation)
            {
                firstLocation = false;
                LatLng xy = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(xy);
                baiduMap.animateMapStatus(status);
            }


            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            latitude = location.getLatitude();    //获取纬度信息
            longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明


            addr = location.getAddrStr();    //获取详细地址信息
            country = location.getCountry();    //获取国家
            province = location.getProvince();    //获取省份
            city = location.getCity();    //获取城市
            district = location.getDistrict();    //获取区县
            street = location.getStreet();    //获取街道信息
            adcode = location.getAdCode();    //获取adcode
            town = location.getTown();    //获取乡镇信息
            if (start) {
                distance = DistanceUtil.getDistance(new LatLng(latitude,longitude),mLatLng);
                lengthText.setText(distance+"");
            }
        }
    }









}
