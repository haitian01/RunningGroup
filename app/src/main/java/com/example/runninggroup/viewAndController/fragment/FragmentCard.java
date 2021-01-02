package com.example.runninggroup.viewAndController.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.example.runninggroup.viewAndController.ActivityCardRecord;
import com.example.runninggroup.viewAndController.CardPersonal;
import com.example.runninggroup.viewAndController.MainInterface;
import com.example.runninggroup.viewAndController.TeamNoticeActivity;
import com.example.runninggroup.viewAndController.TimerCard;
import com.example.runninggroup.viewAndController.adapter.CardAdapter;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.noober.menu.FloatMenu;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FragmentCard extends Fragment implements ActController.ActControllerInterface,
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener,
        View.OnClickListener {
    View view;
    RecyclerView cardRecordRecy;
    CardAdapter mCardAdapter;
    private List<Act> mActs = new ArrayList<>();
    private ActController mActController = new ActController(this);
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Act mAct;
    private Activity mActivity;
    private List<Act> mActsToday = new ArrayList<>();
    private int year; //当前年
    private int month; //当前月
    private int day; //当前月

    private int mYear;
    private int mMonth; //点击月
    private int mDay; //点击月



    //日历
    TextView mTextMonthDay;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;
    CalendarLayout mCalendarLayout;



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
        initData();
        return view;
    }


    protected void initData() {



//        Map<String, Calendar> map = new HashMap<>();
//        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
//                getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
//        map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "事").toString(),
//                getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
//        map.put(getSchemeCalendar(year, month, 9, 0xFFdf1356, "议").toString(),
//                getSchemeCalendar(year, month, 9, 0xFFdf1356, "议"));
//        map.put(getSchemeCalendar(year, month, 13, 0xFFedc56d, "记").toString(),
//                getSchemeCalendar(year, month, 13, 0xFFedc56d, "记"));
//        map.put(getSchemeCalendar(year, month, 14, 0xFFedc56d, "记").toString(),
//                getSchemeCalendar(year, month, 14, 0xFFedc56d, "记"));
//        map.put(getSchemeCalendar(year, month, 15, 0xFFaacc44, "假").toString(),
//                getSchemeCalendar(year, month, 15, 0xFFaacc44, "假"));
//        map.put(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记").toString(),
//                getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记"));
//        map.put(getSchemeCalendar(year, month, 25, 0xFF13acf0, "假").toString(),
//                getSchemeCalendar(year, month, 25, 0xFF13acf0, "假"));
//        map.put(getSchemeCalendar(year, month, 27, 0xFF13acf0, "多").toString(),
//                getSchemeCalendar(year, month, 27, 0xFF13acf0, "多"));
//        //此方法在巨大的数据量上不影响遍历性能，推荐使用
//        mCalendarView.setSchemeDate(map);


//        mRecyclerView = findViewById(R.id.recyclerView);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.addItemDecoration(new GroupItemDecoration<String, Article>());
//        mRecyclerView.setAdapter(new ArticleAdapter(this));
//        mRecyclerView.notifyDataSetChanged();
    }

    private void initView() {

        mActivity = getActivity();
        cardRecordRecy = view.findViewById(R.id.card_record);
        mSwipeRefreshLayout = view.findViewById(R.id.refresh);
        mCalendarView = view.findViewById(R.id.calendarView);
        /**
         * 日历
         */
        mYear = year = mCalendarView.getCurYear(); //获取年
        mMonth = month = mCalendarView.getCurMonth(); //获取月
        mDay = day = mCalendarView.getCurDay();
        mTextMonthDay = view.findViewById(R.id.tv_month_day);
        mTextYear = view.findViewById(R.id.tv_year);
        mTextLunar = view.findViewById(R.id.tv_lunar);
        mRelativeTool = view.findViewById(R.id.rl_tool);
        mCalendarView =  view.findViewById(R.id.calendarView);
        mTextCurrentDay =  view.findViewById(R.id.tv_current_day);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
                    return;
                }
                mCalendarView.showYearSelectLayout(year);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(year));
            }
        });

        view.findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
            }
        });
        mCalendarLayout = view.findViewById(R.id.calendarLayout);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));;
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

        /**
         * 配置适配器
         */
        mCardAdapter = new CardAdapter(mActsToday, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        cardRecordRecy.setLayoutManager(layoutManager);
        cardRecordRecy.setAdapter(mCardAdapter);

        /**
         * 发送获取活动记录的网络请求
         */
        getActs();

    }

    private void initEvent(){
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
            public void onClick(int position) {
                mAct = mActsToday.get(position);
                   if (mAct != null) {
                       Cache.act = mAct;
                       Intent intent = new Intent(mActivity, ActivityCardRecord.class);
                       startActivity(intent);
                   }
            }

            @Override
            public void onLongClick(int pos) {
                FloatMenu floatMenu = new FloatMenu(getActivity());
                floatMenu.items("删除");
                floatMenu.setOnItemClickListener(new FloatMenu.OnItemClickListener() {

                    @Override
                    public void onClick(View v, int position) {
                        mAct = mActsToday.get(position);
                        if (mAct != null) {
                            mActController.deleteAct(mAct, pos);
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
                    setCalendarView(mActs);
                    selectCurrentDayCardRecord();
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
    public void deleteActBack(boolean res, int position) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mActivity, res ?  "已删除" : "删除失败，请重试", Toast.LENGTH_SHORT).show();
               if (res) {
                   Act remove = mActsToday.remove(position);
                   System.out.println(position + "======================");
                   System.out.println(remove.getRunLen() + "=====================");
                   mActs.remove(remove);
                   mCardAdapter.notifyItemRemoved(position);
                   if (position != mActsToday.size()) {
                       mCardAdapter.notifyItemRangeChanged(position, mActsToday.size() - position);
                   }
               }
            }
        });
    }


    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

    /**
     * 挑选今天的活动并展示
     */
    private void selectCurrentDayCardRecord () {
        mActsToday.clear();
        for (Act act : mActs) {
            Timestamp createTime = act.getEndTime();
            String[] split = createTime.toString().split(" ")[0].split("-");
            int y = Integer.parseInt(split[0]);
            int m = Integer.parseInt(split[1]);
            int d = Integer.parseInt(split[2]);
            if (mYear == y && mMonth == m && mDay == d) mActsToday.add(act);
            mCardAdapter.notifyDataSetChanged();

        }
    }

    public void setCalendarView (List<Act> acts) {
        Map<String, Calendar> map = new HashMap<>();
        for (Act act : acts) {
            Timestamp createTime = act.getEndTime();
            String[] split = createTime.toString().split(" ")[0].split("-");
            int y = Integer.parseInt(split[0]);
            int m = Integer.parseInt(split[1]);
            int d = Integer.parseInt(split[2]);
            map.put(getSchemeCalendar(y, m, d, 0xFF40db25, "卡").toString(),
                    getSchemeCalendar(y, m, d, 0xFF40db25, "卡"));
        }
        //        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(map);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
        mMonth = calendar.getMonth();
        mDay = calendar.getDay();
        selectCurrentDayCardRecord();

//        Log.e("onDateSelected", "  -- " + calendar.getYear() +
//                "  --  " + calendar.getMonth() +
//                "  -- " + calendar.getDay() +
//                "  --  " + isClick + "  --   " + calendar.getScheme());
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

}
