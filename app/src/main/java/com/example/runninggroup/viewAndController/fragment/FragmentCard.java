package com.example.runninggroup.viewAndController.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.runninggroup.R;

import static androidx.core.os.LocaleListCompat.create;

public class FragmentCard extends Fragment implements View.OnClickListener {
    Button mButton;
    View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_card,container,false);

        initView();
        initEvent();
        return view;
    }

    private void initView() {
        mButton = view.findViewById(R.id.btn);
    }
    private void initEvent(){
        mButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:

                final String[] items3 = new String[]{"苍老湿", "小泽老湿", "波多野结衣老湿", "吉泽明步老湿"};//创建item
                AlertDialog alertDialog3 = new AlertDialog.Builder(getActivity())
                        .setTitle("选择您喜欢的老湿")
                        .setIcon(R.mipmap.ic_launcher)
                        .setItems(items3, new DialogInterface.OnClickListener() {//添加列表
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getActivity(), "点的是：" + items3[i], Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();
                alertDialog3.show();
                break;
        }
    }
}
