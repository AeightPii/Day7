package com.example.cusnav.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cusnav.api.BikeApi
import com.example.cusnav.model.SignUpErr
import com.example.cusnav.model.SignUpResponse
import com.example.cusnav.model.RentResponse
import com.example.myfavapi.api.MoviesApi
import com.example.retrofitmovies.model.Movies
import com.example.retrofitmovies.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VoteViewModel : ViewModel() {
    var responseMess: MutableLiveData<String> = MutableLiveData()
    var responseMessName: MutableLiveData<String> = MutableLiveData()
    //var responseMessErr: MutableLiveData<String> = MutableLiveData()
    var results: MutableLiveData<List<Result>> = MutableLiveData()
    var responseMessRent: MutableLiveData<String> = MutableLiveData()
    private val moviesApi: MoviesApi = MoviesApi()
    private val bikeApi: BikeApi = BikeApi()

    //getter..........

    fun getResponseMess(): LiveData<String> = responseMess
    fun getResponseMessName(): LiveData<String> = responseMessName

    fun getResponseMessRent(): LiveData<String> = responseMessRent

    // fun getResponseMessErr():LiveData<String> = responseMessErr
    fun getResult(): MutableLiveData<List<Result>> = results

    // setter..........
    fun loadMovie() {
        val apiCall = moviesApi.getMovies("59e90160f05dc382b043b086e34c75c5")

        apiCall.enqueue(object : Callback<Movies> {
            override fun onFailure(call: Call<Movies>, t: Throwable) {

            }

            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                val a = response.body()
                Log.d("response>>>", a.toString())
                response.isSuccessful.let {
                    results.value = response.body()?.results ?: emptyList()
                }
            }
        })
    }

    fun loadSignUp(name: String, email: String, password: String) {
        val ApiCallSignUp = bikeApi.signUp(name, email, password)

        ApiCallSignUp.enqueue(object : Callback<SignUpResponse> {
            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                val a = response.body()
                Log.d("responseSignUp>>>", a.toString())
                response.isSuccessful.let {
                    responseMess.value = response.body()?.message.toString()
                  //  responseMessName.value= response.body()?.brand?.name
                }
            }

        })
    }

    //.......................................................................................
    fun loadError(name: String, email: String, password: String) {
        val ApicallErr = bikeApi.signUpErr(name, email, password)
        ApicallErr.enqueue(object : Callback<SignUpErr> {
            override fun onFailure(call: Call<SignUpErr>, t: Throwable) {

            }
            override fun onResponse(call: Call<SignUpErr>, response: Response<SignUpErr>) {
                val a = response.body()
                Log.d("responseSignUp>>>", a.toString())
                response.isSuccessful.let {
                    // responseMessErr.value = response.body()?.message.toString()
                    //  responseMessErr.value = response.body()?.errors.toString()
                }
            }
        })
    }

//,phone:String,address:String,bikeId:String,start:String,end:String,tday:String,tprice:String
    fun loadRent(name:String,phone:String,address:String,bikeId:String,start:String,end:String,tday:String,tprice:String){
        val apiCall=bikeApi?.rent(name,phone,address,bikeId.toInt(),start,end,tday.toInt(),tprice.toInt())
        apiCall.enqueue(object : Callback<RentResponse> {
            override fun onFailure(call: Call<RentResponse>, t: Throwable) {
                Log.d("error",t.toString())
                    }

            override fun onResponse(call: Call<RentResponse>, response: Response<RentResponse>) {
            val a=response.body()
                Log.d("responseRent",a.toString())
                response?.isSuccessful.let {
                    responseMessRent?.value= response.body()?.message.toString()
                }
            }

        })
    }
    //.................................................................................................
    fun login(email: String, password: String) {
        val apiCallLogin = bikeApi.login(email, password)
            apiCallLogin.enqueue(object : Callback<SignUpResponse> {
            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {

            }
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                val a = response.body()
                Log.d("responseSignUp>>>", a.toString())
                response.isSuccessful.let {
                    responseMess.value = response.body()?.message.toString()
                }
            }

        })
    }
    //..............................................................
//    fun rent(
//        name: String,
//        phone: String,
//        address: String,
//        bikeId: String,
//        startDate: String,
//        endDate: String,
//        totalDay: String,
//        totalPrice: String
//    ) {
//        val apiRentCall = bikeApi.rent(name,phone,address,bikeId,startDate,endDate,totalDay,totalPrice)
//        apiRentCall.enqueue(object : Callback<RentResponse> {
//            override fun onFailure(call: Call<RentResponse>, t: Throwable) {
//                Log.d("err",t.toString())
//            }
//
//            override fun onResponse(call: Call<RentResponse>, response: Response<RentResponse>) {
//                val a = response.body()
//                Log.d("responseRent>>>", a.toString())
//                response.isSuccessful.let {
//                    responseMessRent.value = response.body()?.message.toString()
//                }
//            }
//
//        })
//    }
}


