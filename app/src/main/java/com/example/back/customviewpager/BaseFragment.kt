package com.example.back.customviewpager

import android.support.annotation.AnimRes
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity

open class BaseFragment : Fragment() {
    val act
        get() = (activity as? MainActivity)

    companion object {
        fun show(context: AppCompatActivity, itemId: Int) {
            when (itemId) {
                R.id.action_cards -> context.replaceFragmentSafely(CardsFragment(), CardsFragment.TAG)
                else -> context.replaceFragmentSafely(CardsFragment(), CardsFragment.TAG)
            }
        }

        fun FragmentActivity.replaceFragmentSafely(fragment: Fragment,
                                                   tag: String,
                                                   allowStateLoss: Boolean = false,
                                                   @IdRes containerViewId: Int = R.id.main_container,
                                                   @AnimRes enterAnimation: Int = 0,
                                                   @AnimRes exitAnimation: Int = 0,
                                                   @AnimRes popEnterAnimation: Int = 0,
                                                   @AnimRes popExitAnimation: Int = 0) {
            val ft = supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation)
                    .replace(containerViewId, fragment, tag)
            if (!supportFragmentManager.isStateSaved) {
                ft.commit()
            } else if (allowStateLoss) {
                ft.commitAllowingStateLoss()
            }
        }
    }
}