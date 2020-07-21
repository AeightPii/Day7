package com.example.cusnav.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cusnav.R
import com.example.cusnav.model.BikeX
import com.example.cusnav.model.bicycle.BicycleX
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_m.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    private lateinit var bicycleX: BicycleX

    //    private lateinit var bikeX: BikeX
    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    fun args(bicycle: BicycleX) {
        val bcyId = bicycle.id
        val bcyPrice = bicycle.price
        val action = DetailFragmentDirections.actionDetailFragmentToUserFragment(bcyId, bcyPrice)
        findNavController()?.navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Continue.setOnClickListener {
            args(bicycleX)
        }
        detail_back.setOnClickListener {
            findNavController().popBackStack(R.id.tabFragment, false)
        }
        if (arguments != null) {
            val args = DetailFragmentArgs.fromBundle(requireArguments())
            bicycleX = args.bicycle
        }


        Picasso.get()
            .load("http://bike-rental.khaingthinkyi.me/${bicycleX.image}")
            .into(detail_img)
        detail_description.text = bicycleX.description
        detail_brand.text = bicycleX.model
        detail_color.text = bicycleX.color
        detail_price.text = bicycleX.price

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
