package com.example.cusnav.fragment

import android.app.AlertDialog
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
import androidx.navigation.fragment.findNavController
import com.example.cusnav.R
import com.example.cusnav.api.BikeApi
import com.example.cusnav.model.Brand
import com.example.cusnav.viewmodel.VoteViewModel
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.sign_upp

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {
    private lateinit var nameP:Brand
    private lateinit var viewModel: VoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_sign_up, container, false)
        root.member.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_signUpFragment_to_loginFragment)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences=this.requireActivity().getSharedPreferences(BikeApi.sharedPrefFile,Context.MODE_PRIVATE)
        val sharedPreferencesN=this.requireActivity().getSharedPreferences(BikeApi.sharedPrefFileN,Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this).get(VoteViewModel::class.java)
        sign_upp.setOnClickListener {
            if (checkBoxrr?.isChecked == true) {
                val name = username.text.toString()
                val email = emailsgnup.text.toString()
                val password = password_sgn_up.text.toString()
                val retypepass = confirm_pass_sgn_up.text.toString()
                if (password == retypepass) {
                    viewModel.loadSignUp(name, email, password)

                    // viewModel.loadError(name, email, password)
                    viewModel.getResponseMess()
                        .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                            it
                            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                            if (it == "Successfully User Added!") {
                                val editorOne: SharedPreferences.Editor =  sharedPreferencesN.edit()
                                val editorTwo: SharedPreferences.Editor =  sharedPreferences.edit()

                                editorTwo.putInt("pref_signUp",1)
                                editorOne.putString("pref_name",name)
                                editorOne.putString("pref_email",email)
                                editorOne.apply()
                                editorOne.commit()
                                editorTwo.apply()
                                editorTwo.commit()
                                view?.findNavController()
                                    ?.navigate(R.id.action_signUpFragment_to_ownerFragment)

//                                viewModel.getResponseMessName().observe(viewLifecycleOwner,androidx.lifecycle.Observer {
//                                    val action=SignUpFragmentDirections?.actionSignUpFragmentToOwnerFragment(it)
//                                    findNavController().navigate(action)
//                                })
//                                val argsName=nameP.name
//                                val action=SignUpFragmentDirections.actionSignUpFragmentToOwnerFragment(argsName)
////                                    findNavController().navigate(action)
                            }
                        })

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Comfirm password and password must be the same",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialog)
                builder.setTitle("Error")
                builder.setMessage("You must agree all statements in terms of service!!")
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }

        }
        termofservice.setOnClickListener {
            val items = arrayOf(getString(R.string.TNS))
            val builder = AlertDialog.Builder(requireContext())
            with(builder) {
                setTitle("Term of Services")
                setItems(items) { dialog, which ->

                }
            }
            builder.setNegativeButton(android.R.string.yes) { dialog, which ->
                checkBoxrr.isChecked = true
            }
            builder.setPositiveButton(android.R.string.no) { dialog, which ->
                checkBoxrr.isChecked = false
            }
            builder.show()

        }

    }


}