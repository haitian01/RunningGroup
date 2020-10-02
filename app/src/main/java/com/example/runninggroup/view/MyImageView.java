package com.example.runninggroup.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;


public class MyImageView extends ImageView {

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		//得到Drawable对象
		Drawable drawable = getDrawable();
		if (drawable == null) {
			return;
		}
		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}
		// 得到图片
		Bitmap b = ((BitmapDrawable) drawable).getBitmap();
		if (b == null) {
			return;
		}
		Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

		int w = getWidth(), h = getHeight();
		int diameter = w >= h ? h : w;
		Bitmap roundBitmap = getCroppedBitmap(bitmap, diameter);
		canvas.drawBitmap(roundBitmap, 0, 0, null);
	}

	/*
	 * 获取圆形图片 1.需要图片 2.图片宽高
	 */
	private Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
		Bitmap sbmp;
		if (bmp.getWidth() != radius || bmp.getHeight() != radius)
			sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
		else
			sbmp = bmp;

		Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xffa19774;
		final Paint paint = new Paint();

		final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());
		// 设置为true，则图像在动画进行中会滤掉对Bitmap图像的优化操作
		// paint.setFilterBitmap(true);
		// paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		// 绘制目标图层
		canvas.drawCircle(sbmp.getWidth() / 2, sbmp.getHeight() / 2,
				sbmp.getWidth() / 2 - 6f, paint);
		// 设置混合模式：在两者相交的地方绘制源图像
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		// 绘制源图层
		canvas.drawBitmap(sbmp, rect, rect, paint);
		// 绘制边框
		drawCircleBorder(canvas, sbmp.getWidth() / 2 - 6f, color,
				sbmp.getWidth() / 2, sbmp.getHeight() / 2);
		return output;
	}

	// 外圆
	private void drawCircleBorder(Canvas canvas, float radius, int color,
			float defaultWidth, float defaultHeight) {
		Paint paint = new Paint();
		// 去锯齿
		paint.setAntiAlias(true);
		// 外圆颜色
		paint.setColor(Color.WHITE);
		// 设置paint的　style　为STROKE：空心
		paint.setStyle(Paint.Style.STROKE);
		// 设置paint的宽度
		paint.setStrokeWidth(6.0f);
		canvas.drawCircle(defaultWidth, defaultHeight, radius, paint);
	}
}