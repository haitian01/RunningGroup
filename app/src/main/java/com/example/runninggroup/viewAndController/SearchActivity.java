package com.example.runninggroup.viewAndController;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;

public class SearchActivity extends AppCompatActivity {
    ListView searchListView;
    ImageView deleteImg;
    TextView cancelText;
    EditText msgEdt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initEvent();
    }

    private void initEvent() {

    }

    private void initView() {
        searchListView = findViewById(R.id.search_list);
        deleteImg = findViewById(R.id.delete);
        cancelText = findViewById(R.id.cancel);
        msgEdt = findViewById(R.id.msg);
    }
}
