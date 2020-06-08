package com.example.runninggroup.viewAndController.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoAct;
import com.example.runninggroup.model.DaoFriend;
import com.example.runninggroup.viewAndController.adapter.DynamicAdapter;
import com.example.runninggroup.viewAndController.adapter.PersonalCardAdapter;
import com.example.runninggroup.viewAndController.helper.DynamicHelper;
import com.example.runninggroup.viewAndController.helper.PersonalCardHelper;

import java.util.List;

public class FragmentPersonalCard extends Fragment {
    View mView;
    ListView cardListView;
    List<PersonalCardHelper> mList;
    String name;
    String group;
    String length;
    String username;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_personalcard,container,false);
        initView();
        initEvent();
        return mView;
    }


    private void initView() {
        username = getActivity().getIntent().getStringExtra("username");
        group = getActivity().getIntent().getStringExtra("group");
        length = getActivity().getIntent().getStringExtra("length");
        name = getActivity().getIntent().getStringExtra("name");
        cardListView = mView.findViewById(R.id.cardlist);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                mList = DaoAct.queryCard(name);
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cardListView.setAdapter(new PersonalCardAdapter(getLayoutInflater(),mList));
    }
    private void initEvent() {

    }
}
