package com.example.cusnav.fragment

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.cusnav.R
import com.example.cusnav.adapter.TabAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_tab.*


class TabFragment : Fragment() {

    lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // val headerLayout = main_header_layout
        val viewPager = main_view_pager
        tabLayout = main_tab_layout
        val mainTabAdapter = TabAdapter(childFragmentManager)
        viewPager.adapter = mainTabAdapter

        //Home Visible Search Bar
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
//                Log.d("onPageScrolled", "$position")
            }

            override fun onPageSelected(position: Int) {
//                Log.d("onPageSelected", "$position")
                if (position == 0) {
//                    headerLayout.visibility = View.VISIBLE
                    //txtG1.text = "ALL BIKES"
                } else {
//                    headerLayout.visibility = View.GONE
                    //txtG1.text = "ALL BICYCLES"
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
//                Log.d("onPageScrollState", "$state")
            }
        })
        tabLayout.setupWithViewPager(viewPager)
        setupTabIcons()


        //Back Press to Home
        view.isFocusableInTouchMode = true
        view.requestFocus()

        view.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(
                v: View?,
                keyCode: Int,
                event: KeyEvent
            ): Boolean {

                if (event.action == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (viewPager.currentItem != 0) {
                            viewPager.setCurrentItem(0, false)
                        } else {
                            activity?.finish()
                        }
                        return true
                    }
                }
                return false
            }
        })

    }

    private fun setupTabIcons() {
//        tabLayout.getTabAt(0)?.setIcon(R.drawable.bike_one)
//        tabLayout.getTabAt(1)?.setIcon(R.drawable.m_bike)
    }




}