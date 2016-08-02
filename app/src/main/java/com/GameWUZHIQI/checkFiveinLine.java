package com.GameWUZHIQI;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/8/2.
 */
public class checkFiveinLine {
    protected int MAX_COUNT_IN_LINE =5;//设置结束游戏棋子的数量
    public boolean checkFiveInLine(ArrayList<Point> points) {
        for (Point p : points)
        {
            int x = p.x;
            int y = p.y;

            boolean win =  checkHorizontal(x,y,points);
            if(win)return true;
            win =  checkVertical(x,y,points);
            if(win)return true;
            win =  checkLeftDiagonal(x,y,points);
            if(win)return true;
            win =  checkRigtDiagonal(x,y,points);
            if(win)return true;
        }
        return false;
    }
    /**
     * 判断x,y位置的棋子，是否横向有相邻的五个棋子
     * @param x
     * @param y
     * @param points
     * @return
     */
    private boolean checkHorizontal(int x, int y, List<Point> points) {
        int count = 1;
        //左
        for (int i = 1; i<MAX_COUNT_IN_LINE;i++)
        {
            if(points.contains(new Point(x -i,y)))
            {
                count++;
            }else
            {
                break;
            }
        }
        if(count == MAX_COUNT_IN_LINE) return true;
        //右
        for (int i = 1; i<MAX_COUNT_IN_LINE;i++)
        {
            if(points.contains(new Point(x +i,y)))
            {
                count++;
            }else
            {
                break;
            }
        }

        if(count == MAX_COUNT_IN_LINE) return true;
        return false;
    }

    /**
     * 判断x,y位置的棋子，是否纵向有相邻的五个棋子
     * @param x
     * @param y
     * @param points
     * @return
     */
    private boolean checkVertical(int x, int y, List<Point> points) {
        int count = 1;
        //上
        for (int i = 1; i<MAX_COUNT_IN_LINE;i++)
        {
            if(points.contains(new Point(x,y-i)))
            {
                count++;
            }else
            {
                break;
            }
        }
        if(count == MAX_COUNT_IN_LINE) return true;
        //下
        for (int i = 1; i<MAX_COUNT_IN_LINE;i++)
        {
            if(points.contains(new Point(x,y+i)))
            {
                count++;
            }else
            {
                break;
            }
        }

        if(count == MAX_COUNT_IN_LINE) return true;
        return false;
    }

    /**
     * 判断x,y位置的棋子，是否左斜向有相邻的五个棋子
     * @param x
     * @param y
     * @param points
     * @return
     */
    private boolean checkLeftDiagonal(int x, int y, List<Point> points) {
        int count = 1;
        //上
        for (int i = 1; i<MAX_COUNT_IN_LINE;i++)
        {
            if(points.contains(new Point(x -i,y + i)))
            {
                count++;
            }else
            {
                break;
            }
        }
        if(count == MAX_COUNT_IN_LINE) return true;
        //下
        for (int i = 1; i<MAX_COUNT_IN_LINE;i++)
        {
            if(points.contains(new Point(x +i,y - i)))
            {
                count++;
            }else
            {
                break;
            }
        }

        if(count == MAX_COUNT_IN_LINE) return true;
        return false;
    }
    /**
     * 判断x,y位置的棋子，是否右斜向有相邻的五个棋子
     * @param x
     * @param y
     * @param points
     * @return
     */
    private boolean checkRigtDiagonal(int x, int y, List<Point> points) {
        int count = 1;
        //上
        for (int i = 1; i<MAX_COUNT_IN_LINE;i++)
        {
            if(points.contains(new Point(x -i,y - i)))
            {
                count++;
            }else
            {
                break;
            }
        }
        if(count == MAX_COUNT_IN_LINE) return true;
        //下
        for (int i = 1; i<MAX_COUNT_IN_LINE;i++)
        {
            if(points.contains(new Point(x +i,y + i)))
            {
                count++;
            }else
            {
                break;
            }
        }

        if(count == MAX_COUNT_IN_LINE) return true;
        return false;
    }

}
