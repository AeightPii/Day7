package com.example.cusnav.adapter

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


import com.example.cusnav.R
import com.example.cusnav.database.FavBikeDB
import com.example.cusnav.model.BikeX
import com.example.cusnav.model.bicycle.BicycleX

import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_bike.view.*


class BicycleAdapter(
    bikeItems: List<BicycleX>,
    context: Context
) : RecyclerView.Adapter<BicycleAdapter.ViewHolder>() {

    private var bikeItems: List<BicycleX> = listOf()
    private val context: Context
    private var favBikeDB: FavBikeDB? = null
    //private lateinit var re: BikeX
    var mClickListener: ClickListener? = null   //....................global variable
    fun setClickListener(clickListener: ClickListener) {
        this.mClickListener = clickListener
    }
    companion object{
        var key="key"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        favBikeDB = FavBikeDB(context)
        val prefs =
            context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val firstStart = prefs.getBoolean("firstStart", true)
        if (firstStart) {
            createTableOnFirstStart()
        }
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_bike, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bikeItem: BicycleX= bikeItems[position]
        readCursorData(bikeItem, holder)
        holder.title.text = bikeItem.model
       // holder.description.text = bikeItem.description
        Picasso.get()
            .load("http://bike-rental.khaingthinkyi.me/${bikeItem.image}")
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.imageView)
        holder.bindBike(bikeItems[position])
       // this.re = bikeItem
//        holder.imageView.setOnClickListener {
//            var intent=Intent(context,DetailFragment::class.java)
//            intent.putExtra("key",bikeItem)
//        }
        // holder.bindVote(votingItems!![position])
    }

    override fun getItemCount(): Int {
        return bikeItems.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var imageView: ImageView = itemView.findViewById(R.id.imageViewBike)
        var title: TextView = itemView.findViewById(R.id.titleTextViewBike)
      //  var description: TextView = itemView.findViewById(R.id.descriptionTextView)
        var favBtn: Button = itemView.findViewById(R.id.favBtnBike)
        //var likeCountTextView: TextView = itemView.findViewById(R.id.likeCountTextView)

        private lateinit var bike: BicycleX
        init {
            itemView.setOnClickListener(this)

            favBtn.setOnClickListener {
                val position = adapterPosition
                val bikeItem: BicycleX = bikeItems!![position]
                likeClick(bikeItem, favBtn)
            }
        }
        fun bindBike(bike:BicycleX){
            this.bike=bike
            itemView.txtPrice.text=bike.price
        }

        override fun onClick(v: View?) {
            mClickListener?.onClick(bike)
        }

    }
    interface ClickListener {
        fun onClick(id: BicycleX)//no implementation no body
    }

    private fun likeClick(bikeItem: BicycleX, favBtn: Button) {
        val refLike =
            FirebaseDatabase.getInstance().reference.child("likes")
        val upvotesRefLike = bikeItem.id.let { refLike.child(it.toString()) }
        if (bikeItem.getFavStatus() == "0") {
            bikeItem.setFavStatus("1")
            val c = "http://bike-rental.khaingthinkyi.me/${bikeItem.image}"
            bikeItem.model.let {
                favBikeDB?.insertIntoTheDatabase(
                    it, c,
                    bikeItem.id.toString(),

                    //bikeItem.number,
//                    bikeItem.color,
//                    bikeItem.brand.toString(),
//                    bikeItem.price,
//                    bikeItem.description,
                    bikeItem.getFavStatus()
                )
            }
            favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
            favBtn.isSelected = true
            upvotesRefLike.runTransaction(object :
                Transaction.Handler {
                override fun doTransaction(mutableData: MutableData): Transaction.Result {
                    try {
                        val currentValue = mutableData.getValue(Int::class.java)
                        if (currentValue == null) {
                            mutableData.value = 1
                        } else {
                            mutableData.value = currentValue + 1
                            Handler(Looper.getMainLooper())
                            //.post { textlike.text = mutableData.value.toString() }

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
        } else if (bikeItem.getFavStatus() == "1") {
            bikeItem.setFavStatus("0")
            bikeItem.id?.let { favBikeDB?.remove_fav(it.toString()) }
            favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp)
            favBtn.isSelected = false
            upvotesRefLike.runTransaction(object :
                Transaction.Handler {
                override fun doTransaction(mutableData: MutableData): Transaction.Result {
                    try {
                        val currentValue = mutableData.getValue(Int::class.java)
                        if (currentValue == null) {
                            mutableData.value = 1
                        } else {
                            mutableData.value = currentValue - 1
                            Handler(Looper.getMainLooper())
                            // .post { textlike.text = mutableData.value.toString() }

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


    private fun createTableOnFirstStart() {
        favBikeDB?.insertEmpty()
        val prefs =
            context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("firstStart", false)
        editor.apply()
    }

    private fun readCursorData(
        bikeItem: BicycleX, viewHolder: ViewHolder
    ) {
        val cursor: Cursor? = bikeItem.id.let { favBikeDB!!.read_all_data(it.toString()) }
        val db: SQLiteDatabase? = favBikeDB!!.getReadableDatabase()
        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val item_fav_status =
                        cursor.getString(cursor.getColumnIndex(FavBikeDB.FAVORITE_STATUS))
                    bikeItem.setFavStatus(item_fav_status)

                    //check fav status
                    if (item_fav_status != null && item_fav_status == "1") {
                        viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
                    } else if (item_fav_status != null && item_fav_status == "0") {
                        viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp)
                    }
                }
            }
        } finally {
            if (cursor != null && cursor.isClosed) cursor.close()
            db?.close()
        }
    }

    init {
        this.bikeItems = bikeItems
        this.context = context
    }

    fun updateList(result: List<BicycleX>) {
        this.bikeItems = result
        notifyDataSetChanged()
    }


}