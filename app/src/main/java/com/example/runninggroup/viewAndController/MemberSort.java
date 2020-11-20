package com.example.runninggroup.viewAndController;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.FileController;
import com.example.runninggroup.controller.UserController;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.ConstantUtil;
import com.example.runninggroup.util.DensityUtil;
import com.example.runninggroup.util.ImgNameUtil;
import com.example.runninggroup.viewAndController.adapter.MemberAdapter;

import java.util.ArrayList;
import java.util.List;

public class MemberSort extends AppCompatActivity implements UserController.UserControllerInterface, FileController.FileControllerInterface {
    TextView rankingTxt, usernameTxt, sortedTxt, firstNameTxt;
    ImageView myHeadImg, firstHeadImg, backHeadImg;
    RelativeLayout titleRela;
    ListView memberList;
    List<User> mUsers = new ArrayList<>();
    private UserController mUserController = new UserController(this);
    private FileController mFileController = new FileController(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_sort);
        initView();
        initEvent();
    }

    private void initEvent() {
        memberList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cache.friend = mUsers.get(position);
                Intent intent = new Intent(MemberSort.this, FriendMessage.class);
                intent.putExtra("fromActivity", ConstantUtil.MEMBER_SORT);
                startActivity(intent);


            }
        });
    }

    private void initView() {
        rankingTxt = findViewById(R.id.ranking);
        usernameTxt = findViewById(R.id.username);
        sortedTxt = findViewById(R.id.sorted);
        firstNameTxt = findViewById(R.id.firstName);
        myHeadImg = findViewById(R.id.myHead);
        firstHeadImg = findViewById(R.id.firstImg);
        memberList = findViewById(R.id.memberList);
        backHeadImg = findViewById(R.id.backImg);
        titleRela = findViewById(R.id.title);

        //

        memberList.setAdapter(new MemberAdapter(mUsers, this, "length"));
        User user = new User();
        user.setTeam(Cache.user.getTeam());
        mUserController.selectUserByUser(user);




    }

    @Override
    public void selectUserByUserBack(List<User> users) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (users == null)
                    Toast.makeText(MemberSort.this, "网络故障", Toast.LENGTH_SHORT).show();
                else if(users.size() == 0)
                    Toast.makeText(MemberSort.this, "跑团已经解散！", Toast.LENGTH_SHORT).show();
                else {
                    mUsers = users;
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(MemberSort.this, mUsers.size() * 90));
                    layoutParams.addRule(RelativeLayout.BELOW, titleRela.getId());
                    memberList.setLayoutParams(layoutParams);
                    memberList.setAdapter(new MemberAdapter(mUsers, MemberSort.this, "length"));
                    //设置个人排名
                    rankingTxt.setText(getRanking(Cache.user.getId()) + "");
                    usernameTxt.setText(Cache.user.getUsername());
                    sortedTxt.setText(Cache.user.getLength() + "m");
                    mUserController.getHeadImg(ImgNameUtil.getUserHeadImgName(Cache.user.getId()));

                    //设置封面
                    firstNameTxt.setText(mUsers.get(0).getUsername());
                    mFileController.getImg(ImgNameUtil.getUserHeadImgName(mUsers.get(0).getId()));
                }
            }
        });
    }
    //根据id获取用户排名
    public int getRanking (int id) {
        for (int i = 0; i < mUsers.size(); i++) {
            if (mUsers.get(i).getId() == Cache.user.getId()) return i + 1;
        }
        return 1;
    }

    @Override
    public void getHeadImgBack(Drawable drawable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (drawable != null) myHeadImg.setImageDrawable(drawable);
            }
        });
    }

    @Override
    public void getImgBack(Drawable drawable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (drawable != null) {
                    firstHeadImg.setImageDrawable(drawable);
                    backHeadImg.setImageDrawable(drawable);
                }
            }
        });
    }
}
