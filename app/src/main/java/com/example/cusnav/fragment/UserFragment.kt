package com.example.cusnav.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.cusnav.R
import com.example.cusnav.model.BikeX
import com.example.cusnav.model.bicycle.BicycleX
import com.example.cusnav.viewmodel.VoteViewModel
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user.view.*
import kotlinx.android.synthetic.main.item_dialog.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*

class UserFragment : Fragment() {
    private lateinit var viewModel: VoteViewModel
    //private lateinit var bike: BikeX
    private lateinit var bicycle:BicycleX
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_user, container, false)
        root.menu_login.setOnClickListener {
            //view?.findNavController()?.navigate(R.id.action_userFragment_to_loginFragment2)
        }
        return root
    }

    var cal = Calendar.getInstance()
    var cale = Calendar.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        start_date!!.text = sdf.format(cal.time)
        end_date!!.text = sdf.format(cale.time)


        val startYear = cal.get(Calendar.YEAR)
        val startMonth = cal.get(Calendar.MONTH)
        val startDay = cal.get(Calendar.DAY_OF_MONTH)

        val endYear = cale.get(Calendar.YEAR)
        val endMonth = cale.get(Calendar.MONTH)
        val endDay = cale.get(Calendar.DAY_OF_MONTH)

        val start = LocalDate.of(startYear, startMonth, startDay)
        val end = LocalDate.of(endYear, endMonth, endDay)

        val calculate = ChronoUnit.DAYS.between(start, end)
        total_day!!.text = calculate.toString()

        val result =
            Integer.parseInt(total_day!!.text.toString()) * Integer.parseInt(user_price?.text.toString())
        totalPrice!!.text = (result!!.toString())


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(VoteViewModel::class.java)
        val argsMessage=arguments?.let { UserFragmentArgs.fromBundle(it) }
        user_price.text=argsMessage?.itemPrice
        val bId= argsMessage?.itemId
//        if (arguments != null) {
//            val args = UserFragmentArgs.fromBundle(requireArguments())
//            bike = args.
//            user_price.text = bike.price
//            //idUser.text= bike.id.toString()
//        }
        user_confirm_btn.setOnClickListener {
            val userName = user_name.text.toString()
            val userPhone = user_ph.text.toString()
            val userAddress = user_address.text.toString()
            val startDate = start_date.text.toString()
            val endDate = end_date.text.toString()
            val totalDay = total_day.text.toString()
            val totalPrice = totalPrice.text.toString()
            //val bikeId = bike.id.toString()
            if (bId != null) {
                viewModel.loadRent(
                    userName
                    ,userPhone,
                    userAddress,
                    bId,
                    startDate,
                    endDate,
                    totalDay,
                    totalPrice
                )
            }

            viewModel?.getResponseMessRent()
                ?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                    if(it == "Successfully Rent Added!"){
                        val thanksDialog = LayoutInflater.from(context).inflate(R.layout.item_dialog, null)
                        val thanksBuilder = context.let {
                            AlertDialog.Builder(it)
                                .setView(thanksDialog)
                        }
                        val alertDialog = thanksBuilder?.show()
                        thanksDialog.dialog_button?.setOnClickListener {
                            view?.findNavController()!!.navigate(R.id.action_userFragment_to_tabFragment)
                            alertDialog?.dismiss()
                        }
                    }else{
                        Toast.makeText(requireContext(),"Something went wrong",Toast.LENGTH_LONG).show()
                    }
                }
                )
        }


        // create an OnDateSetListener
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        val dateSetListener1 =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cale.set(Calendar.YEAR, year)
                cale.set(Calendar.MONTH, monthOfYear)
                cale.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()

            }
        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        s_submit!!.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH - 1)
            ).show()
        }
        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        e_submit!!.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                dateSetListener1,
                // set DatePickerDialog to point to today's date when it loads up
                cale.get(Calendar.YEAR),
                cale.get(Calendar.MONTH),
                cale.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

}