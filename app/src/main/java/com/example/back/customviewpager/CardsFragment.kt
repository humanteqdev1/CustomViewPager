package com.example.back.customviewpager

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.back.customviewpager.databinding.CardLayoutBinding
import com.example.back.customviewpager.databinding.CardsFragmentLayoutBinding
import kotlinx.android.synthetic.main.activity_main.*


class CardsFragment : BaseFragment() {
    companion object {
        val TAG: String = CardsFragment::class.java.simpleName
    }
//
//    private var cardList = arrayListOf<Card>()
//    private val model by viewModel<CardsViewModel>() // Lazy
    // For a eager fetch of the ViewModel, just use val model = getViewModel<CardsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: CardsFragmentLayoutBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.cards_fragment_layout,
                container, false)

        val displayMetrics = resources.displayMetrics
        binding.viewPager.clipToPadding = false
        binding.viewPager.pageMargin = -resources.getDimensionPixelSize(R.dimen.width) / 3
        binding.viewPager.offscreenPageLimit = 3
        val pages = arrayListOf<View>()
        repeat(3) {
            val view = DataBindingUtil.inflate<CardLayoutBinding>(
                    layoutInflater,
                    R.layout.card_layout,
                    binding.viewPager as ViewGroup,
                    false)

            val oldHeight = view.cv.layoutParams.height
            view.iv.setOnClickListener {
                binding.viewPager.swipeEnabled = view.cv.layoutParams.width == -1
                if (view.cv.layoutParams.width == -1) {
                    view.cv.layoutParams.height = oldHeight
                    view.cv.layoutParams.width = resources.getDimensionPixelSize(R.dimen.width)
                    view.iv.layoutParams.height = resources.getDimensionPixelSize(R.dimen.card_iv_height_collapsed)
                    view.i1?.root?.animate()?.alpha(1f)
                    view.i1?.btn?.isEnabled = true
                    view.i2?.root?.visibility = View.GONE
//                    ViewCompat.animate(binding.viewPager).translationZ(0f)
                    ViewCompat.animate(main_container).translationZ(0f)
                    ViewCompat.animate(binding.root).translationZ(0f)
//                    ViewCompat.animate(bottomNavigation).translationZ(resources.getDimension(R.dimen.default_elevation))
                    (act as? MainActivity)?.show()
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

                    animateNeigbourCards(binding.viewPager, pages, 0f)
                } else {
                    view.cv.layoutParams.height = -1
                    view.cv.layoutParams.width = -1
                    view.iv.layoutParams.height = resources.getDimensionPixelSize(R.dimen.card_iv_height_expanded)
                    view.i1?.root?.animate()?.alpha(0f)
                    view.i1?.btn?.isEnabled = false
                    view.i2?.root?.visibility = View.VISIBLE
//                    ViewCompat.animate(binding.viewPager).translationZ(resources.getDimension(R.dimen.default_elevation))
                    ViewCompat.animate(main_container).translationZ(resources.getDimension(R.dimen.default_elevation))
                    ViewCompat.animate(binding.root).translationZ(resources.getDimension(R.dimen.default_elevation))
//                    ViewCompat.animate(bottomNavigation).translationZ(0f)
                    (act as? MainActivity)?.hide()
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

                    animateNeigbourCards(binding.viewPager, pages, displayMetrics.widthPixels / 2f)
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
        binding.viewPager.adapter = adapter


//        context?.let { binding.rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) }
//        LinearSnapHelper().attachToRecyclerView(binding.rv)
//        binding.rv.setHasFixedSize(true)
//        val sliderAdapter = SliderAdapter(cardList, this)
//        sliderAdapter.setHasStableIds(true)
//        binding.rv.adapter = sliderAdapter
//
//        context?.let {
//            val displayMetrics = it.resources.displayMetrics
////        val dpHeight = displayMetrics.heightPixels / displayMetrics.density
//            val dpWidth = displayMetrics.widthPixels
//
//            val cardWidth = resources.getDimensionPixelSize(R.dimen.card_width)
//            val leftOffset = (dpWidth - cardWidth) / 2f
//
//            val dividerItemDecoration = CDividerItemDecoration(
//                    leftOffset.toInt(),
//                    resources.getDimensionPixelOffset(R.dimen.card_spacing_right),
//                    context,
//                    LinearLayoutManager.HORIZONTAL)
//            binding.rv.addItemDecoration(dividerItemDecoration)
//        }
//
//        // Listen to data changes
//        model.model.observe(this@CardsFragment, Observer<BaseModel> { testsModel ->
//            testsModel?.let {
//                if (it.error == null) {
//                    it as CardsModel
//                    cardList.clear()
//                    cardList.addAll(it.cardList)
//                    updateList(binding, sliderAdapter)
//                } else {
//                    dfToast("Error: ${it.error}")
//                }
//            }
//        })
//
//        model.isFinished.observe(this@CardsFragment, Observer {
//            it?.let {
//                if (it && !binding.tvTimeLeft.isVisible) updateView(binding, it)
//                if (!it && binding.tvTimeLeft.isVisible) updateView(binding, it)
//            }
//        })
//
//        model.timeLeft.observe(this, Observer { it?.let { binding.tvTimeLeft.text = it } })
//
//        updateList(binding, sliderAdapter)
//
//        // Show data
//        bg { model.getData() }
//
//        binding.btnShowAnotherCards.setOnClickListener { dozeClick { showAnotherCards() } }
//
//        binding.btnShowMoreCards.setOnClickListener { model.resetCards() }

        return binding.root
    }

    private fun animateNeigbourCards(viewPager: ViewPager, pages: List<View>, xTranslation: Float) {
        val leftCard = if (viewPager.currentItem == 0) -1 else viewPager.currentItem - 1
        val rightCard = if (viewPager.currentItem == pages.size - 1) -1 else viewPager.currentItem + 1
        if (leftCard > -1)
            pages[leftCard].animate().translationX(-xTranslation)
        if (rightCard > -1)
            pages[rightCard].animate().translationX(xTranslation)
    }

//    private fun setStatusBarTranslucent(makeTranslucent: Boolean) {
//        if (makeTranslucent) {
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        } else {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        }
//    }
//    private fun updateList(binding: CardsFragmentLayoutBinding, adapter: SliderAdapter) {
//        if (cardList.isEmpty()) {
//            binding.pb.visibility = View.VISIBLE
//            binding.rv.visibility = View.GONE
//            binding.btnShowAnotherCards.visibility = View.GONE
//        } else {
//            binding.pb.visibility = View.GONE
//            binding.rv.visibility = View.VISIBLE
//            binding.btnShowAnotherCards.visibility = View.VISIBLE
//            adapter.notifyDataSetChanged()
//        }
//    }
//
//    private fun updateView(binding: CardsFragmentLayoutBinding, isOver: Boolean) {
//        if (isOver) {
//            binding.pb.visibility = View.GONE
//            binding.rv.visibility = View.GONE
//            binding.btnShowAnotherCards.visibility = View.GONE
//            binding.iv.visibility = View.VISIBLE
//            binding.tvExhaustedInfo.visibility = View.VISIBLE
//            binding.tvTimeLeft.visibility = View.VISIBLE
//            binding.btnShowMoreCards.visibility = View.VISIBLE
//            model.onStartTimer()
//            (activity as BaseActivity).onShowAchievement()
//        } else {
//            binding.iv.visibility = View.GONE
//            binding.tvExhaustedInfo.visibility = View.GONE
//            binding.tvTimeLeft.visibility = View.GONE
//            binding.btnShowMoreCards.visibility = View.GONE
//            model.onStopTimer()
//        }
//    }
//
//    override fun onCardInfoClicked(id: String, sharedImage: ImageView) {
//        activity?.let {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//                val opt = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        it, sharedImage, ViewCompat.getTransitionName(sharedImage)).toBundle()
//
//                startActivityForResult(it.cardInfoActivityIntent(id, ViewCompat.getTransitionName(sharedImage)),
//                        CODE_CONTENT_PASSED, opt)
//
//
//            } else {
//                startActivityForResult(it.cardInfoActivityIntent(id), CODE_CONTENT_PASSED)
//            }
//        }
//    }
//
//    override fun onCardClicked(id: String, sharedImage: ImageView) {
//        onCardInfoClicked(id, sharedImage)
//    }
//
//    private fun showAnotherCards() {
//        context?.let {
//            TextPopup(it, rightBtnFunc = { model.shuffle() })
//                    .show()
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == CODE_CONTENT_PASSED && resultCode == RESULT_OK) bg { model.getData() }
//    }
}