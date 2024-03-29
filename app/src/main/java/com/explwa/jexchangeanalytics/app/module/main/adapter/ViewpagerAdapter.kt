package com.explwa.jexchangeanalytics.app.module.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.explwa.jexchangeanalytics.app.module.marketplace.fragment.MarketplaceFragment
import com.explwa.jexchangeanalytics.app.module.portfolio.fragment.PortfolioFragment
import com.explwa.jexchangeanalytics.app.module.staking.fragment.StakingFragment


class ViewpagerAdapter(fa: FragmentActivity)
    : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return MarketplaceFragment.newInstance()
            1 -> return PortfolioFragment.newInstance()
            2 -> return StakingFragment.newInstance()
        }
        return MarketplaceFragment.newInstance()
    }
}