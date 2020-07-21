package com.example.cusnav.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.cusnav.api.BikeApi
import com.example.cusnav.model.Bike
import com.example.cusnav.model.BikeX

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BikeViewModel : ViewModel() {

    var results: MutableLiveData<List<BikeX>> = MutableLiveData()
   // var bicycleresults: MutableLiveData<List<BikeX>> = MutableLiveData()
    private val bikeApi: BikeApi = BikeApi()

    //getter..........
    fun getResult(): MutableLiveData<List<BikeX>> = results
    //fun getBicycle():MutableLiveData<List<BikeX>> = bicycleresults

   // setter..........
    fun loadBike() {
       val apiCall = bikeApi.getBike()

      apiCall.enqueue(object : Callback<Bike> {
          override fun onFailure(call: Call<Bike>, t: Throwable) {

          }

          override fun onResponse(call: Call<Bike>, response: Response<Bike>) {
              val a = response.body()
              Log.d("response>>>", a.toString())
              response.isSuccessful.let {
                  results.value = response.body()?.bikes ?: emptyList()
              }
          }
      })
    }

//    fun loadBicycle() {
//        val apiCall = bikeApi.getBicycle()
//
//        apiCall.enqueue(object : Callback<Bike> {
//            override fun onFailure(call: Call<Bike>, t: Throwable) {
//
//            }
//
//            override fun onResponse(call: Call<Bike>, response: Response<Bike>) {
//                val a = response.body()
//                Log.d("responsebi>>>", a.toString())
//                response.isSuccessful.let {
//                    bicycleresults.value = response.body()?.bikes ?: emptyList()
//                }
//            }
//        })
//    }
}