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
public class WaringDialogWithTwoButton extends Dialog implements OnClickListener {
    public WaringDialogWithTwoButton(Context context, String msg, String left, String right) {
        super(context, R.style.waringDialog);
        //初始化布局
        setContentView(R.layout.dialog_waring_with_two_button);
        Window dialogWindow = getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogWindow.setGravity(Gravity.CENTER);
        TextView msgTxt = findViewById(R.id.msg);
        TextView leftTxt = findViewById(R.id.left);
        TextView rightTxt = findViewById(R.id.right);
        msgTxt.setText(msg);
        leftTxt.setText(left);
        rightTxt.setText(right);
        findViewById(R.id.right).setOnClickListener(this);
        findViewById(R.id.left).setOnClickListener(this);
        setCanceledOnTouchOutside(true);
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (onButtonClickListener != null) {
            switch (v.getId()) {
                case R.id.right:
                    onButtonClickListener.right();
                    break;
                case R.id.left:
                    onButtonClickListener.left();
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
        void right();
        void left();
    }
    public class OnButtonClickListenerAdapter implements OnButtonClickListener {

        @Override
        public void right() {
            dismiss();
        }

        @Override
        public void left() {
            dismiss();
        }
    }
    private OnButtonClickListener onButtonClickListener;
    private OnButtonClickListenerAdapter onButtonClickListenerAdapter;
    public OnButtonClickListener getOnButtonClickListener() {
        return onButtonClickListener;
    }
    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }
    public void setOnButtonClickListenerAdapter(OnButtonClickListenerAdapter onButtonClickListenerAdapter) {
        this.onButtonClickListenerAdapter = onButtonClickListenerAdapter;
    }
}