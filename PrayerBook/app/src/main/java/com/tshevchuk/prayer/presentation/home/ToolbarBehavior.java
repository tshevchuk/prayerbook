package com.tshevchuk.prayer.presentation.home;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import hugo.weaving.DebugLog;
import timber.log.Timber;

/**
 * Created by taras on 09.06.16.
 */
public class ToolbarBehavior extends CoordinatorLayout.Behavior<Toolbar> {
    private boolean toolbarHidingOnScrollEnabled;

    @DebugLog
    public ToolbarBehavior() {
    }

    @DebugLog
    public ToolbarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setToolbarHidingOnScrollEnabled(boolean toolbarHidingOnScrollEnabled) {
        this.toolbarHidingOnScrollEnabled = toolbarHidingOnScrollEnabled;
    }

    @DebugLog
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        return dependency instanceof FrameLayout;
    }

    @DebugLog
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View directTargetChild, View target, int nestedScrollAxes) {
        return (toolbarHidingOnScrollEnabled && nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL)
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @DebugLog
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View target, int dx, int dy, int[] consumed) {
        if (dy > 0) { // move up
            int consumedDy = Math.min(dy, child.getHeight() + (int) child.getTranslationY());
            child.setTranslationY(child.getTranslationY() - consumedDy);
            consumed[1] = consumedDy;
            Timber.d("moveDy: %d", consumedDy);
        } else if (dy < 0) { // move down
            int consumedDy = Math.max(dy, (int) child.getTranslationY());
            child.setTranslationY(child.getTranslationY() - consumedDy);
            consumed[1] = consumedDy;
        }
    }
}
