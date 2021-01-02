package com.example.runninggroup.viewAndController.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.runninggroup.R;
import com.example.runninggroup.util.WindowsEventUtil;
import com.example.runninggroup.viewAndController.MainInterface;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

/**
 * 单张图片显示Fragment
 */
public class FragmentImageDetail extends Fragment {
    private Drawable mDrawable;
    private ImageView mImageView;
    private RelativeLayout back;
    private View v;

    public FragmentImageDetail(Drawable drawable) {
        mDrawable = drawable;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_image_detail,
                container, false);
       initView();
       initEvent();
        return v;
    }

    private void initEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowsEventUtil.systemBack();
            }
        });

    }

    private void initView() {
        back = v.findViewById(R.id.back);
        mImageView = v.findViewById(R.id.image);
        mImageView.setImageDrawable(mDrawable);
    }
}