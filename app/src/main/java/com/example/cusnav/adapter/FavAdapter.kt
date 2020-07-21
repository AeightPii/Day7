package com.example.cusnav.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.cusnav.R
import com.example.cusnav.database.FavBikeDB
import com.example.myfavapi.model.FavItem
import com.google.firebase.database.*
import com.squareup.picasso.Picasso


class FavAdapter(
    private val context: Context,
    private val favItemList: MutableList<FavItem>? =
        ArrayList()
) : RecyclerView.Adapter<FavAdapter.ViewHolder>() {
    private var favBikeDB: FavBikeDB? = null
    private var refLike: DatabaseReference? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_item, parent, false)
        favBikeDB = FavBikeDB(context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int
    ) {
        holder.favTextView.text = favItemList?.get(position)?.item_title

        Picasso.get()
            .load(favItemList?.get(position)?.item_image)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.favImageView)
      //  holder.favImageView.setImageResource(favItemList?.get(position)!!.item_image)
    }

    override fun getItemCount(): Int {
        return favItemList?.size!!
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var favTextView: TextView = itemView.findViewById(R.id.favTextView)
//        var favDescriotion:TextView=itemView.findViewById(R.id.descriptionTextView)
        var favBtn: Button = itemView.findViewById(R.id.favBtn)
        var favImageView: ImageView = itemView.findViewById(R.id.favImageView)

        init {
            refLike = FirebaseDatabase.getInstance().reference.child("likes")
            //remove from fav after click
            favBtn.setOnClickListener {
                val position = adapterPosition
                val favItem = favItemList?.get(position)
                val upvotesRefLike =
                    favItemList?.get(position)?.key_id?.let { it1 -> refLike!!.child(it1) }
                favItem?.key_id?.let { it1 -> favBikeDB!!.remove_fav(it1) }
                removeItem(position)
                upvotesRefLike?.runTransaction(object :
                    Transaction.Handler {
                    override fun doTransaction(mutableData: MutableData): Transaction.Result {
                        try {
                            val currentValue = mutableData.getValue(Int::class.java)
                            if (currentValue == null) {
                                mutableData.value = 1
                            } else {
                                mutableData.value = currentValue - 1
                            }
                        } catch (e: Exception) {
                            throw e
                        }
                        return Transaction.success(mutableData)
                    }

                    override fun onComplete(
                        databaseError: DatabaseError?,
                        b: Boolean,
                        dataSnapshot: DataSnapshot?
                    ) {
                        println("Transaction completed")
                    }
                })
            }
        }
    }

    private fun removeItem(position: Int) {
        favItemList?.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, favItemList?.size!!)
    }

}
