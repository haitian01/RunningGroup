package com.example.runninggroup.viewAndController;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.viewAndController.fragment.FragmentImageDetail;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 图片查看器
 */
public class ImageDetail extends AppCompatActivity {

    private ViewPager mPager;
    private int num;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        initView();
        initEvent();



    }

    private void initEvent() {
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        num = getIntent().getIntExtra("num", 0);
        mPager = findViewById(R.id.pager);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(
                getSupportFragmentManager(), Cache.sDrawables);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(num);
    }


    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public ConcurrentHashMap<Integer ,Drawable> mDrawables;



        public ImagePagerAdapter(@NonNull FragmentManager fm,  ConcurrentHashMap<Integer ,Drawable> mDrawables) {
            super(fm);
            this.mDrawables = mDrawables;

        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return new FragmentImageDetail(mDrawables.get(position));
        }

        @Override
        public int getCount() {
            return mDrawables.size();
        }



        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

    }
}
