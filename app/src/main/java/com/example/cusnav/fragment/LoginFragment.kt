package com.example.cusnav.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.cusnav.R
import com.example.cusnav.api.BikeApi
import com.example.cusnav.api.BikeApi.Companion.sharedPrefFile
import com.example.cusnav.viewmodel.VoteViewModel
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_login.view.btnLogin


class LoginFragment : Fragment() {

    private lateinit var viewModel: VoteViewModel
    //private val bikeApi: BikeApi = BikeApi()
   // val sharedPrefFile = "TEST_SHARED_PREFERENCE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val navBar: ChipNavigationBar = requireActivity().findViewById(R.id.bottom_menu)
        val root=inflater.inflate(R.layout.fragment_login, container, false)
        root.btnCancel.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_startFragment)
            navBar.visibility=View.VISIBLE
        }
        root.id_sign.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences=this.requireActivity().getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this).get(VoteViewModel::class.java)
        btnLogin?.setOnClickListener {
            val email = et1.text.toString()
            val password = et2.text.toString()

            viewModel.login(email, password)
            viewModel.getResponseMess().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                it
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                if (it == "Login Successfully") {
                    val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                    editor.putInt("pref_login",1)
                    editor.putString("pref_login_email",email)
                    editor.apply()
                    editor.commit()
                    view?.findNavController()
                        ?.navigate(R.id.action_loginFragment_to_ownerFragment)
                }
            })

        }
    }


}