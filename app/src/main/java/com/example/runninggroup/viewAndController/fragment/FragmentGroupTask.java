package com.example.runninggroup.viewAndController.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoGroup;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.viewAndController.adapter.GroupTaskAdapter;
import com.example.runninggroup.viewAndController.helper.FriendsHelper;
import com.example.runninggroup.viewAndController.helper.GroupTaskHelper;

import java.util.ArrayList;
import java.util.List;

public class FragmentGroupTask extends Fragment {
    View view;
    ListView mListView;
    List<GroupTaskHelper> list;
    String username;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_grouptask,container,false);
        initView();
        initEvent();
        return view;
    }

    private void initView() {
        username = getActivity().getIntent().getStringExtra("username");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Bundle arguments = getArguments();
                list = DaoGroup.getGroupTask(arguments.getString("group"));

                for(GroupTaskHelper groupTaskHelper:list){
                    groupTaskHelper.setImg(DaoUser.getImg(DaoUser.getUserHeadImgName(groupTaskHelper.getRelease_name())));
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mListView = view.findViewById(R.id.grouptask_listview);
        mListView.setAdapter(new GroupTaskAdapter(getLayoutInflater(),list));
    }
    private void initEvent() {
    }
}
