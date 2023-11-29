package com.example.mpproject.adapters


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mpproject.fragments.CongestFragment
import com.example.mpproject.fragments.Dustragment
import com.example.mpproject.fragments.EventFragment
import com.example.mpproject.fragments.WeatherFragment

private const val NUM_PAGES = 4

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val congestBundle: Bundle,
    private val weatherBundle: Bundle,
    private val dustBundle: Bundle,
    private val eventBundle: Bundle
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = when (position) {
            0 -> {
                val congestFragment = CongestFragment()
                congestFragment.arguments = congestBundle
                congestFragment
            }
            1 -> {
                val weatherFragment = WeatherFragment()
                weatherFragment.arguments = weatherBundle
                weatherFragment
            }
            2 -> {
                val dustFragment = Dustragment()
                dustFragment.arguments = dustBundle
                dustFragment
            }
            else ->  {
                val eventFragment = EventFragment()
                eventFragment.arguments = eventBundle
                eventFragment
            }
        }

        return fragment
    }
}