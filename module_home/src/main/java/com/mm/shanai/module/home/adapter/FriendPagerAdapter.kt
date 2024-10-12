package com.mm.shanai.module.home.adapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * @Author: lzm
 * @Date: 2024-09-23 11:09
 * @Description: TODO
 */

class FriendPagerAdapter(
    fragmentManager: FragmentManager,
    fragmentActivity: FragmentActivity,
    private val fragments: List<Fragment>
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getCount(): Int  = fragments.size

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

}
