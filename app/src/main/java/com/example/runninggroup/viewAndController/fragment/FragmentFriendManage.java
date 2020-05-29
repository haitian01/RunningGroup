package com.example.runninggroup.viewAndController.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.runninggroup.R;
import com.example.runninggroup.model.DaoFriend;
import com.example.runninggroup.model.DaoUser;
import com.example.runninggroup.viewAndController.MainInterface;

public class FragmentFriendManage extends Fragment {
    View mView;
    ListView friendManageList;
    Drawable mDrawable;
    String username;
    String name;
    String group;
    String length;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_friendmanage,container,false);
        initView();
        initEvent();
        return mView;
    }

    private void initView() {
        username = getActivity().getIntent().getStringExtra("username");
        name = getActivity().getIntent().getStringExtra("name");
        group = getActivity().getIntent().getStringExtra("group");
        length = getActivity().getIntent().getStringExtra("length");
        friendManageList = mView.findViewById(R.id.friendmanage_list);
        Thread t = new Thread(() -> {
            mDrawable = DaoUser.getImg(DaoUser.getUserHeadImgName(name));
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void initEvent() {
        friendManageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                switch (position){
                    case 0:
                        //删除好友
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if(DaoFriend.deleteFriend(username,name)){
                                   makeToast("删除成功");
                                   Intent intent = new Intent(getActivity(), MainInterface.class);
                                   intent.putExtra("username",username);
                                   intent.putExtra("group",group);
                                   intent.putExtra("name",name);
                                   intent.putExtra("length",length);
                                   intent.putExtra("id",2);
                                   startActivity(intent);

                                }else {
                                    makeToast("删除失败");
                                }
                            }
                        }).start();
                        break;
                }
            }
        });
    }
    private void makeToast(final String msg){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
