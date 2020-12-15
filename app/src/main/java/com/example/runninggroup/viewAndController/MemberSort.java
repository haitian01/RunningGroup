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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MemberSort extends AppCompatActivity implements UserController.UserControllerInterface, FileController.FileControllerInterface {
    TextView rankingTxt, usernameTxt, sortedTxt, firstNameTxt, sortedTitleTxt;
    ImageView myHeadImg, firstHeadImg, backHeadImg;
    RelativeLayout titleRela;
    ListView memberList;
    List<User> mUsers = new ArrayList<>();
    private MemberAdapter memeberAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
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

        sortedTitleTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sortedString = sortedTitleTxt.getText().toString();
                if ("里程".equals(sortedString)) sortedTitleTxt.setText("分数");
                else sortedTitleTxt.setText("里程");
                selectUserByUserBack(mUsers);

            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                selectTeamUser();
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
        mSwipeRefreshLayout = findViewById(R.id.refresh);
        sortedTitleTxt = findViewById(R.id.sortedTitle);


        memeberAdapter = new MemberAdapter(mUsers, this, "length");
        memberList.setAdapter(memeberAdapter);



        selectTeamUser();

    }

    /**
     * 查出同一跑团的所有成员
     */
    public void selectTeamUser () {
        User user = new User();
        user.setTeam(Cache.user.getTeam());
        mUserController.selectUserByUser(user);
    }

    @Override
    public void selectUserByUserBack(List<User> users) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                if (users == null)
                    Toast.makeText(MemberSort.this, "网络故障", Toast.LENGTH_SHORT).show();
                else if(users.size() == 0)
                    Toast.makeText(MemberSort.this, "跑团已经解散！", Toast.LENGTH_SHORT).show();
                else {
                    /**
                     * 排序
                     */
                    String sortedString = sortedTitleTxt.getText().toString();

                    User[] myUsers = new User[users.size()];
                    for (int i = 0; i < myUsers.length; i++) {
                        myUsers[i] = users.get(i);
                    }
                    Arrays.sort(myUsers, new Comparator<User>() {
                        @Override
                        public int compare(User o1, User o2) {
                            if ("里程".equals(sortedString)) {
                                if (o1.getLength() == o2.getLength()) return 0;
                                return o1.getLength() > o2.getLength() ? -1 : 1;
                            }else {
                                if (o1.getScore() == o2.getScore()) return 0;
                                return o1.getScore() > o2.getScore() ? -1 : 1;
                            }
                        }
                    });
                    mUsers = new ArrayList<>();
                    for (int i = 0; i < myUsers.length; i++) {
                        mUsers.add(myUsers[i]);
                    }


                    //根据user数量，设置listview的长度
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(MemberSort.this, mUsers.size() * 90));
                    layoutParams.addRule(RelativeLayout.BELOW, titleRela.getId());
                    memberList.setLayoutParams(layoutParams);
                    memberList.setAdapter(new MemberAdapter(mUsers, MemberSort.this, sortedString));

                    //设置个人排名
                    rankingTxt.setText(getRanking(Cache.user.getId()) + "");
                    usernameTxt.setText(Cache.user.getUsername());
                    if (sortedString.equals("里程")) sortedTxt.setText(Cache.user.getLength() + "公里");
                    else sortedTxt.setText(Cache.user.getScore() + "分");
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
