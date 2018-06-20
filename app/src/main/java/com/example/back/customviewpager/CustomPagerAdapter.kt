package com.example.back.customviewpager

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup


class CustomPagerAdapter(private val pages: List<View>) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = pages[position]
        container.addView(view)

        return view
    }

    override fun isViewFromObject(view: View, obj: Any) = view == obj

    override fun getCount() = pages.size

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }
}