package com.example.cusnav.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cusnav.R
import com.example.cusnav.adapter.BicycleAdapter
import com.example.cusnav.adapter.BikeAdapter
import com.example.cusnav.model.BikeX
import com.example.cusnav.model.bicycle.BicycleX
import com.example.cusnav.viewmodel.BikeViewModel
import kotlinx.android.synthetic.main.fragment_bicycle.*
import kotlinx.android.synthetic.main.fragment_home.*


class BicycleFragment : Fragment(), BikeAdapter.ClickListener{
    private lateinit var bikeViewModel: BikeViewModel
    private lateinit var bikeAdapter: BikeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_bicycle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bikeViewModel = ViewModelProvider(this).get(BikeViewModel::class.java)


    }

    private fun observeViewModel() {

        bikeViewModel.loadBike()
        bikeViewModel.getResult().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val bikeAdapter = BikeAdapter(it, requireContext())
            bikeAdapter.updateList(it)
            bikeAdapter.setClickListener(this)
            recyclerViewBike.adapter = bikeAdapter
            recyclerViewBike.layoutManager = LinearLayoutManager(context)

        })
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()

    }

    override fun onClick(id: BikeX) {
//        Toast.makeText(context, re.description, Toast.LENGTH_LONG).show()
//        var mName = re.brand.name
//        var mImg = re.image
        val action = TabFragmentDirections.actionTabFragmentToMFragment(id)
        findNavController().navigate(action)
    }
    

}