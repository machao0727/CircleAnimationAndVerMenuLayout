package com.machao.animationview.animation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.machao.animationview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * create by：mc on 2019/1/17 15:38
 * email:
 * 云
 */
public class CloudView extends View {

    private List<ChildBitmap> bitmaps = new ArrayList<>();
    private int sizeWidth;
    private int sizeHeight;
    private int padding = 20;
    private int during;
    private Paint paint;
    private float scale = 0.1f;//缩放比例一开始是0.1

    public CloudView(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public CloudView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public CloudView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }


    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        paint = new Paint();
        paint.setAntiAlias(true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CloudView, defStyleAttr, 0);
        during = a.getInteger(R.styleable.CloudView_cv_during, 500);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < bitmaps.size(); i++) {
            canvas.drawBitmap(bitmaps.get(i).bitmapDisplay, bitmaps.get(i).getCurrentX() - (bitmaps.get(i).getBitmapW() / 2) * scale, bitmaps.get(i).getCurrentY() - bitmaps.get(i).getBitmapH() / 2, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        sizeWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingRight() - getPaddingLeft();
        sizeHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        int radus = sizeWidth > sizeHeight ? sizeWidth / 3 : sizeHeight / 3;
        int y;
        for (int i = 0; i < bitmaps.size(); i++) {
            double angle = 360 / (bitmaps.size() + 2) * (i + 1);
            if (angle >= 180) angle += 45;
            bitmaps.get(i).setStartX(sizeWidth / 2);
            bitmaps.get(i).setStartY(sizeHeight / 2);
            bitmaps.get(i).setCurrentX(sizeWidth / 2);
            bitmaps.get(i).setCurrentY(sizeHeight / 2);
            if (angle < 90) {
                y = (int) (sizeHeight / 2 - Math.cos(Math.toRadians(angle)) * radus);
                if (y - bitmaps.get(i).getBitmapH() / 2 < 0) {
                    y = bitmaps.get(i).getBitmapH() / 2 + padding;
                }
                bitmaps.get(i).setEndX((int) (Math.sin(Math.toRadians(angle)) * radus + sizeWidth / 2));
                bitmaps.get(i).setEndY(y);
            } else if (angle >= 90 && angle < 180) {
                angle = angle - 90;
                y = (int) (Math.sin(Math.toRadians(angle)) * radus + sizeHeight / 2);
                if (y + bitmaps.get(i).getBitmapH() / 2 > sizeHeight) {
                    y = sizeHeight - padding - bitmaps.get(i).getBitmapH() / 2;
                }
                bitmaps.get(i).setEndY(y);
                bitmaps.get(i).setEndX((int) (Math.cos(Math.toRadians(angle)) * radus + sizeWidth / 2));
            } else if (angle >= 180 && angle < 270) {
                angle = angle - 180;
                y = (int) (Math.cos(Math.toRadians(angle)) * radus + sizeHeight / 2);
                if (y + bitmaps.get(i).getBitmapH() / 2 > sizeHeight) {
                    y = sizeHeight - padding - bitmaps.get(i).getBitmapH() / 2;
                }
                bitmaps.get(i).setEndX((int) (sizeWidth / 2 - Math.sin(Math.toRadians(angle)) * radus));
                bitmaps.get(i).setEndY(y);
            } else {
                angle = angle - 270;
                y = (int) (sizeHeight / 2 - Math.sin(Math.toRadians(angle)) * radus);
                if (y - bitmaps.get(i).getBitmapH() / 2 < 0) {
                    y = bitmaps.get(i).getBitmapH() / 2 + padding;
                }
                bitmaps.get(i).setEndY(y);
                bitmaps.get(i).setEndX((int) (sizeWidth / 2 - Math.cos(Math.toRadians(angle)) * radus));
            }
        }
    }

    private float downX;
    private float downY;
    private boolean isClick;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                isClick = false;
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                float offsetX = event.getX() - downX;
                float offsetY = event.getY() - downY;
                if (offsetX == 0 && offsetY == 0) {//点击事件
                    setClick(downX, downY);
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    private void setClick(float downX, float downY) {
        for (int i = 0; i < bitmaps.size(); i++) {
            if (downX > bitmaps.get(i).getCurrentX()-bitmaps.get(i).getBitmapW()/2 && downX < bitmaps.get(i).getCurrentX() + bitmaps.get(i).getBitmapW()/2
                    && downY > bitmaps.get(i).getCurrentY()-bitmaps.get(i).getBitmapH()/2 && downY < bitmaps.get(i).getCurrentY() + bitmaps.get(i).getBitmapH()/2) {
                if (listener != null) {
                    listener.itemClick(i);
                }
                return;
            }
        }
    }

    public void setIcon(int... icons) {
        bitmaps.clear();
        for (int i = 0; i < icons.length; i++) {
            ChildBitmap bitmap = new ChildBitmap();
            Bitmap bp = BitmapFactory.decodeResource(getResources(), icons[i]);
            bitmap.setBitmap(bp);
            bitmap.setBitmapW(bp.getWidth());
            bitmap.setBitmapH(bp.getHeight());
            Matrix matrix = new Matrix();
            matrix.setScale(0.1f, 0.1f);
            bitmap.setBitmapDisplay(Bitmap.createBitmap(bp, 0, 0, bp.getWidth(), bp.getHeight(), matrix, true));
            bitmaps.add(bitmap);
        }
        requestLayout();
    }


    public void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0.1f, 1.0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scale = (float) animation.getAnimatedValue();
                for (int i = 0; i < bitmaps.size(); i++) {
                    Matrix matrix = new Matrix();
                    matrix.setScale(scale, scale);
                    int translateX = (int) ((bitmaps.get(i).getEndX() - bitmaps.get(i).getStartX()) / 0.9 * scale);//总比例是1.2所以要除以1.2
                    int translateY = (int) ((bitmaps.get(i).getEndY() - bitmaps.get(i).getStartY()) / 0.9 * scale);
                    bitmaps.get(i).setCurrentX(bitmaps.get(i).getStartX() + translateX);
                    bitmaps.get(i).setCurrentY(bitmaps.get(i).getStartY() + translateY);
                    Bitmap bitmapDisplay = Bitmap.createBitmap(bitmaps.get(i).getBitmap(), 0, 0, bitmaps.get(i).getBitmapW(), bitmaps.get(i).getBitmapH(), matrix, true);
                    bitmaps.get(i).setBitmapDisplay(bitmapDisplay);
                }
                invalidate();
            }
        });
        animator.setDuration(during);
        animator.start();
    }

    private onItemClickListener listener;

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public interface onItemClickListener {
        void itemClick(int position);
    }

    public class ChildBitmap {
        private Bitmap bitmap;
        private int bitmapW;
        private int bitmapH;
        private Bitmap bitmapDisplay;
        private int startX;
        private int startY;
        private int endX;
        private int endY;
        private int currentX;
        private int currentY;

        public int getCurrentX() {
            return currentX;
        }

        public void setCurrentX(int currentX) {
            this.currentX = currentX;
        }

        public int getCurrentY() {
            return currentY;
        }

        public void setCurrentY(int currentY) {
            this.currentY = currentY;
        }

        public int getStartX() {
            return startX;
        }

        public void setStartX(int startX) {
            this.startX = startX;
        }

        public int getStartY() {
            return startY;
        }

        public void setStartY(int startY) {
            this.startY = startY;
        }

        public int getEndX() {
            return endX;
        }

        public void setEndX(int endX) {
            this.endX = endX;
        }

        public int getEndY() {
            return endY;
        }

        public void setEndY(int endY) {
            this.endY = endY;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public int getBitmapW() {
            return bitmapW;
        }

        public void setBitmapW(int bitmapW) {
            this.bitmapW = bitmapW;
        }

        public int getBitmapH() {
            return bitmapH;
        }

        public void setBitmapH(int bitmapH) {
            this.bitmapH = bitmapH;
        }

        public Bitmap getBitmapDisplay() {
            return bitmapDisplay;
        }

        public void setBitmapDisplay(Bitmap bitmapDisplay) {
            this.bitmapDisplay = bitmapDisplay;
        }
    }
}
