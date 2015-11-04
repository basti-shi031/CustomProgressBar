package basti.coryphaei.com.learnview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Bowen on 2015-11-04.
 */
public class RingView extends View {
    private Paint mPaintArc,mPaintText;
    private float sweepAngle = 0;
    private float startAngle = -90;//弧线的起点为90度方向

    private RectF mRect;
    private int length;
    private int width,height;
    private static final int FLAG_WIDTH = 0;
    private static final int FLAG_HEIGHT = 1;
    private int textSize = 50;
    private int mCircleXY = 0;
    private int mProgress = 0;
    public RingView(Context context) {
        this(context, null);
    }

    public RingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaintArc = new Paint();
        mPaintArc.setColor(getResources().getColor(android.R.color.holo_blue_light));
        mPaintArc.setStrokeWidth(5);
        mPaintArc.setAntiAlias(true);
        mPaintArc.setStyle(Paint.Style.STROKE);

        mPaintText = new Paint();
        mPaintText.setTextSize(textSize);
        mPaintText.setColor(getResources().getColor(android.R.color.black));
        mPaintText.setAntiAlias(true);
        mPaintText.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        setMeasuredDimension(measureUtils(widthMode, widthSize, FLAG_WIDTH), measureUtils(heightMode, heightSize, FLAG_HEIGHT));

        initView();
    }

    private void initView() {

        length = Math.min(width,height);
        mCircleXY = length/2;
        mRect = new RectF(length*0.1f,length*0.1f,length*0.9f,length*0.9f);
    }

    private int measureUtils(int mode, int size,int flag) {
        int result = 0;

        if (mode == MeasureSpec.EXACTLY){
            //表示match_parent和精确值
            setValue(flag,size);
            return size;
        }else {
            result = 200;
            if (mode == MeasureSpec.AT_MOST){
                //精确值
                setValue(flag,Math.min(result,size));
                return Math.min(result,size);
            }
            setValue(flag,Math.min(result,size));
        }
        return result;
    }

    private void setValue(int flag,int size) {
        switch (flag){
            case FLAG_WIDTH:
                width = size;break;
            case FLAG_HEIGHT:
                height = size;break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mRect, startAngle, sweepAngle, false, mPaintArc);
        canvas.drawText(getProgressText(),
                0,
                getProgressText().length(),
                mCircleXY, mCircleXY + (textSize / 4), mPaintText);
    }

    private String getProgressText() {
        return mProgress+"%";
    }

    public void setSweepAngle(float arc){
        if (arc<0){
            return ;
        }if (arc>=360){
            setVisibility(INVISIBLE);
        }

        sweepAngle = arc;

    }

    public void setProgress(int progress){
        mProgress = progress;
        setSweepAngle(((float) mProgress) / 100 * 360);
        startAngle = sweepAngle-90;
        this.invalidate();
    }
}
