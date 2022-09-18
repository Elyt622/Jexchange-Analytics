package com.explwa.jexchange.app.module.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.explwa.jexchange.app.module.home.fragment.HomeFragment
import com.explwa.jexchange.app.module.tokentx.fragment.MyTxsFragment
import com.explwa.jexchange.app.module.staking.fragment.StakingFragment


class ViewpagerAdapter(fa: FragmentActivity)
    : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return HomeFragment.newInstance()
            1 -> return MyTxsFragment.newInstance()
            2 -> return StakingFragment.newInstance()
        }
        return HomeFragment.newInstance()
    }
}