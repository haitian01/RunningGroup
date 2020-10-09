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
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.viewAndController.adapter.PersonSearchAdapter;

import java.util.List;

public class FragmentPersonSearch extends Fragment {
    View view;
    ListView mListView;
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
        mListView.setAdapter(new PersonSearchAdapter(getLayoutInflater(), Cache.user));
    }

    private void initView() {
        mListView = view.findViewById(R.id.listView);
    }
}
