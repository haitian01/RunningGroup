package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.ConstantUtil;
import com.example.runninggroup.util.StringUtil;
import com.example.runninggroup.viewAndController.adapter.SearchAdapter;

import java.util.List;

public class PersonSearchActivity extends AppCompatActivity implements UserController.UserControllerInterface {
    ListView searchListView;
    ImageView deleteImg;
    TextView cancelText;
    EditText msgEdt;
    UserController mUserController = new UserController(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_search);
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
                    deleteImg.setVisibility(View.INVISIBLE);
                }
                if (! StringUtil.isStringNull(msgEdt.getText().toString())) {
                    searchListView.setAdapter(new SearchAdapter(getLayoutInflater(), msgEdt.getText().toString()));
                    searchListView.setVisibility(View.VISIBLE);
                    deleteImg.setVisibility(View.VISIBLE);
                }
            }
        });
        deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgEdt.setText("");
            }
        });
        cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) mUserController.selectUser(msgEdt.getText().toString());
            }
        });

    }

    private void initView() {
        searchListView = findViewById(R.id.search_list);
        deleteImg = findViewById(R.id.delete);
        cancelText = findViewById(R.id.cancel);
        msgEdt = findViewById(R.id.msg);
    }

    @Override
    public void selectUserBack(List<User> users) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (users == null)
                    Toast.makeText(PersonSearchActivity.this, "网络故障", Toast.LENGTH_SHORT).show();
                else if (users.size() == 0)
                    Toast.makeText(PersonSearchActivity.this, "未找到", Toast.LENGTH_SHORT).show();
                else if (users.size() == 1) {
                    Cache.friend = users.get(0);
                    Intent intent = new Intent(getApplicationContext(), FriendMessage.class);
                    intent.putExtra("fromActivity", ConstantUtil.PERSON_SEARCH);
                    startActivity(intent);
                } else {
                    Toast.makeText(PersonSearchActivity.this, "找到" + users.size() + "人", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
