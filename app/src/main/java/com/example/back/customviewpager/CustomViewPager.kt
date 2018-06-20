package com.example.back.customviewpager

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class CustomViewPager : ViewPager {
    var swipeEnabled = true

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs)

    override fun onTouchEvent(event: MotionEvent) = swipeEnabled && super.onTouchEvent(event)

    override fun onInterceptTouchEvent(event: MotionEvent) = swipeEnabled && super.onInterceptTouchEvent(event)
}