package com.example.runninggroup.viewAndController.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.ActController;
import com.example.runninggroup.pojo.Act;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.viewAndController.CardPersonal;
import com.example.runninggroup.viewAndController.MainInterface;
import com.example.runninggroup.viewAndController.TimerCard;
import com.example.runninggroup.viewAndController.adapter.CardAdapter;
import com.noober.menu.FloatMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FragmentCard extends Fragment implements View.OnClickListener, ActController.ActControllerInterface {
    Button mButton;
    View view;
    RecyclerView cardRecordRecy;
    CardAdapter mCardAdapter;
    private List<Act> mActs = new ArrayList<>();
    private ActController mActController = new ActController(this);
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Act mAct;
    private Activity mActivity;
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
        mActivity = getActivity();
        mButton = view.findViewById(R.id.btn);
        cardRecordRecy = view.findViewById(R.id.card_record);
        mSwipeRefreshLayout = view.findViewById(R.id.refresh);


        /**
         * 配置适配器
         */
        mCardAdapter = new CardAdapter(mActs, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        cardRecordRecy.setLayoutManager(layoutManager);
        cardRecordRecy.setAdapter(mCardAdapter);

        /**
         * 发送获取活动记录的网络请求
         */
        getActs();

    }
    private void initEvent(){
        mButton.setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getActs();
            }
        });

        /**
         * 设置点击事件监听(分享or不分享)
         */
        mCardAdapter.setOperateImgOnClickListener(new CardAdapter.OperateImgOnClickListener() {
            @Override
            public void shareImgOnClick(Act act) {
                FloatMenu floatMenu = new FloatMenu(getActivity());
                floatMenu.items("删除");
                floatMenu.setOnItemClickListener(new FloatMenu.OnItemClickListener() {

                    @Override
                    public void onClick(View v, int position) {
                        if(position == 0) {

                            if (act != null) {
                                mAct = act;
                                mActController.deleteAct(mAct);
                            }
                        }
                    }
                });
                floatMenu.show(((MainInterface)getActivity()).point);
            }
        });
    }

    /**
     * 发送获取活动记录的网络请求
     *
     */
    public void getActs () {
        Act act = new Act();
        User user = new User();
        user.setId(Cache.user.getId());
        act.setUser(user);
        mActController.selectAct(act);
    }


    /**
     * 拉去打卡记录回调
     * @param acts
     */


    @Override
    public void selectActBack(List<Act> acts) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                if (acts == null) Toast.makeText(getActivity(), "网络故障", Toast.LENGTH_SHORT).show();
                else {
                    mActs.clear();
                    mActs.addAll(acts);
                    mCardAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 更新Act回调
     * @param res
     */
    @Override
    public void updateActBack(boolean res) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mActivity, res ? "success" : "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 删除Act回调
     * @param res
     */
    @Override
    public void deleteActBack(boolean res) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mActivity, res ? "success" : "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:

                final String[] items3 = new String[]{"计时跑步", "手动录入"};//创建item
                AlertDialog alertDialog3 = new AlertDialog.Builder(getActivity())
                        .setTitle("选择您的打卡方式")
                        .setIcon(R.drawable.paobu)
                        .setItems(items3, new DialogInterface.OnClickListener() {//添加列表
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               switch (i){
                                   case 0:
                                       Intent intent1 = new Intent(getActivity(), TimerCard.class);
                                       startActivity(intent1);

                                       break;
                                   case 1:
                                       Intent intent = new Intent(getActivity(), CardPersonal.class);
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
