package com.example.runninggroup.viewAndController.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.viewAndController.PersonSearchActivity;
import com.example.runninggroup.viewAndController.adapter.PersonSearchAdapter;

import java.util.List;

public class FragmentPersonSearch extends Fragment implements View.OnClickListener {
    View view;
    ListView mListView;
    RelativeLayout numRela;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_person_search,container,false);
        initView();
        initEvent();
        return view;

    }

    private void initEvent() {
        numRela.setOnClickListener(this);


    }

    private void initView() {
        mListView = view.findViewById(R.id.listView);
        numRela = view.findViewById(R.id.numRela);
        mListView.setAdapter(new PersonSearchAdapter(getLayoutInflater(), Cache.user));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.numRela:
                Intent intent = new Intent(getContext(), PersonSearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
