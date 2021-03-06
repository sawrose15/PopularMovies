package com.sawrose.popularmovies.ui.activity

import android.app.Activity
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.transition.AutoTransition
import android.transition.Fade
import android.view.MenuItem
import android.view.View
import com.sawrose.popularmovies.R
import com.sawrose.popularmovies.ui.fragment.MoviePagerFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(),HasSupportFragmentInjector  {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private val mainPagerFragment: MoviePagerFragment = MoviePagerFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mainPagerFragment)
                .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0){
            super.onBackPressed()
        }else{
            supportFragmentManager.popBackStackImmediate()
        }

    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    fun addFragment(fragment: Fragment, sharedElement: View, transitionName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mainPagerFragment.sharedElementEnterTransition = AutoTransition()
            mainPagerFragment.sharedElementReturnTransition = AutoTransition()
            fragment.enterTransition = Fade()
            fragment.exitTransition = Fade()
            fragment.sharedElementEnterTransition = AutoTransition()
            fragment.sharedElementReturnTransition = AutoTransition()
        }

        supportFragmentManager.beginTransaction()
                .addSharedElement(sharedElement, transitionName)
                .add(R.id.container, fragment)
                .addToBackStack(fragment.toString())
                .commit()
    }

    fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                .add(R.id.container, fragment)
                .addToBackStack(fragment.toString())
                .commit()
    }
}
