package com.talyeh3.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by talyeh3 on 22/01/2019.
 */

public class LineView extends View
{
    private Paint paint = new Paint();

    private PointF pointA, pointB;

    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        int color = ContextCompat.getColor(getContext(), R.color.ColorButtum);
        paint.setColor(color);
        paint.setStrokeWidth(4);
        canvas.drawLine(pointA.x,pointA.y,pointB.x,pointB.y,paint);
        super.onDraw(canvas);
    }

    public void setPointA(PointF point)
    {
        pointA = point ;
    }

    public void setPointB(PointF point)
    {
        pointB = point ;
    }

    public void draw()
    {
        invalidate();
        requestLayout();
    }
}
