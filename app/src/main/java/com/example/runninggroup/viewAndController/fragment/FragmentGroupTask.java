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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        list=new ArrayList<GroupTaskHelper>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Bundle arguments = getArguments();
                list = DaoGroup.getGroupTask(arguments.getString("group"));

                if ("[null]".equals(list.toString())){
//                    Looper.prepare();
//                    Toast.makeText(getActivity(),"网络异常",Toast.LENGTH_SHORT).show();
//                    Looper.loop();
                    list = new ArrayList<GroupTaskHelper>();
                    Log.v("tag","网络异常555");
                }else {
                    for(GroupTaskHelper groupTaskHelper:list){
                        groupTaskHelper.setImg(R.mipmap.defaultpic);
                    }
                }

            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        view =inflater.inflate(R.layout.fragment_grouptask,container,false);
        initView();
        initEvent();

        return view;
    }

    private void initView() {
        mListView = view.findViewById(R.id.grouptask_listview);
        mListView.setAdapter(new GroupTaskAdapter(getLayoutInflater(),list));
    }
    private void initEvent() {
    }
}
