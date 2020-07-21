package com.example.cusnav.api


import com.example.cusnav.model.Bike
import com.example.cusnav.model.SignUpErr
import com.example.cusnav.model.SignUpResponse
import com.example.cusnav.model.RentResponse
import com.example.cusnav.model.bicycle.Bicycle
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BikeApiInterface {
    @GET("bike")
    fun getBike(): Call<Bike>
    @GET("bicycle")
    fun getBicycle(): Call<Bicycle>

//    @POST("rent")
//    fun rentUser(
//        @Query("name") name: String,
//        @Query("phone_no") phone:String,
//        @Query("address")
//    )

    @POST("user")
    fun signUp(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<SignUpResponse>

    @POST("user")
    fun signUpErr(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<SignUpErr>
    @POST("check_auth")
    fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<SignUpResponse>

   @POST("rent")
    fun rent(
        @Query("name") name: String,
        @Query("phone_no") phone: String,
        @Query("address") address:String,
        @Query("bike_id") bikeId:Int,
        @Query("start_date") startDate:String,
        @Query("end_date") endDate:String,
        @Query("total_day") totalDay:Int,
        @Query("total_price") totalPrice:Int
        ): Call<RentResponse>

//    @POST("rent")
//    fun rent(
//        @Query("name") name: String
//    ): Call<RentResponse>

}