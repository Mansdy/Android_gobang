package com.GameWUZHIQI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by admin on 2016/8/2.
 */
public class WuzhiqiPanel extends View {
    checkFiveinLine cl = new checkFiveinLine();
    private int mPanelWidth;        //棋盘的高度
    private float mLineHeight;      //棋盘单行间距
    private int MAX_LINE = 10;      //棋盘行列数

    private Paint mPaint = new Paint();
    private Bitmap mWhitePiece; //白色棋子
    private Bitmap mBlackPiece; //黑色棋子

    //棋子占行距的比例
    private float ratioPieceOfLineHeight = 3 * 1.0f /4;

    //是否将要下白棋
    private boolean mIsWhite = true;

    private ArrayList<Point> mWhiteArray = new ArrayList<>();    //储存棋子所需要的list
    private ArrayList<Point> mBlackArray = new ArrayList<>();


    //游戏是否结束
    private boolean mIsGameOver ;
    private boolean mIsWhiteWinner;
    public WuzhiqiPanel(Context context, AttributeSet attrs){
        super(context,attrs);
        //setBackgroundColor(0x44ff0000);
        init();
    }

    private void init(){

        mPaint.setColor(0x88000000);
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//防抖动
        mPaint.setStyle(Paint.Style.STROKE);

        //加载棋子
        mWhitePiece = BitmapFactory.decodeResource(getResources(),R.drawable.stone_w2);
        mBlackPiece = BitmapFactory.decodeResource(getResources(),R.drawable.stone_b2);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);  //宽度
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);    //高度
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize,heightSize);

        //解决嵌套在ScrollView中时情况出现的问题
        if(widthMode == MeasureSpec.UNSPECIFIED){
            width = heightSize;
        }else  if (heightMode == MeasureSpec.UNSPECIFIED)
        {
            width = widthSize;
        }
        setMeasuredDimension(width,width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPanelWidth = w;
        mLineHeight = mPanelWidth * 1.0f / MAX_LINE;

        int pieceWidth = (int) (mLineHeight * ratioPieceOfLineHeight);
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece,pieceWidth,pieceWidth,false);
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece,pieceWidth,pieceWidth,false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(mIsGameOver) return false;
        int action = event.getAction();
        if(action ==MotionEvent.ACTION_UP)
        {
            int x = (int) event.getX();
            int y = (int) event.getY();

            Point p = getValidPoint(x,y);

            if(mWhiteArray.contains(p) || mBlackArray.contains(p))
            {
                return false;
            }
            //获取坐标放置对应颜色的棋子
            if(mIsWhite)
            {
                mWhiteArray.add(p);
            }else
            {
                mBlackArray.add(p);
            }
            invalidate();       //请求重绘
            mIsWhite = !mIsWhite;   //改变棋子状态
            return true;
        }
            return true;
    }

    /**
     * 根据触摸点获取最近的格子位置
     * @param x
     * @param y
     * @return
     */
    private Point getValidPoint(int x, int y) {
        return new Point((int)(x / mLineHeight), (int) (y / mLineHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBoard(canvas);
        drawPieces(canvas);
        checkGameOver();
    }

    /**
     * 检查游戏是否结束
     */
    private void checkGameOver()
    {
        boolean whiteWin = cl.checkFiveInLine(mWhiteArray);
        boolean blackWin = cl.checkFiveInLine(mBlackArray);
        if(whiteWin || blackWin)
        {
            mIsGameOver = true;
            mIsWhiteWinner = whiteWin;

            String text = mIsWhiteWinner?"白棋胜利":"黑棋胜利";

            Toast.makeText(getContext(),text,Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 绘制棋子
     * @param canvas
     */
    private void drawPieces(Canvas canvas)
    {
        //绘制白子
        for(int i = 0,n = mWhiteArray.size();i < n ;i++)
        {
            Point whitePoint = mWhiteArray.get(i);
            canvas.drawBitmap(mWhitePiece,
                    (whitePoint.x + (1 - ratioPieceOfLineHeight)/2) * mLineHeight,
                    (whitePoint.y + (1 - ratioPieceOfLineHeight)/2) * mLineHeight,null);
        }
        //绘制黑子
        for(int i = 0,n = mBlackArray.size();i < n ;i++)
        {
            Point blackPoint = mBlackArray.get(i);
            canvas.drawBitmap(mBlackPiece,
                    (blackPoint.x + (1 - ratioPieceOfLineHeight)/2) * mLineHeight,
                    (blackPoint.y + (1 - ratioPieceOfLineHeight)/2) * mLineHeight,null);
        }
    }

    /**
     * 绘制棋盘
     * @param canvas
     */
    private void drawBoard(Canvas canvas) {
        int w = mPanelWidth;
        float lineHeight = mLineHeight;

        for (int i = 0;i<MAX_LINE;i++){
            int startX = (int) (lineHeight /2);
            int endX = (int) (w-lineHeight/2);

            int y = (int) ((0.5 + i)*lineHeight);
            canvas.drawLine(startX,y,endX,y,mPaint);//画横线

            canvas.drawLine(y,startX,y,endX,mPaint);//画竖线
        }
    }

    /**
     * 重新开始
     */

    public void start(){
        mWhiteArray.clear();
        mBlackArray.clear();
        mIsGameOver = false;
        mIsWhiteWinner = false;
        invalidate();
    }

    /**
     * 当View被销毁时需要保存游戏数据
     */
    private static final String INSTANCE = "instance";
    private static final String INSTANCE_GAME_OVER = "instance_game_over";
    private static final String INSTANCE_WHITE_ARRAY = "instance_white_over";
    private static final String INSTANCE_BLACK_ARRAY = "instance_black_over";

    /**
     * 保存游戏数据
     * @return
     */
    @Override
    protected Parcelable onSaveInstanceState()
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE,super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAME_OVER,mIsGameOver);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY,mWhiteArray);
        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAY,mBlackArray);
        return bundle;
    }

    /**
     * 恢复游戏数据
     * @param state
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle){    //判断是否我自己设置的Bundle
            Bundle bundle = (Bundle) state;
            bundle.getBoolean(INSTANCE_GAME_OVER);
            mWhiteArray = bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
            mBlackArray = bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAY);

            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
