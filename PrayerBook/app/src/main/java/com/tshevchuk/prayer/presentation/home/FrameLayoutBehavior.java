package com.tshevchuk.prayer.presentation.home;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import hugo.weaving.DebugLog;

/**
 * Created by taras on 09.06.16.
 */
public class FrameLayoutBehavior extends CoordinatorLayout.Behavior<FrameLayout> {
    @DebugLog
    public FrameLayoutBehavior() {
    }

    @DebugLog
    public FrameLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @DebugLog
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FrameLayout child, View dependency) {
        return dependency instanceof Toolbar;
    }

    @DebugLog
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FrameLayout child, View dependency) {
        if (dependency instanceof Toolbar) {
            child.setTranslationY(dependency.getTranslationY() + dependency.getHeight());
            return true;
        }
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
