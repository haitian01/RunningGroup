package com.example.runninggroup.viewAndController.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.runninggroup.R;

import java.util.Random;

import static androidx.core.os.LocaleListCompat.create;

public class FragmentCard extends Fragment implements View.OnClickListener {
    Button mButton;
    View view;
    ImageView imageView;
    String username;
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
        initImageview();
        return view;
    }

    private void initView() {
        username = getActivity().getIntent().getStringExtra("username");
        mButton = view.findViewById(R.id.btn);
    }
    private void initEvent(){
        mButton.setOnClickListener(this);
    }
    private  void initImageview(){
        imageView = view.findViewById(R.id.famousQuotes);
        Random random = new Random();
        int randomnum = random.nextInt(8);
        if (randomnum == 1){
            imageView.setBackground(getResources().getDrawable(R.drawable.quote01));
        }
        else if (randomnum == 2){
            imageView.setBackground(getResources().getDrawable(R.drawable.quote02));
        }
        else if (randomnum == 3){
            imageView.setBackground(getResources().getDrawable(R.drawable.quote03));
        }
        else if (randomnum == 4){
            imageView.setBackground(getResources().getDrawable(R.drawable.quote04));
        }
        else if (randomnum == 5){
            imageView.setBackground(getResources().getDrawable(R.drawable.quote05));
        }
        else if (randomnum == 6){
            imageView.setBackground(getResources().getDrawable(R.drawable.quote06));
        }
        else if (randomnum == 7){
            imageView.setBackground(getResources().getDrawable(R.drawable.quote07));
        }
        else {
            imageView.setBackground(getResources().getDrawable(R.drawable.quote08));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:

                final String[] items3 = new String[]{"手环", "手动输入与截图"};//创建item
                AlertDialog alertDialog3 = new AlertDialog.Builder(getActivity())
                        .setTitle("选择您的打卡方式")
                        .setIcon(R.mipmap.ic_launcher)
                        .setItems(items3, new DialogInterface.OnClickListener() {//添加列表
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               switch (i){
                                   case 1:
                                       Intent intent = new Intent(getActivity(),CardPersonal.class);
                                       intent.putExtra("username",username);
                                       startActivity(intent);
                                       break;
                               }
                            }
                        })
                        .create();
                alertDialog3.show();
                break;
        }
    }
}
