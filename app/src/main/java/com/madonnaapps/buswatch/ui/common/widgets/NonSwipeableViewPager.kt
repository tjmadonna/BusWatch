package com.madonnaapps.buswatch.ui.common.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NonSwipeableViewPager : ViewPager {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onTouchEvent(ev: MotionEvent?): Boolean = false

    override fun performClick(): Boolean = super.performClick()

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean = false

}