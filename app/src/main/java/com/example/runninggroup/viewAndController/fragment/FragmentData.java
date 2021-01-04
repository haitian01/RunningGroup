package com.example.runninggroup.viewAndController.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.util.Log;
import android.widget.Toast;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runninggroup.R;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;
import com.example.runninggroup.viewAndController.TimeAndData.GetData;
import com.example.runninggroup.viewAndController.adapter.CardAdapter;


public class FragmentData extends Fragment implements BaseSliderView.OnSliderClickListener,ViewPagerEx.OnPageChangeListener {
    private SliderLayout mSlider;
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
    }

    private void initView() {
        Context context = getActivity();
        mSlider = view.findViewById(R.id.slider);

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
