package com.example.runninggroup.viewAndController;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    private BaiduMap baiduMap;
    private boolean firstLocation;
    private MyLocationConfiguration config;
    Button mButton;
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
    }

    private void initView() {
        mButton = findViewById(R.id.card_personal);
        mMapView = findViewById(R.id.map);
        baiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15f);
        baiduMap.setMapStatus(msu);
        // 定位初始化
        locationClient = new LocationClient(this);
        firstLocation =true;
        // 设置定位的相关配置
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        locationClient.setLocOption(option);

//        // 设置自定义图标
//        BitmapDescriptor myMarker = BitmapDescriptorFactory
//                .fromResource(R.mipmap.defaultpic);
//        config = new MyLocationConfiguration(
//                MyLocationConfiguration.LocationMode.FOLLOWING, true, myMarker);


        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                // map view 销毁后不在处理新接收的位置
                if (location == null || mMapView == null)
                    return;
                // 构造定位数据
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                // 设置定位数据
                baiduMap.setMyLocationData(locData);

                // 第一次定位时，将地图位置移动到当前位置
                if (firstLocation)
                {
                    firstLocation = false;
                    LatLng xy = new LatLng(location.getLatitude(),
                            location.getLongitude());
                    MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(xy);
                    baiduMap.animateMapStatus(status);
                }
            }
        });
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
//                DistanceUtil.getDistance();
                LatLng latLng = new LatLng(locationClient.getLastKnownLocation().getLatitude(),locationClient.getLastKnownLocation().getLongitude());
                Toast.makeText(this, latLng.toString(), Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void requestLocation(){
        initLocation();
        locationClient.start();
    }

    private void navigateTo(BDLocation location){
        if(firstLocation){
            LatLng ll=new LatLng(location.getLatitude(),location.getLongitude());//LatLng类用于存放经纬度
            // 第一个参数是纬度值，第二个参数是精度值。这里输入的是本地位置。
            MapStatusUpdate update= MapStatusUpdateFactory.newLatLng(ll);//将LatLng对象传入
            baiduMap.animateMapStatus(update);
            update=MapStatusUpdateFactory.zoomTo(16f);//百度地图缩放范围，限定在3-19之间，可以去小数点位值
            // 值越大，地图显示的信息越精细
            baiduMap.animateMapStatus(update);
            firstLocation=false;//防止多次调用animateMapStatus()方法，以为将地图移动到我们当前位置只需在程序
            // 第一次定位的时候调用一次就可以了。
        }
        MyLocationData.Builder locationBuilder=new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData=locationBuilder.build();
        baiduMap.setMyLocationData(locationData);//获取我们的当地位置
    }
    private void initLocation() {
        LocationClientOption option=new LocationClientOption();
        option.setScanSpan(5000);//表示每5秒更新一下当前位置
        option.setIsNeedAddress(true);
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        // Hight_Accuracy表示高精确度模式，会在GPS信号正常的情况下优先使用GPS定位，在无法接收GPS信号的时候使用网络定位。
        // Battery_Saving表示节电模式，只会使用网络进行定位。
        // Device_Sensors表示传感器模式，只会使用GPS进行定位。
        locationClient.setLocOption(option);
    }

}
