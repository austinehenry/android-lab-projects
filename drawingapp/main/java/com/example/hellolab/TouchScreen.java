package com.example.hellolab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TouchScreen extends View {

    private Paint paint = new Paint();
    private Path path = new Path();

    // Constructor for XML inflation
    public TouchScreen(Context c, AttributeSet a) {
        super(c, a);
        init();
    }

    // Constructor for Java code
    public TouchScreen(Context c) {
        super(c);
        init();
    }

    private void init() {
        paint.setAntiAlias(true); // smooth edges
        paint.setColor(Color.RED);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float X = event.getX();
        float Y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(X, Y);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(X, Y);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }
}
