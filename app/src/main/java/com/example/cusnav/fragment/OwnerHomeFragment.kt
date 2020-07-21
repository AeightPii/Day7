package com.example.cusnav.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.cusnav.R
import com.example.cusnav.api.BikeApi
import kotlinx.android.synthetic.main.fragment_owner_home.*

class OwnerHomeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_owner_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        var messageArgs = arguments?.let {OwnerFragmentArgs.fromBundle(it) }
//        val ggname:String?=messageArgs?.ProfileName
//        title_name.text=ggname
        val sharedPreferences=this.requireActivity().getSharedPreferences(
            BikeApi.sharedPrefFile,
            Context.MODE_PRIVATE)
        val sharedPreferencesN=this.requireActivity().getSharedPreferences(
            BikeApi.sharedPrefFileN,
            Context.MODE_PRIVATE)
        val titleName=sharedPreferencesN.getString("pref_name","Default")
        val ownerEmailS=sharedPreferences.getString("pref_email","d@gmail.com")
        val ownerEmailL=sharedPreferences.getString("pref_login_email","email")
        title_name.text=titleName.toString()
        menu_item_logout.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            Toast.makeText(requireContext(),"Logout successfully",Toast.LENGTH_LONG).show()
            view.findNavController().navigate(R.id.action_ownerFragment_to_startFragment)
        }
        menu_item_home.setOnClickListener {
            view.findNavController().navigate(R.id.action_ownerFragment_to_startFragment)
        }
    }
}