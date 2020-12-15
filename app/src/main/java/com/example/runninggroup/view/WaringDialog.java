package com.example.runninggroup.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.runninggroup.R;

/**
 * 对话框实现类
 * @author admin
 *
 */
public class WaringDialog extends Dialog implements OnClickListener {
    public WaringDialog(Context context, String msg, String title, String define) {
        super(context, R.style.waringDialog);
        //初始化布局
        setContentView(R.layout.dialog_waring);
        Window dialogWindow = getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogWindow.setGravity(Gravity.CENTER);
        TextView msgTxt = findViewById(R.id.msg);
        TextView titleTxt = findViewById(R.id.title);
        TextView defineTxt = findViewById(R.id.define);
        msgTxt.setText(msg);
        titleTxt.setText(title);
        defineTxt.setText(define);
        findViewById(R.id.define).setOnClickListener(this);
        setCanceledOnTouchOutside(false);
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (onButtonClickListener != null) {
            switch (v.getId()) {
                case R.id.define:
                    onButtonClickListener.define();
                    break;
                default:
                    break;
            }
        }
    }
    /**
     * 按钮的监听器
     * @author Orathee
     * @date 2014年3月20日 下午4:28:39
     */
    public interface OnButtonClickListener{
        void define();
    }
    private OnButtonClickListener onButtonClickListener;
    public OnButtonClickListener getOnButtonClickListener() {
        return onButtonClickListener;
    }
    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }
}