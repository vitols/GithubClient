package com.example.android.githubclient.mainScreen.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


/**
 * Created by admin on 21.03.2018.
 */
class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private var fragments = ArrayList<Pair<Fragment, String>>()

    override fun getItem(position: Int): Fragment {
        return fragments[position].first
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragments[position].second
    }

    fun addFragment(fragment: Fragment, tag: String) {
        fragments.add(Pair(fragment, tag))
    }

}