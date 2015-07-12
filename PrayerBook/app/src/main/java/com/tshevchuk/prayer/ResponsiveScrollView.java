package com.tshevchuk.prayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ResponsiveScrollView extends ScrollView {
	private boolean isFling;
	private int startScrollingY;
	private OnEndScrollListener mOnEndScrollListener;
	public ResponsiveScrollView(Context context) {
		this(context, null, 0);
	}

	public ResponsiveScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ResponsiveScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void fling(int velocityY) {
		super.fling(velocityY);
		isFling = true;
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldX, int oldY) {
		super.onScrollChanged(x, y, oldX, oldY);
		if (Math.abs(y - oldY) < 2 || y >= getMeasuredHeight() || y == 0) {
			if (mOnEndScrollListener != null) {
				mOnEndScrollListener.onEndScroll(y > oldY, isFling, y - startScrollingY);
			}
			if (isFling) {
				isFling = false;
			}
		}
	}

	public void setOnEndScrollListener(OnEndScrollListener mOnEndScrollListener) {
		this.mOnEndScrollListener = mOnEndScrollListener;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(ev.getActionMasked() == MotionEvent.ACTION_DOWN){
			startScrollingY = getScrollY();
		}
		return super.onTouchEvent(ev);
	}

	public interface OnEndScrollListener {
		void onEndScroll(boolean moveContentUp, boolean isFling, int dy);
	}
}
