package com.share.jack.numberkeyboard.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.share.jack.numberkeyboard.R;

/**
 * Created by Jack on 16/10/13
 */
public class FullScreenNumberKeyboardView extends View {

    private Paint mPaint;
    private Bitmap mBpDelete;
    private float clickX, clickY;   //点击时的x,y坐标
    private float mWidth, mHeight;   //屏幕的宽高
    private float mRectWidth, mRectHeight;   //单个按键的宽高
    private float mWidthOfBp, mHeightOfBp;
    private boolean isInit = false;   //view是否已经初始化
    private String number;//点击的数字
    private float[] xs = new float[3];//声明数组保存每一列的矩形中心的横坐标
    private float[] ys = new float[4];//声明数组保存每一排的矩形中心的纵坐标
    private OnNumberClickListener onNumberClickListener;
    private float x1, y1, x2, y2;  //按下的时候所处的矩形的左上和右下的坐标

    /**
     * 判断刷新数据
     * -1 不进行数据刷新
     * 0  按下刷新
     * 1  弹起刷新
     */
    private int type = -1;

    public FullScreenNumberKeyboardView(Context context) {
        super(context);
    }

    public FullScreenNumberKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullScreenNumberKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OnNumberClickListener getOnNumberClickListener() {
        return onNumberClickListener;
    }

    public void setOnNumberClickListener(OnNumberClickListener onNumberClickListener) {
        this.onNumberClickListener = onNumberClickListener;
    }

