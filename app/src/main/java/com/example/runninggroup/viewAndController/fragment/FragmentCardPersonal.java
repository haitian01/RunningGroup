package com.example.runninggroup.viewAndController.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.runninggroup.R;

public class FragmentCardPersonal extends Fragment {

    private Button mBtn_cardperson;
    private EditText mEt_date, mEt_position, mEt_length;
    private RadioGroup rg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cardpersonal,container,false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtn_cardperson = view.findViewById(R.id.card_personal);
        mEt_date = view.findViewById(R.id.date);
        mEt_position = view.findViewById(R.id.position);
        mEt_length = view.findViewById(R.id.length);
        rg = view.findViewById(R.id.radio_group);

        mBtn_cardperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.card_personal:
                        Toast.makeText(getContext(),"打卡完成",Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.single:
                        Log.e("rb","选择个人打卡");
                        break;
                    case R.id.group:
                        Log.e("rb","选择跑团打卡");
                        break;

                }
            }
        });

        }

    }

