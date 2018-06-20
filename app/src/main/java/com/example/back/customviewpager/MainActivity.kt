package com.example.back.customviewpager

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        setStatusBarTranslucent(true)

        bottomNavigation.setOnNavigationItemSelectedListener {
            BaseFragment.show(this, it.itemId)
            true
        }
        bottomNavigation.selectedItemId = R.id.action_cards

        ViewCompat.animate(bottomNavigation).translationZ(0f)
        bottomNavigation.translationZ = 0f
        ViewCompat.animate(main_container).translationZ(resources.getDimension(R.dimen.default_elevation))
        main_container.translationZ = resources.getDimension(R.dimen.default_elevation)
    }

    private fun animateNeigbourCards(viewPager: ViewPager, pages: List<View>, xTranslation: Float) {
        val leftCard = if (viewPager.currentItem == 0) -1 else viewPager.currentItem - 1
        val rightCard = if (viewPager.currentItem == pages.size - 1) -1 else viewPager.currentItem + 1
        if (leftCard > -1)
            pages[leftCard].animate().translationX(-xTranslation)
        if (rightCard > -1)
            pages[rightCard].animate().translationX(xTranslation)
    }

    private fun setStatusBarTranslucent(makeTranslucent: Boolean) {
        if (makeTranslucent) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    fun hide() {
        ViewCompat.animate(bottomNavigation).translationZ(0f)
    }

    fun show() {
        ViewCompat.animate(bottomNavigation).translationZ(resources.getDimension(R.dimen.default_elevation))
    }
}
