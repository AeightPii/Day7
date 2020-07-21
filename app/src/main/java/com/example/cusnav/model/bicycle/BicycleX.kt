package com.example.cusnav.model.bicycle

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BicycleX(
    //val brand: Brand,
    val color: String,
    val created_at: String,
    val description: String,
    val id: String,
    val image: String,
    val model: String,
    val number: String,
    val price: String,
    var status: String,
    val updated_at: String,
    val user: String
):Parcelable{


    fun getFavStatus(): String {

        return status
    }

    fun setFavStatus(favStatus: String?) {
        if (favStatus != null) {
            this.status = favStatus
        }
    }
}