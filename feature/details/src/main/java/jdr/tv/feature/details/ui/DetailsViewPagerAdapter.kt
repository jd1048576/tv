package jdr.tv.feature.details.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ShowDetailsFragment()
            1 -> SeasonsFragment()
            else -> throw IllegalStateException("Illegal position $position")
        }
    }
}
