package com.example.cusnav.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

import com.example.cusnav.R
import com.example.cusnav.model.BikeX
import com.example.cusnav.model.bicycle.BicycleX
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_m.*
import kotlinx.android.synthetic.main.fragment_m.view.*
import java.util.*


class MFragment : Fragment() {

    private lateinit var bikex: BikeX

    //  private lateinit var bicycleX: BicycleX
    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_m, container, false)
//        root.detail_back.setOnClickListener {
//            view?.findNavController()?.navigate(R.id.action_MFragment_to_tabFragment)
//        }
        return root
    }

    fun agrs(bike: BikeX) {
        val bId = bike.id
        val bPrice = bike.price
        val action = MFragmentDirections.actionMFragmentToUserFragment(bId.toString(), bPrice)
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // val bie:BikeX
//        val bId=bikex.id
//        val bPrice=bikex.price
        Continue.setOnClickListener {
            agrs(bikex)
        }
        //Back Pressed to Return
//        val callback: OnBackPressedCallback =
//            object : OnBackPressedCallback(true /* enabled by default */) {
//                override fun handleOnBackPressed() {
//                    findNavController().popBackStack(R.id.tabFragment, false)
//                    // Handle the back button event
//                }
//            }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        detail_back.setOnClickListener {
            findNavController().popBackStack(R.id.tabFragment, false)
        }
//        retrieve_detail_toolbar.setNavigationOnClickListener {
//            findNavController().popBackStack(R.id.tabFragment, false)
//        }


//        var messageArgs = arguments?.let {
//            MFragmentArgs.fromBundle(
//                it
//            )
//        }
//        var title = messageArgs?.title
//        var img: String? = messageArgs?.img
//        Picasso.get()
//            .load("https://image.tmdb.org/t/p/w200${img}")
//            .into(img_vote)
//        voteName.text = title
        if (arguments != null) {
            val args = MFragmentArgs.fromBundle(requireArguments())
            //  val argsh = DetailFragmentArgs.fromBundle(requireArguments())
            bikex = args.bike
            // bicycleX=argsh.bicycle
        }


        Picasso.get()
            .load("http://bike-rental.khaingthinkyi.me/${bikex.image}")
            .into(detail_img)
        detail_description.text = bikex.description
        detail_brand.text = bikex.model
        detail_color.text = bikex.color
        detail_price.text = bikex.price

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()

            }
        calendar!!.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

    }


    private fun updateDateInView() {}


}