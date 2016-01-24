package com.example.hg_mannen.redditimg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by hg-mannen on 2015-12-07.
 */
public class SwipeTraceView extends View {

    public Paint paint;
    public ArrayList<float[]> dots = new ArrayList<float[]>();

    public SwipeTraceView(Context context) {
        super(context);
        init();
    }
    public SwipeTraceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeTraceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(!dots.isEmpty()){
            for(int i=0; i<dots.size(); i++) {
                float x = dots.get(i)[0];
                float y = dots.get(i)[1];
                paint.setAlpha((int)((255*i/20.0f)));
                canvas.drawCircle(x, y, 3.0f*i, paint);
            }
        }
        canvas.translate(150, 0);
    }
    private void init(){

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(50);
        paint.setColor(Color.LTGRAY);
    }
    public void pressDraw(float xpos,float ypos)
    {
        if(dots.size()> 20){
            dots.remove(0);
        }
        dots.add(new float[] {xpos,ypos});
        this.invalidate();
    }
    public void killDraw()
    {
        if(!dots.isEmpty())
        {
            dots.clear();
            this.invalidate();
        }
    }


}
