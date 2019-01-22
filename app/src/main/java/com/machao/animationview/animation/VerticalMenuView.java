package com.machao.animationview.animation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.machao.animationview.R;
import com.machao.animationview.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * create by：mc on 2019/1/17 11:27
 * email:
 * 竖向菜单
 */
public class VerticalMenuView extends ViewGroup {

    private int SizeH = 0;//控件高度
    private int SizeW;//控件宽度
    private int padding;
    private List<Child> children;
    private Context context;
    private int textW;
    private int during;

    public VerticalMenuView(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public VerticalMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public VerticalMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < children.size(); i++) {
            getChildAt(i).layout(children.get(i).getEndX(), children.get(i).getEndY(), children.get(i).getEndX() + children.get(i).getCurrentW(), children.get(i).getEndY() + children.get(i).getChildH());
        }
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VerticalMenuView, defStyleAttr, 0);
        padding = a.getDimensionPixelSize(R.styleable.VerticalMenuView_padding, 20);
        during = a.getInteger(R.styleable.VerticalMenuView_vm_during, 500);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        SizeW = MeasureSpec.getSize(widthMeasureSpec) - getPaddingRight() - getPaddingLeft();
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(SizeW, modeWidth == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : modeWidth);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(SizeH, modeHeight == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : modeHeight);

        for (int i = 0; i < children.size(); i++) {
            getChildAt(i).measure(childWidthMeasureSpec, childHeightMeasureSpec);
            Child item = children.get(i);
            item.setStartX(SizeW);
            item.setStartY(SizeH - padding - item.getChildH());
            item.setEndX(SizeW / getChildCount() * (i + 1) - SizeW / getChildCount() / 2 - item.getChildW() / 2);
            item.setEndY(item.getStartY());
        }
        setMeasuredDimension(SizeW, SizeH);
    }

    public void setText(String... texts) {
        removeAllViews();
        Random random = new Random();
        children = new ArrayList<>();
        //文本宽度
        textW = DensityUtils.dpTopx(35, context);
        for (int i = 0; i < texts.length; i++) {
            View view = View.inflate(context, R.layout.item_vertical_menu_layout, null);
            TextView mTvText = view.findViewById(R.id.mTvText);
            int randH = random.nextInt(30);
            //文本高度
            int textH = DensityUtils.dpTopx(25, context) * texts[i].length() + randH;
            SizeH = textH + padding * 4 > SizeH ? textH + padding * 4 : SizeH;
            Child item = new Child();
            item.setChildW(textW);
            item.setChildH(textH + padding * 2);
            children.add(item);
            mTvText.setText(texts[i]);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.itemClick(finalI);
                    }
                }
            });
            addView(view);
        }
    }

    public void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofInt(0, textW);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                for (int i = 0; i < children.size(); i++) {
                    children.get(i).setCurrentW((Integer) animation.getAnimatedValue());
                    requestLayout();
                }
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

    public class Child {
        private int startX;
        private int startY;
        private int childW;
        private int childH;
        private int endX;
        private int endY;
        private int currentW = 0;

        public int getCurrentW() {
            return currentW;
        }

        public void setCurrentW(int currentW) {
            this.currentW = currentW;
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

        public int getChildW() {
            return childW;
        }

        public void setChildW(int childW) {
            this.childW = childW;
        }

        public int getChildH() {
            return childH;
        }

        public void setChildH(int childH) {
            this.childH = childH;
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
    }

}
