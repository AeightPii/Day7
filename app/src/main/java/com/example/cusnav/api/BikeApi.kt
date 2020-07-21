package com.example.cusnav.api


import android.util.Log
import com.example.cusnav.model.Bike
import com.example.cusnav.model.SignUpErr
import com.example.cusnav.model.SignUpResponse
import com.example.cusnav.model.RentResponse
import com.example.cusnav.model.bicycle.Bicycle
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BikeApi {
    private val bikeApiInterface: BikeApiInterface

    companion object {
        const val BASE_URL = " http://bike-rental.khaingthinkyi.me/api/setup/"
        const val sharedPrefFile = "TEST_SHARED_PREFERENCE"
        const val sharedPrefFileN = "TEST_SHARED_PREFERENCE_N"
    }

    init {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        bikeApiInterface = retrofit.create(BikeApiInterface::class.java)


    }

    fun getBike(): Call<Bike> {
        return bikeApiInterface.getBike()
    }

    fun getBicycle(): Call<Bicycle> {
        return bikeApiInterface.getBicycle()
    }

    fun signUp(name: String, email: String, password: String): Call<SignUpResponse> {
        return bikeApiInterface.signUp(name, email, password)
    }

    fun signUpErr(name: String, email: String, password: String): Call<SignUpErr> {
        return bikeApiInterface.signUpErr(name, email, password)
    }

    fun login(email: String, password: String): Call<SignUpResponse> {
        return bikeApiInterface.login(email, password)
    }

        fun rent(name: String,phone: String,address:String,bikeId:Int,startDate:String,endDate:String,totalDay:Int,totalPrice:Int): Call<RentResponse> {
        Log.d("Data",name)
        return bikeApiInterface.rent(name,phone,address, bikeId, startDate, endDate, totalDay, totalPrice)
    }
//    fun rent(name: String): Call<RentResponse> {
//        Log.d("Data", name)
//        return bikeApiInterface.rent(name)
//    }
}