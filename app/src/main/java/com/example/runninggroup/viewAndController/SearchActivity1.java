package com.example.runninggroup.viewAndController;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.util.StringUtil;
import com.example.runninggroup.viewAndController.adapter.SearchAdapter;

public class SearchActivity1 extends AppCompatActivity {
    ListView searchListView;
    ImageView deleteImg;
    TextView cancelText;
    EditText msgEdt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search1);
        initView();
        initEvent();
    }

    private void initEvent() {
        msgEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtil.isStringNull(msgEdt.getText().toString())) {
                    searchListView.setVisibility(View.INVISIBLE);
                }
                if (! StringUtil.isStringNull(msgEdt.getText().toString())) {
                    searchListView.setAdapter(new SearchAdapter(getLayoutInflater(), msgEdt.getText().toString()));
                    searchListView.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void initView() {
        searchListView = findViewById(R.id.search_list);
        deleteImg = findViewById(R.id.delete);
        cancelText = findViewById(R.id.cancel);
        msgEdt = findViewById(R.id.msg);
    }
}
