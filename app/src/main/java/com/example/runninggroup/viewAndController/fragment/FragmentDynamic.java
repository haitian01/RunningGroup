package com.example.runninggroup.viewAndController.fragment;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoFriend;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.request.ImgUpload;
import com.example.runninggroup.viewAndController.PersonalSetting;
import com.example.runninggroup.viewAndController.adapter.DynamicAdapter;
import com.example.runninggroup.viewAndController.helper.DynamicHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class FragmentDynamic extends Fragment {
    View mView;
    ListView dynamicListView;
    List<DynamicHelper> mList;
    String name;
    String group;
    String length;
    String username;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamic,container,false);
        initView();
        initEvent();
        return mView;
    }


    private void initView() {
        username = getActivity().getIntent().getStringExtra("username");
        group = getActivity().getIntent().getStringExtra("group");
        length = getActivity().getIntent().getStringExtra("length");
        name = getActivity().getIntent().getStringExtra("name");
        dynamicListView = mView.findViewById(R.id.dynamic);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                mList = DaoFriend.getDynamic(name);
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dynamicListView.setAdapter(new DynamicAdapter(getLayoutInflater(),mList,name));
    }
    private void initEvent() {

    }
}
