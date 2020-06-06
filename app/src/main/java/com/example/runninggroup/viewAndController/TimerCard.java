package com.example.runninggroup.viewAndController;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

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
import com.example.runninggroup.model.DaoAct;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.request.ImgUpload;
import com.example.runninggroup.util.BitmapUtil;
import com.example.runninggroup.util.CharacterUtil;
import com.example.runninggroup.util.TimerUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class TimerCard extends AppCompatActivity implements View.OnClickListener {
    private MapView mMapView;
    private LocationClient locationClient;
    private LocationClientOption locationOption;
    private BaiduMap baiduMap;
    private boolean firstLocation;
    private MyLocationConfiguration config;
    Button mButton,startBtn,stopBtn,returnBtn;
    TextView startText,stopText,lengthText,timeText;
    ImageView mImageView;
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
    TimeThread timeThread;
    String username;
    long beginTime;
    long endTime;
    String begin_place;
    String end_place;
    Spinner spinner;
    private String[] act_type={"常规跑步","全马/半马","校比赛"};
    private double score = 0;
    String type;
    String sex;
    long length;
    File mFile;
    Bitmap bitmap;
    private String img_src;
    private final int SELECT_PHOTO = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_timercard);
        initView();
        initvent();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PHOTO:
                switch (resultCode) {
                    case RESULT_OK:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Uri uri = data.getData();
                                img_src = uri.getPath();//这是本机的图片路径

                                ContentResolver cr = getContentResolver();
                                try {
                                    InputStream inputStream = cr.openInputStream(uri);
                                    bitmap = BitmapFactory.decodeStream(inputStream);
                                    inputStream.close();

                                    String[] proj = {MediaStore.Images.Media.DATA};
                                    CursorLoader loader = new CursorLoader(TimerCard.this, uri, proj, null, null, null);
                                    Cursor cursor = loader.loadInBackground();
                                    if (cursor != null) {
                                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                                        cursor.moveToFirst();

                                        img_src = cursor.getString(column_index);//图片实际路径

                                    }
                                    cursor.close();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mImageView.setImageBitmap(bitmap);
                                        }
                                    });


                                } catch (FileNotFoundException e) {
                                    Log.e("Exception", e.getMessage(), e);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }).start();

                        break;
                }
                break;


        }

    }

    private void initvent() {
        mButton.setOnClickListener(this);

        startBtn.setOnClickListener(this);
        Drawable sbtn = getResources().getDrawable(R.drawable.kaishi);
        sbtn.setBounds(16,0,100,100);
        startBtn.setCompoundDrawables(sbtn,null,null,null);

        stopBtn.setOnClickListener(this);
        Drawable pbtn = getResources().getDrawable(R.drawable.jieshu);
        pbtn.setBounds(20,0,100,100);
        stopBtn.setCompoundDrawables(pbtn,null,null,null);

        returnBtn.setOnClickListener(this);
        Drawable rbtn = getResources().getDrawable(R.drawable.houtaituichufanhuichu);
        rbtn.setBounds(30,0,110,100);
        returnBtn.setCompoundDrawables(rbtn,null,null,null);


        mImageView.setOnClickListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = act_type[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type = act_type[0];
            }
        });

    }

    private void initView() {
        username = getIntent().getStringExtra("username");
        //性别
        new Thread(new Runnable() {
            @Override
            public void run() {
                sex = DaoUser.getSex(username);
            }
        }).start();
        spinner = findViewById(R.id.act_type);
        mButton = findViewById(R.id.card_personal);
        startBtn = findViewById(R.id.start_run);
        startText = findViewById(R.id.start_position);
        returnBtn = findViewById(R.id.time_return);
        stopText = findViewById(R.id.stop_position);
        lengthText = findViewById(R.id.length);
        timeText = findViewById(R.id.act_time);
        stopBtn = findViewById(R.id.stop_run);
        mMapView = findViewById(R.id.map);
        mImageView = findViewById(R.id.img);
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
        if(timeThread != null){
            timeThread.stopThread();
        }
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
//                Toast.makeText(this, "纬度："+latitude+"\n"+"经度："+longitude+"\n"+"位置："+addr, Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, test.getText().toString(), Toast.LENGTH_SHORT).show();
                try {
                    begin_place = startText.getText().toString();
                    end_place = stopText.getText().toString();
                    length = Long.parseLong(lengthText.getText().toString().substring(0,lengthText.getText().toString().lastIndexOf(".")));
                    //分数
                    switch (type) {
                        case "常规跑步":
                            score = length / 1500.00;
                            break;
                        case "全马/半马":
                            score = length/1000.00;
                            break;
                        case "校比赛":
                            score = length/1000.00;
                            break;
                    }
                    score = ("男".equals(sex)) ? score : 1.5*score;
                    //插入活动记录
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //发表动态
                            try {
                                String path = BitmapUtil.saveMyBitmap(TimerCard.this,bitmap,DaoUser.getCardImgName(username,beginTime));
                                mFile = new File(path);
                                ImgUpload.uploadFile(mFile,DaoUser.getCardImgName(username,beginTime));
                            }catch (Exception e){
                                makeToast("图片未发表");
                            }
                            if(DaoAct.insertAct(username,beginTime,endTime,length,score,type,begin_place,end_place)){
                                runOnUiThread(new Runnable() {
                                    @SuppressLint("ResourceType")
                                    @Override
                                    public void run() {
                                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TimerCard.this);

                                        builder.setMessage("打卡成功").setPositiveButton("返回主页", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(TimerCard.this,MainInterface.class);
                                                intent.putExtra("id",0);
                                                intent.putExtra("username",username);
                                                startActivity(intent);
                                            }
                                        }).setNegativeButton("继续打卡", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).create().show();
                                    }
                                });


                            }else {
                                makeToast("打卡失败");
                            }
                        }
                    }).start();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.start_run:
                if(! start){
                    timeThread = new TimeThread();
                    timeThread.start();
                    beginTime = System.currentTimeMillis();
                    start = true;
                    mLatLng = new LatLng(latitude,longitude);
                    Toast.makeText(this, "开始计时\n"+"纬度："+latitude+"\n"+"经度："+longitude, Toast.LENGTH_SHORT).show();
                    startText.setText(addr);
                }else {
                    Toast.makeText(this, "已经开始计时", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.stop_run:
                if (start){
                    timeThread.stopThread();
                    start = false;
                    endTime = System.currentTimeMillis();
                    Toast.makeText(this, "结束计时\n"+"纬度："+latitude+"\n"+"经度："+longitude+"\n"+distance, Toast.LENGTH_SHORT).show();
                    stopText.setText(addr);
                }else {
                    Toast.makeText(this, "尚未开始计时", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.time_return:
                Intent intent = new Intent(TimerCard.this,MainInterface.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case R.id.img:
                AlertDialog.Builder builder = new AlertDialog.Builder(TimerCard.this);
                builder.setTitle("请选择头像：");
                builder.setMessage("可以通过相机或者相册选取头像。");
                builder.setPositiveButton("相册选取", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, SELECT_PHOTO);
                    }
                });
                builder.create();
                builder.show();
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
                lengthText.setText((distance+"").substring(0,(distance+"").lastIndexOf(".")+2));
            }
        }
    }

    class TimeThread extends Thread{
        boolean running = true;
        TimerUtil mTimerUtil = new TimerUtil();
        @Override
        public void run() {
            mTimerUtil.start();
            while (running){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeText.setText(mTimerUtil.getCurrentTime());
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void stopThread(){
            running = false;
            mTimerUtil.stopThread();
        }



    }

    private void makeToast(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TimerCard.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }














}
