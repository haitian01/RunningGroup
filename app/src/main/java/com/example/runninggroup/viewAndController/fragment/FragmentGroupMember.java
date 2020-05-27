package com.example.runninggroup.viewAndController.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoGroup;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.viewAndController.adapter.GroupMemberAdapter;
import com.example.runninggroup.viewAndController.helper.GroupTaskHelper;
import com.example.runninggroup.viewAndController.helper.User;

import java.util.ArrayList;
import java.util.List;

public class FragmentGroupMember extends Fragment {
    ListView mListView;
    View view;
    List<User> list;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Bundle arguments = getArguments();
                list = DaoUser.getAllMember(arguments.getString("group"));
                for(User user:list){
                    user.setPic(R.mipmap.user);
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        view =inflater.inflate(R.layout.fragment_groupmember,container,false);

        initView();
        initEvent();

        return view;
    }

    private void initView() {
        mListView = view.findViewById(R.id.groupmember_listview);
        mListView.setAdapter(new GroupMemberAdapter(getLayoutInflater(),list));
    }
    private void initEvent() {
    }
}
