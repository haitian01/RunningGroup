package com.example.runninggroup.viewAndController.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runninggroup.R;

import java.util.List;

public class WriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NORMAL = 0;
    private static final int ADD_IMG = 1;
    private List<Drawable> mList;
    private ImgOnClickListener mImgOnClickListener;
    public WriteAdapter (List<Drawable> list) {
        mList = list;
    }
    public interface ImgOnClickListener {
        //添加图片点击时间
        void addImgOnClick();
        //图片点击事件
        void imgOnClick();
    }
    public void setImgOnClickListener (ImgOnClickListener imgOnClickListener) {
        mImgOnClickListener = imgOnClickListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == NORMAL) {
            view  = View.inflate(parent.getContext(), R.layout.helper_write_normal, null);
            return new InnerHolder(view);
        }else if (viewType == ADD_IMG) {
            view  = View.inflate(parent.getContext(), R.layout.helper_friend_addimg, null);
            return new ImgHolder(view);
        }
        return null;


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InnerHolder) ((InnerHolder) holder).setDate(mList.get(position));

    }

    @Override
    public int getItemCount() {
        return mList.size() < 9 ? mList.size() + 1 : 9;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.size() < 9) {
            return position == mList.size() ? 1 : 0;
        }else return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder{
        private ImageView img;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            init();
            initEvent();
        }

        private void initEvent() {
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mImgOnClickListener != null) {
                        mImgOnClickListener.imgOnClick();
                    }
                }
            });
        }

        private void init() {
            img = itemView.findViewById(R.id.img);
        }

        private void setDate(Drawable drawable) {
            img.setImageDrawable(drawable);
        }
    }
    public class ImgHolder extends RecyclerView.ViewHolder{
        ImageView add_img;

        public ImgHolder(@NonNull View itemView) {
            super(itemView);
            init();
            initEvent();
        }

        private void init() {
            add_img = itemView.findViewById(R.id.add_img);
        }

        private void initEvent() {
            add_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mImgOnClickListener != null) mImgOnClickListener.addImgOnClick();
                }
            });
        }
    }

}
