package com.example.runninggroup.viewAndController.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.viewAndController.RunData;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class FragmentData extends Fragment implements BaseSliderView.OnSliderClickListener,ViewPagerEx.OnPageChangeListener, UserController.UserControllerInterface {
    private SliderLayout mSlider;
    private Button mBtnRundata;
    private TextView sportDataTxt, scoreDataTxt;
    private UserController mUserController = new UserController(this);
    private Activity mActivity;
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_data,container,false);
        initView();
        initEvent();
        return view;
    }

    private void initEvent() {
        mActivity = getActivity();
        sportDataTxt = view.findViewById(R.id.SportData);
        scoreDataTxt = view.findViewById(R.id.Score);
    }

    private void initView() {
        Context context = getActivity();
        mSlider = view.findViewById(R.id.slider);
        mBtnRundata = view.findViewById(R.id.picture_right01);
        mBtnRundata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RunData.class);
                startActivity(intent);
            }
        });
        HashMap<String,Integer> fils_map = new HashMap<String,Integer>();

        fils_map.put("Fighting",R.mipmap.first);
        fils_map.put("Running",R.mipmap.second);
        fils_map.put("Exciting",R.mipmap.third);
        fils_map.put("Relaxing",R.mipmap.fourth);

        for (String name : fils_map.keySet()) {
            TextSliderView textSliderView = new TextSliderView(context);
            textSliderView
                    .description(name)
                    .image(fils_map.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra",name);

            mSlider.addSlider(textSliderView);
        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(this);
    }


    @Override
    public void selectUserByUserBack(List<User> users) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (users != null && users.size() > 0) {
                    Cache.user = users.get(0);
                    sportDataTxt.setText(Cache.user.getLength() + "");
                    scoreDataTxt.setText(Cache.user.getScore() + "");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Cache.user.getId() != null) {
            User user = new User();
            user.setId(Cache.user.getId());
            mUserController.selectUserByUser(user);
        }
    }

    @Override
    public void onStop() {
        mSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Context context = getActivity();
        Toast.makeText(context,slider.getBundle().get("extra") + "" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider demo :","Page Change : " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
