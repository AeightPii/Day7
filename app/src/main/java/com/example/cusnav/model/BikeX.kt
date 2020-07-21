package com.example.cusnav.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BikeX(
    //val brand: BrandX,
    val color: String,
    val created_at: String,
    val description: String,
    val id: Int,
    val image: String,
    val model: String,
    val number: String,
    val price: String,
    var status: String,
    val updated_at: String
     //, val user: User
) : Parcelable {


    fun getFavStatus(): String {

            return status
        }

        fun setFavStatus(favStatus: String?) {
            if (favStatus != null) {
                this.status = favStatus
            }
    }
}