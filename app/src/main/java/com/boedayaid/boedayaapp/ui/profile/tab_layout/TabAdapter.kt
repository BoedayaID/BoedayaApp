package com.boedayaid.boedayaapp.ui.profile.tab_layout

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.boedayaid.boedayaapp.ui.profile.tab_layout.list.ListFragment

class TabAdapter(activity: AppCompatActivity) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ListFragment.newInstance("bucket")
            1 -> ListFragment.newInstance("history")
            else -> ListFragment()
        }
    }

}