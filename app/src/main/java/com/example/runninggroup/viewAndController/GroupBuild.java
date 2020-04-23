package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.viewAndController.fragment.FragmentGroup;

public class GroupBuild extends AppCompatActivity implements View.OnClickListener {
    Button build;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupbuild);
        initView();
        initEvent();
    }

    private void initView() {
        build = findViewById(R.id.build);
    }
    private void initEvent() {
        build.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.build:
                Toast.makeText(this,"创建成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainInterface.class);
                intent.putExtra("jump",3);
                startActivity(intent);
        }
    }
}
