package com.example.cusnav.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cusnav.R
import com.example.cusnav.adapter.BicycleAdapter
import com.example.cusnav.adapter.BikeAdapter
import com.example.cusnav.adapter.VoteAdapter
import com.example.cusnav.model.BikeX
import com.example.cusnav.model.bicycle.BicycleX
import com.example.cusnav.viewmodel.BicycleViewModel
import com.example.cusnav.viewmodel.BikeViewModel
import com.example.cusnav.viewmodel.VoteViewModel
import com.example.myfavapi.api.MoviesApi
import com.example.retrofitmovies.model.Movies
import com.example.retrofitmovies.model.Result
import kotlinx.android.synthetic.main.fragment_bicycle.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(), BicycleAdapter.ClickListener {


   // private lateinit var voteViewModel: VoteViewModel
    private lateinit var bicycleViewModel: BicycleViewModel
   // private lateinit var voteAdapter: VoteAdapter

    private val moviesApi: MoviesApi = MoviesApi()//    var results: MutableLiveData<List<VotingItem>> = MutableLiveData()
//    fun getResult(): MutableLiveData<List<VotingItem>> = results

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //voteViewModel = ViewModelProvider(this).get(VoteViewModel::class.java)
        bicycleViewModel = ViewModelProvider(this).get(BicycleViewModel::class.java)


    }

    private fun observeViewModel() {

        bicycleViewModel.loadBicycle()
        bicycleViewModel.getBicycle().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val bicycleAdapter = BicycleAdapter(it, requireContext())
            bicycleAdapter.updateList(it)
            bicycleAdapter.setClickListener(this)
            recyclerViewBicycle.adapter = bicycleAdapter
            recyclerViewBicycle.layoutManager = LinearLayoutManager(context)

        })
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()

    }



    override fun onClick(id: BicycleX) {
        val action =TabFragmentDirections.actionTabFragmentToDetailFragment(id)

        findNavController().navigate(action)
    }

}