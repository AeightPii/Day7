package com.example.cusnav.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.cusnav.R
import com.example.cusnav.api.BikeApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_start.view.*


class StartFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var navBar: ChipNavigationBar = requireActivity().findViewById(R.id.bottom_menu)
        navBar.visibility = View.VISIBLE
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_start, container, false)
        val sharedPreferences = this.requireActivity().getSharedPreferences(
            BikeApi.sharedPrefFile,
            Context.MODE_PRIVATE
        )
        val loginValue = sharedPreferences.getInt("pref_login", 0)
        val signUpValue = sharedPreferences.getInt("pref_signUp", 0)
        if (loginValue == 1 || signUpValue == 1) {
            root?.btn_go_to_login?.text = "Go to your account"
        }
        root.btn_go_to_login.setOnClickListener {
            if (loginValue == 0 && signUpValue == 0) {
                view?.findNavController()!!.navigate(R.id.action_startFragment_to_loginFragment)
            } else {
                view?.findNavController()!!.navigate(R.id.action_startFragment_to_ownerFragment)

            }
            navBar.visibility = View.GONE
        }
        return root
    }


}