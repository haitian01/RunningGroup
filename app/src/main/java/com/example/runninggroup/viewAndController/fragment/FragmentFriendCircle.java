package com.example.runninggroup.viewAndController.fragment;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.runninggroup.R;
import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.controller.CommentController;
import com.example.runninggroup.controller.FriendCircleController;
import com.example.runninggroup.pojo.Comment;
import com.example.runninggroup.pojo.FriendCircle;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.ConstantUtil;
import com.example.runninggroup.util.NetworkUtils;
import com.example.runninggroup.util.StringUtil;
import com.example.runninggroup.util.WindowsEventUtil;
import com.example.runninggroup.viewAndController.FriendMessage;
import com.example.runninggroup.viewAndController.MainInterface;
import com.example.runninggroup.viewAndController.adapter.FriendCircleAdapter;
import com.noober.menu.FloatMenu;

import java.util.ArrayList;
import java.util.List;

public class FragmentFriendCircle extends Fragment implements FriendCircleController.FriendCircleControllerInterface, CommentController.CommentControllerInterface {
    View mView;
    private RecyclerView mFriendCircleRec;
    private List<FriendCircle> mFriendCircleList = new ArrayList();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FriendCircleAdapter friendCircleAdapter;
    private EditText messageEdt;
    private Button sendBtn;
    private RelativeLayout commentRela;
    private FriendCircleAdapter.InnerHolder mInnerHolder;
    private Comment comment = new Comment();
    private FriendCircleController mFriendCircleController = new FriendCircleController(this);
    private CommentController mCommentController = new CommentController(this);
    private Activity mActivity;
    private RelativeLayout network;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_friend_circle,container,false);
        initView();
        initEvent();
        return mView;
    }




    private void initView() {
        mActivity = getActivity();
        mSwipeRefreshLayout = mView.findViewById(R.id.reload);
        messageEdt = mView.findViewById(R.id.message);
        sendBtn = mView.findViewById(R.id.send);
        network = mView.findViewById(R.id.network);
        commentRela = mView.findViewById(R.id.comment_rela);


        mSwipeRefreshLayout.setEnabled(true);
        mFriendCircleRec = mView.findViewById(R.id.friend_circle_list);

        //设置边距
        ItemDecoration itemDecoration = new ItemDecoration(15);
        mFriendCircleRec.addItemDecoration(itemDecoration);

        //布局
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mFriendCircleRec.setLayoutManager(layoutManager);
        //适配器
        friendCircleAdapter = new FriendCircleAdapter(mFriendCircleList, getActivity());
        /**
         * 上拉加载事件
         */
        friendCircleAdapter.setOnRefreshListener(new FriendCircleAdapter.OnRefreshListener() {
            @Override
            public void onUpPullRefresh(FriendCircleAdapter.LoadHolder loadHolder) {

                if (mFriendCircleList.size() > 0) mFriendCircleController.getFriendCircle(mFriendCircleList.get(mFriendCircleList.size() - 1).getId(), loadHolder);
                else {
                    loadHolder.update(FriendCircleAdapter.LoadHolder.NORMAL);
                }
            }
        });

        friendCircleAdapter.setCommentOnClickListener(new FriendCircleAdapter.CommentOnClickListener() {

            @Override
            public void messageOnClick(Comment comment, FriendCircleAdapter.InnerHolder innerHolder) {
                if (comment != null && innerHolder != null) {
                    messageEdt.setText("");
                    mInnerHolder = innerHolder;
                    FragmentFriendCircle.this.comment.setTo(comment.getFrom());
                    messageEdt.setHint("回复" + comment.getFrom().getUsername() + "：");
                    commentRela.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void messageOnLongClick(Comment comment, FriendCircleAdapter.InnerHolder innerHolder) {
                /**
                 * 如果是评论者或者动态的发布者，则有权删除该条评论
                 * 否则只可以复制
                 */
                if (comment != null && innerHolder != null) {
                    if ((comment.getFrom() != null && Cache.user.getId() == comment.getFrom().getId()) || (innerHolder.mFriendCircle != null && innerHolder.mFriendCircle.getUser() != null && innerHolder.mFriendCircle.getUser().getId() == Cache.user.getId())) {

                        FloatMenu floatMenu = new FloatMenu(getActivity());
                        floatMenu.items("复制", "删除");
                        floatMenu.setOnItemClickListener(new FloatMenu.OnItemClickListener() {
                            @Override
                            public void onClick(View v, int position) {
                                if (position == 0) {
                                    String msg = comment.getMessage();
                                    onClickCopy(msg);
                                    Toast.makeText(getContext(), "已复制", Toast.LENGTH_SHORT).show();
                                }else if (position == 1) {
                                    mInnerHolder = innerHolder;
                                    FragmentFriendCircle.this.comment = comment;
                                    mCommentController.deleteComment(comment);
                                }
                            }
                        });
                        floatMenu.show(((MainInterface)getActivity()).point);


                    }else {
                        FloatMenu floatMenu = new FloatMenu(getActivity());
                        floatMenu.items("复制");
                        floatMenu.setOnItemClickListener(new FloatMenu.OnItemClickListener() {
                            @Override
                            public void onClick(View v, int position) {
                                if (position == 0) {
                                    String msg = comment.getMessage();
                                    onClickCopy(msg);
                                    Toast.makeText(getContext(), "已复制", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        floatMenu.show(((MainInterface)getActivity()).point);
                    }

                }
            }

            @Override
            public void fromOnClick(Comment comment, FriendCircleAdapter.InnerHolder innerHolder) {
                if (comment.getFrom() != null) {
                    Cache.friend = comment.getFrom();
                    Intent intent = new Intent(getContext(), FriendMessage.class);
                    startActivity(intent);
                }
            }

            @Override
            public void toOnClick(Comment comment, FriendCircleAdapter.InnerHolder innerHolder) {
                if (comment.getTo() != null) {
                    Cache.friend = comment.getTo();
                    Intent intent = new Intent(getContext(), FriendMessage.class);
                    startActivity(intent);
                }
            }
        });




        /**
         * 点赞and评论事件触发
         */
        friendCircleAdapter.setControllerOnClickListener(new FriendCircleAdapter.ControllerOnClickListener() {
            @Override
            public void zanOnClick(FriendCircleAdapter.InnerHolder innerHolder) {
                mFriendCircleController.updateZan(innerHolder);

            }

            @Override
            public void commentOnClick(FriendCircleAdapter.InnerHolder innerHolder) {
                //评论框显示、显示软键盘
                comment.setTo(null);
                commentRela.setVisibility(View.VISIBLE);
                mInnerHolder = innerHolder;
                messageEdt.setHint("评论：");
            }

            @Override
            public void deleteOnClick(FriendCircleAdapter.InnerHolder innerHolder) {
                FloatMenu floatMenu = new FloatMenu(getActivity());
                floatMenu.items("删除动态");
                floatMenu.setOnItemClickListener(new FloatMenu.OnItemClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        if (position == 0) {
                            if (innerHolder != null && innerHolder.mFriendCircle != null) {
                                mInnerHolder = innerHolder;
                                mFriendCircleController.deleteFriendCircle(innerHolder, position);
                            }
                        }
                    }
                });
                floatMenu.show(((MainInterface)getActivity()).point);
            }
        });

        /**
         * 图片加载回调监听
         */
        friendCircleAdapter.setImgBackListener(new FriendCircleAdapter.ImgBackListener() {
            @Override
            public void imgBack(Drawable drawable, FriendCircleAdapter.InnerHolder innerHolder) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (drawable != null) innerHolder.headImg.setImageDrawable(drawable);
                    }
                });
            }
        });
        mFriendCircleRec.setAdapter(friendCircleAdapter);
        mFriendCircleController.getFriendCircleFirst();

    }



    private void initEvent () {
        //监听网络状况
        listenNetwork();

        /**
         * 点击跳转到设置
         */
        network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "click", Toast.LENGTH_SHORT).show();
            }
        });
        //下拉刷新
        handleDownPullUpdate();

        /**
         * 监听RecycleView滑动
         */
        mFriendCircleRec.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //隐藏评论框、隐藏键盘
                commentRela.setVisibility(View.GONE);
                WindowsEventUtil.hideSoftInput(getContext(), messageEdt);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });

        /**
         * 评论发送
         */
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comment != null && mInnerHolder != null) {
                    String message = messageEdt.getText().toString();
                    if (StringUtil.isStringNull(message))
                        Toast.makeText(getContext(), "内容为空", Toast.LENGTH_SHORT).show();
                    else {
                        comment.setMessage(message);
                        comment.setFrom(Cache.user);
                        comment.setFriendCircle(mInnerHolder.mFriendCircle);
                        mCommentController.insertComment(comment);
                    }
                }else {
                    Toast.makeText(getContext(), "comment/mInnerHolder = null", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    /**
     * 监听网络
     */
    public void listenNetwork () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    boolean res = NetworkUtils.isNetworkAvailable(mActivity);

                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (res) network.setVisibility(View.GONE);
                            else network.setVisibility(View.VISIBLE);
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * 复制内容到剪切板
     */
    public void onClickCopy(String msg) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(msg);
    }

    /**
     * 删除评论回调
     */

    @Override
    public void deleteCommentBack(boolean res) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), res ? "success" : "fail", Toast.LENGTH_SHORT).show();
                if (res && mInnerHolder != null && comment != null) {
                    mInnerHolder.mCommentList.remove(comment);
                    mInnerHolder.commentAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 删除动态回调
     * @param res
     */
    @Override
    public void deleteFriendCircleBack(boolean res, int position) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mActivity, res ? "已删除" : "删除失败", Toast.LENGTH_SHORT).show();
                if (res && mInnerHolder != null) {

                    mFriendCircleList.remove(mInnerHolder.mFriendCircle);
                    friendCircleAdapter.notifyItemRemoved(position);
                    if (position != mFriendCircleList.size()) {
                        friendCircleAdapter.notifyItemRangeChanged(position, mFriendCircleList.size() - position);
                    }
                }
            }
        });
    }

    /**
     * 评论发送结果回调
     * @param res
     */
    @Override
    public void insertCommentBack(boolean res) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), res ? "发表成功" : "发表失败", Toast.LENGTH_SHORT).show();
                if (res) {
                    mInnerHolder.mCommentList.add(comment);
                    mInnerHolder.commentAdapter.notifyItemInserted(mInnerHolder.mCommentList.size());
                    //隐藏评论框、隐藏键盘,清空输入框
                    messageEdt.setText("");
                    commentRela.setVisibility(View.GONE);
                    WindowsEventUtil.hideSoftInput(getContext(), messageEdt);
                }

            }
        });
    }


    /**
     * 下拉刷新
     */
    private void handleDownPullUpdate() {
        mSwipeRefreshLayout.setEnabled(true);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFriendCircleController.getFriendCircleFirst();
            }
        });
    }


    /**
     * 点赞回调
     * @param res
     * @param innerHolder
     */
    @Override
    public void updateZanBack(boolean res, FriendCircleAdapter.InnerHolder innerHolder) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (res) {
                    int up = innerHolder.mFriendCircle.getZanNum() + 1;
                    int down = innerHolder.mFriendCircle.getZanNum() - 1;
                    String zanGroup = innerHolder.mFriendCircle.getZanGroup();
                    if (zanGroup == null || zanGroup.length() == 0) {
                        innerHolder.zanNum.setText(up + "");
                        innerHolder.mFriendCircle.setZanGroup(Cache.user.getId() + ",");
                        innerHolder.mFriendCircle.setZanNum(up);
                        innerHolder.zanImg.setImageResource(R.drawable.zan_after);
                    }else {
                        String[] split = zanGroup.split(",");
                        StringBuilder newZanGroup = new StringBuilder();
                        boolean add = true;
                        for (String s : split) {
                            if (s.equals(Cache.user.getId() + "")) add = false;
                            else newZanGroup.append(s + ",");
                        }
                        if (add) newZanGroup.append(Cache.user.getId()).append(",");
                        innerHolder.mFriendCircle.setZanGroup(newZanGroup.toString());
                        innerHolder.zanNum.setText(add ? up + "" : down + "");
                        innerHolder.mFriendCircle.setZanNum(add ? up : down);
                        innerHolder.zanImg.setImageResource(add ? R.drawable.zan_after : R.drawable.zan
                        );
                    }

                }
            }
        });
    }

    /**
     * 拉去最新动态回调
     * @param friendCircleList
     */
    @Override
    public void getFriendCircleFirstBack(List<FriendCircle> friendCircleList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (friendCircleList == null) {
                    Toast.makeText(getActivity(), "网络故障", Toast.LENGTH_SHORT).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                else {
                    ViewPager viewPager = mActivity.findViewById(R.id.viewPager);
                    if (viewPager.getCurrentItem() == ConstantUtil.FRAGMENT_FRIEND_CIRCLE)
                        Toast.makeText(getActivity(),  "拉取最新动态 " + friendCircleList.size() + " 条", Toast.LENGTH_SHORT).show();
                    mFriendCircleList.clear();
                    mFriendCircleList.addAll(friendCircleList);
                    mFriendCircleRec.setAdapter(friendCircleAdapter);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    /**
     * 上拉刷新回调
     * @param friendCircleList
     * @param loadHolder
     */
    @Override
    public void getFriendCircleBack(List<FriendCircle> friendCircleList, FriendCircleAdapter.LoadHolder loadHolder) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (friendCircleList == null) {
                    Toast.makeText(getActivity(), "网络故障", Toast.LENGTH_SHORT).show();
                    loadHolder.update(FriendCircleAdapter.LoadHolder.LOAD_FAIL);
                }
                else {
                    if (friendCircleList.size() != 0){
                        mFriendCircleList.addAll(friendCircleList);
                        friendCircleAdapter.notifyDataSetChanged();
                    }
                    loadHolder.update(FriendCircleAdapter.LoadHolder.NORMAL);

                }

            }
        });
    }

    /**
     * RecycleView设置间距
     */
    public class ItemDecoration extends RecyclerView.ItemDecoration {
        public int it;
        public ItemDecoration(int it){
            this.it=it;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.bottom=it;
        }
    }
}