    @SuppressLint("DrawAllocation")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInit) {
            initData();
        }
        //画键盘
        drawKeyboard(canvas);

        //判断是否点击数字
        if (clickX > 0 && clickY > 0) {
            if (type == 0) {  //按下刷新
                if ("delete".equals(number)) {
                    mPaint.setColor(Color.WHITE);
                    canvas.drawRoundRect(new RectF(x1, y1, x2, y2), 10, 10, mPaint);
                    canvas.drawBitmap(mBpDelete, xs[2] - mWidthOfBp / 2 + 10, ys[3] - mHeightOfBp / 2 - 10, mPaint);
                } else {
                    if (".".equals(number)) {
                        mPaint.setColor(Color.WHITE);
                    } else {
                        mPaint.setColor(Color.GRAY);
                    }
                    canvas.drawRoundRect(new RectF(x1, y1, x2, y2), 10, 10, mPaint);
                    mPaint.setColor(Color.BLACK);
                    mPaint.setTextSize(60);// 设置字体大小
                    mPaint.setStrokeWidth(2);
                    canvas.drawText(number, clickX, clickY, mPaint);
                }
            } else if (type == 1) {  //抬起刷新
                if ("delete".equals(number)) {
                    mPaint.setColor(Color.GRAY);
                    canvas.drawRoundRect(new RectF(x1, y1, x2, y2), 10, 10, mPaint);
                    canvas.drawBitmap(mBpDelete, xs[2] - mWidthOfBp / 2 + 10, ys[3] - mHeightOfBp / 2 - 10, mPaint);
                } else {
                    if (".".equals(number)) {
                        mPaint.setColor(Color.GRAY);
                    } else {
                        mPaint.setColor(Color.WHITE);
                    }
                    canvas.drawRoundRect(new RectF(x1, y1, x2, y2), 10, 10, mPaint);
                    mPaint.setColor(Color.BLACK);
                    mPaint.setTextSize(60);// 设置字体大小
                    mPaint.setStrokeWidth(2);
                    canvas.drawText(number, clickX, clickY, mPaint);
                }
                //绘制完成后,重置
                clickX = 0;
                clickY = 0;
            }
        }
    }

    private void initData() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWidth = getWidth();
        mHeight = getHeight();
        mBpDelete = BitmapFactory.decodeResource(getResources(), R.mipmap.keyboard_backspace);
        mWidthOfBp = mBpDelete.getWidth();
        mHeightOfBp = mBpDelete.getHeight();

        mRectWidth = (mWidth - 40) / 3;   //每个按键左右间距10
        mRectHeight = (mHeight - 50) / 4;//每个按键上下间距10

        xs[0] = mRectWidth / 2;
        xs[1] = (mRectWidth * 3) / 2 + 10;
        xs[2] = (mRectWidth * 5) / 2 + 20;

        ys[0] = mRectHeight / 2 + 25;
        ys[1] = (mRectHeight * 3) / 2 + 35;
        ys[2] = (mRectHeight * 5) / 2 + 45;
        ys[3] = (mRectHeight * 7) / 2 + 55;

        isInit = true;
    }

    /**
     * drawRoundRect(float left, float top, float right, float bottom, float rx, float ry, Paint paint)这种方式在5.0以下的机器上会报错，
     * 需要换成drawRoundRect(RectF rect, float rx, float ry, Paint paint)
     *
     * @param canvas
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawKeyboard(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        //画宫格
        //第一排
        canvas.drawRoundRect(new RectF(10, 10, 10 + mRectWidth, 10 + mRectHeight), 10, 10, mPaint);
        canvas.drawRoundRect(new RectF(20 + mRectWidth, 10, 20 + 2 * mRectWidth, 10 + mRectHeight), 10, 10, mPaint);
        canvas.drawRoundRect(new RectF(30 + 2 * mRectWidth, 10, 30 + 3 * mRectWidth, 10 + mRectHeight), 10, 10, mPaint);
        //第二排
        canvas.drawRoundRect(new RectF(10, 20 + mRectHeight, 10 + mRectWidth, 20 + 2 * mRectHeight), 10, 10, mPaint);
        canvas.drawRoundRect(new RectF(20 + mRectWidth, 20 + mRectHeight, 20 + 2 * mRectWidth, 20 + 2 * mRectHeight), 10, 10, mPaint);
        canvas.drawRoundRect(new RectF(30 + 2 * mRectWidth, 20 + mRectHeight, 30 + 3 * mRectWidth, 20 + 2 * mRectHeight), 10, 10, mPaint);
        //第三排
        canvas.drawRoundRect(new RectF(10, 30 + 2 * mRectHeight, 10 + mRectWidth, 30 + 3 * mRectHeight), 10, 10, mPaint);
        canvas.drawRoundRect(new RectF(20 + mRectWidth, 30 + 2 * mRectHeight, 20 + 2 * mRectWidth, 30 + 3 * mRectHeight), 10, 10, mPaint);
        canvas.drawRoundRect(new RectF(30 + 2 * mRectWidth, 30 + 2 * mRectHeight, 30 + 3 * mRectWidth, 30 + 3 * mRectHeight), 10, 10, mPaint);
        //第四排
        mPaint.setColor(Color.GRAY);
        canvas.drawRoundRect(new RectF(10, 40 + 3 * mRectHeight, 10 + mRectWidth, 40 + 4 * mRectHeight), 10, 10, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawRoundRect(new RectF(20 + mRectWidth, 40 + 3 * mRectHeight, 20 + 2 * mRectWidth, 40 + 4 * mRectHeight), 10, 10, mPaint);
        mPaint.setColor(Color.GRAY);
        canvas.drawRoundRect(new RectF(30 + 2 * mRectWidth, 40 + 3 * mRectHeight, 30 + 3 * mRectWidth, 40 + 4 * mRectHeight), 10, 10, mPaint);


        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(60);// 设置字体大小
        mPaint.setStrokeWidth(2);
        //画数字
        //第一排
        canvas.drawText("1", xs[0], ys[0], mPaint);
        canvas.drawText("2", xs[1], ys[0], mPaint);
        canvas.drawText("3", xs[2], ys[0], mPaint);
        //第二排
        canvas.drawText("4", xs[0], ys[1], mPaint);
        canvas.drawText("5", xs[1], ys[1], mPaint);
        canvas.drawText("6", xs[2], ys[1], mPaint);
        //第三排
        canvas.drawText("7", xs[0], ys[2], mPaint);
        canvas.drawText("8", xs[1], ys[2], mPaint);
        canvas.drawText("9", xs[2], ys[2], mPaint);
        //第四排
        canvas.drawText(".", xs[0], ys[3], mPaint);
        canvas.drawText("0", xs[1], ys[3], mPaint);
        canvas.drawBitmap(mBpDelete, xs[2] - mWidthOfBp / 2 + 10, ys[3] - mHeightOfBp / 2 - 10, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: //按下
                setDefault();
                handleDown(x, y);
                return true;
            case MotionEvent.ACTION_UP: //弹起
                type = 1;//弹起刷新
                invalidate();//刷新界面
                //一次按下结束,返回点击的数字
                if (onNumberClickListener != null) {
                    if (number != null) {
                        if (number.equals("delete")) {
                            onNumberClickListener.onNumberDelete();
                        } else {
                            onNumberClickListener.onNumberReturn(number);
                        }
                    }
                }
                //恢复默认
                setDefault();
                return true;
            case MotionEvent.ACTION_CANCEL:  //取消
                //恢复默认值
                setDefault();
                return true;
            default:
                break;
        }
        return false;
    }

    private void setDefault() {
        clickX = 0;
        clickY = 0;
        number = null;
        type = -1;
    }

    private void handleDown(float x, float y) {
        if (y < mHeight / 2) {
            return;
        }
        if (x >= 10 && x <= 10 + mRectWidth) {   //第一列
            clickX = xs[0];
            if (y >= 10 && y <= 10 + mRectHeight) {  //第一排(1)
                clickY = ys[0];
                x1 = 10;
                y1 = 10;
                x2 = 10 + mRectWidth;
                y2 = 10 + mRectHeight;
                number = "1";
            } else if (y >= 20 + mRectHeight && y <= 20 + 2 * mRectHeight) {  //第二排(4)
                x1 = 10;
                y1 = 20 + mRectHeight;
                x2 = 10 + mRectWidth;
                y2 = 20 + 2 * mRectHeight;
                clickY = ys[1];
                number = "4";
            } else if (y >= 30 + 2 * mRectHeight && y <= 30 + 3 * mRectHeight) {  //第三排(7)
                x1 = 10;
                y1 = 30 + 2 * mRectHeight;
                x2 = 10 + mRectWidth;
                y2 = 30 + 3 * mRectHeight;
                clickY = ys[2];
                number = "7";
            } else if (y >= 40 + 3 * mRectHeight && y <= 40 + 4 * mRectHeight) { //第四排(0)
                x1 = 10;
                y1 = 40 + 3 * mRectHeight;
                x2 = 10 + mRectWidth;
                y2 = 40 + 4 * mRectHeight;
                clickY = ys[3];
                number = ".";
            }
        } else if (x >= 20 + mRectWidth && x <= 20 + 2 * mRectWidth) {  //第二列
            clickX = xs[1];
            if (y >= 10 && y <= 10 + mRectHeight) {  //第一排(2)
                x1 = 20 + mRectWidth;
                y1 = 10;
                x2 = 20 + 2 * mRectWidth;
                y2 = 10 + mRectHeight;
                clickY = ys[0];
                number = "2";
            } else if (y >= 20 + mRectHeight && y <= 20 + 2 * mRectHeight) {  //第二排(5)
                x1 = 20 + mRectWidth;
                y1 = 20 + mRectHeight;
                x2 = 20 + 2 * mRectWidth;
                y2 = 20 + 2 * mRectHeight;
                clickY = ys[1];
                number = "5";
            } else if (y >= 30 + 2 * mRectHeight && y <= 30 + 3 * mRectHeight) {  //第三排(8)
                x1 = 20 + mRectWidth;
                y1 = 30 + 2 * mRectHeight;
                x2 = 20 + 2 * mRectWidth;
                y2 = 30 + 3 * mRectHeight;
                clickY = ys[2];
                number = "8";
            } else if (y >= 40 + 3 * mRectHeight && y <= 40 + 4 * mRectHeight) { //第四排(0)
                x1 = 20 + mRectWidth;
                y1 = 40 + 3 * mRectHeight;
                x2 = 20 + 2 * mRectWidth;
                y2 = 40 + 4 * mRectHeight;
                clickY = ys[3];
                number = "0";
            }
        } else if (x >= 30 + 2 * mRectWidth && x <= 30 + 3 * mRectWidth) {   //第三列
            clickX = xs[2];
            if (y >= 10 && y <= 10 + mRectHeight) {  //第一排(3)
                x1 = 30 + 2 * mRectWidth;
                y1 = 10;
                x2 = 30 + 3 * mRectWidth;
                y2 = 10 + mRectHeight;
                clickY = ys[0];
                number = "3";
            } else if (y >= 20 + mRectHeight && y <= 20 + 2 * mRectHeight) {  //第二排(6)
                x1 = 30 + 2 * mRectWidth;
                y1 = 20 + mRectHeight;
                x2 = 30 + 3 * mRectWidth;
                y2 = 20 + 2 * mRectHeight;
                clickY = ys[1];
                number = "6";
            } else if (y >= 30 + 2 * mRectHeight && y <= 30 + 3 * mRectHeight) {  //第三排(9)
                x1 = 30 + 2 * mRectWidth;
                y1 = 30 + 2 * mRectHeight;
                x2 = 30 + 3 * mRectWidth;
                y2 = 30 + 3 * mRectHeight;
                clickY = ys[2];
                number = "9";
            } else if (y >= 40 + 3 * mRectHeight && y <= 40 + 4 * mRectHeight) { //第四排(删除键)
                x1 = 30 + 2 * mRectWidth;
                y1 = 40 + 3 * mRectHeight;
                x2 = 30 + 3 * mRectWidth;
                y2 = 40 + 4 * mRectHeight;
                clickY = ys[3];
                number = "delete";
            }
        }
        type = 0;   //按下刷新
        invalidate();
    }

    public interface OnNumberClickListener {
        //回调点击的数字
        public void onNumberReturn(String number);

        //删除键的回调
        public void onNumberDelete();
    }
}
