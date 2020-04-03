package com.aas.footballapp.ui.favorite


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager

import com.aas.footballapp.R
import com.aas.footballapp.ui.MainActivity.Companion.INSTANCE
import com.aas.footballapp.ui.favorite.favoriteevent.FavoriteEventFragment
import com.aas.footballapp.ui.favorite.favoriteteam.FavoriteTeamFragment
import com.aas.footballapp.util.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    private lateinit var mAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    companion object{
        const val INTENT_REQUEST_CODE = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = view.findViewById(R.id.vp_favorite)
        tabLayout = view.findViewById(R.id.tl_favorite)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        mAdapter = ViewPagerAdapter(childFragmentManager)
        mAdapter.addFragment(FavoriteEventFragment())
        mAdapter.addFragment(FavoriteTeamFragment())
        viewPager.adapter = mAdapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(tabLayoutListener)
        super.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(INSTANCE, FavoriteFragment::class.java.simpleName)
        super.onSaveInstanceState(outState)
    }

    private val tabLayoutListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {

        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {

        }

        override fun onTabSelected(tab: TabLayout.Tab) {
            viewPager.currentItem = tab.position
        }

    }

}
