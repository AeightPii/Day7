package com.example.cusnav.activity

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import com.example.cusnav.R
import com.example.cusnav.fragment.DashboardFragment

import com.example.cusnav.fragment.HostForHomeFrag
import com.example.cusnav.fragment.MainFragment
import com.example.cusnav.fragment.aboutUsFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.ismaeldivita.chipnavigation.sample.util.colorAnimation

class MainActivity : AppCompatActivity() {

    private val container by lazy { findViewById<android.view.View>(R.id.container) }
    private val title by lazy { findViewById<TextView>(R.id.title) }
    private val menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_menu) }

    private var lastColor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //menu.visibility=View.VISIBLE
        //cv.visibility=View.VISIBLE
        lastColor = (container.background as ColorDrawable).color

        menu.setOnItemSelectedListener { id ->
           // cv.visibility=View.VISIBLE
            val option = when (id) {
                R.id.home -> {

                    val sFragment = HostForHomeFrag()
                    val fragmentOne = supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frame_layout, sFragment)

                    }
                   // cv.visibility=View.VISIBLE

                    fragmentOne.commit()
                    R.color.home to "Home"
                }
                R.id.activity -> {
                    val hFragment = MainFragment()
                    val fragment = supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frame_layout, hFragment)
                    }
                   // cv.visibility=View.VISIBLE
                    fragment.commit()
                   R.color.activity to ""

                }
                R.id.favorites -> {
                    val favFragment = DashboardFragment()
                    val secFragment = supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frame_layout, favFragment)
                    }
                    //cv.visibility=View.VISIBLE
                    secFragment.commit()
                    R.color.favorites to "Favorites"
                }
                R.id.settings -> {
                    val abtFragment = aboutUsFragment()
                    val trdFragment = supportFragmentManager.beginTransaction().apply {
                        replace(R.id.frame_layout, abtFragment)
                    }
                    trdFragment.commit()

                    R.color.settings to "Settings"
                }
                else -> R.color.home to "Home"

            }
            //cv.visibility=View.GONE


            val color = ContextCompat.getColor(this, option.first as Int)
            container.colorAnimation(lastColor, color)
            lastColor = color

            title.text = option.second


        }
        if (!isFinishing) {
            menu.setItemSelected(R.id.home)
           // cv.visibility=View.VISIBLE
        }


        if (savedInstanceState == null) {
            menu.showBadge(R.id.home)
            // menu.showBadge(R.id.settings, 32)
        }
    }

    var doubleBackToExitOnce: Boolean = false
    override fun onBackPressed() {
        if (doubleBackToExitOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitOnce = true
        Toast.makeText(this, "Please press again to exit", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(
            {
                kotlin.run { doubleBackToExitOnce = false }
            }, 2000
        )
    }
}
