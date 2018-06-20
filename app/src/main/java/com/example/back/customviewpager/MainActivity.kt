package com.example.back.customviewpager

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.example.back.customviewpager.databinding.CardLayoutBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        setStatusBarTranslucent(true)

        val displayMetrics = resources.displayMetrics
        viewPager.clipToPadding = false
        viewPager.pageMargin = -resources.getDimensionPixelSize(R.dimen.width) / 3
        viewPager.offscreenPageLimit = 3
        val pages = arrayListOf<View>()
        repeat(3) {
            val view = DataBindingUtil.inflate<CardLayoutBinding>(
                    layoutInflater,
                    R.layout.card_layout,
                    viewPager as ViewGroup,
                    false)

            val oldHeight = view.cv.layoutParams.height
            view.iv.setOnClickListener {
                viewPager.swipeEnabled = view.cv.layoutParams.width == -1
                if (view.cv.layoutParams.width == -1) {
                    view.cv.layoutParams.height = oldHeight
                    view.cv.layoutParams.width = resources.getDimensionPixelSize(R.dimen.width)
                    view.iv.layoutParams.height = resources.getDimensionPixelSize(R.dimen.card_iv_height_collapsed)
                    view.i1?.root?.animate()?.alpha(1f)
                    view.i1?.btn?.isEnabled = true
                    view.i2?.root?.visibility = View.GONE
                    ViewCompat.animate(viewPager).translationZ(0f)
                    view.cv.radius = resources.getDimension(R.dimen.card_corners_radius)
                    view.tvStartCard.visibility = View.GONE
                    view.tvCardType.visibility = View.GONE

                    view.ivInfo.visibility = View.VISIBLE
                    view.ivClose.visibility = View.GONE
                    with(ConstraintSet()) {
                        clone(view.infoCl.parent as ConstraintLayout)
                        setMargin(view.infoCl.id, ConstraintSet.TOP,
                                resources.getDimensionPixelSize(R.dimen.card_collapsed_infocl_top_margin))
                        applyTo(view.infoCl.parent as ConstraintLayout)
                    }

                    animateNeigbourCards(viewPager, pages, 0f)
                } else {
                    view.cv.layoutParams.height = -1
                    view.cv.layoutParams.width = -1
                    view.iv.layoutParams.height = resources.getDimensionPixelSize(R.dimen.card_iv_height_expanded)
                    view.i1?.root?.animate()?.alpha(0f)
                    view.i1?.btn?.isEnabled = false
                    view.i2?.root?.visibility = View.VISIBLE
                    ViewCompat.animate(viewPager).translationZ(resources.getDimension(R.dimen.default_elevation))
                    view.cv.radius = 0f
                    view.tvStartCard.visibility = View.VISIBLE
                    view.tvCardType.visibility = View.VISIBLE

                    view.ivInfo.visibility = View.GONE
                    view.ivClose.visibility = View.VISIBLE
                    with(ConstraintSet()) {
                        clone(view.infoCl.parent as ConstraintLayout)
                        setMargin(view.infoCl.id, ConstraintSet.TOP,
                                resources.getDimensionPixelSize(R.dimen.card_expanded_infocl_top_margin))
                        applyTo(view.infoCl.parent as ConstraintLayout)
                    }

                    animateNeigbourCards(viewPager, pages, displayMetrics.widthPixels / 2f)
                }
                TransitionManager.beginDelayedTransition(view.cv)
            }

            view.i1?.btn?.setOnClickListener {
                Log.e("---", " btn LEARN!")
            }
            view.tvStartCard.setOnClickListener {
                Log.e("---", " btn tvStartCard!")
            }

            pages.add(view.root)
        }
        val adapter = CustomPagerAdapter(pages)
        viewPager.adapter = adapter
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
}
