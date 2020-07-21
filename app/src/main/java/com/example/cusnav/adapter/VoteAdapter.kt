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
import com.example.cusnav.database.FavDB
import com.example.cusnav.R
import com.example.retrofitmovies.model.Movies
import com.example.retrofitmovies.model.Result
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList


class VoteAdapter(
    votingItems: List<Result>,
    context: Context
) : RecyclerView.Adapter<VoteAdapter.ViewHolder>(){

    private var votingItems: List<Result> = listOf()
    private val context: Context
    private var favDB: FavDB? = null
    private lateinit var re:Result
     var mClickListener: ClickListener? = null   //....................global variable
    fun setClickListener(clickListener: ClickListener) {
        this.mClickListener = clickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        favDB= FavDB(context)
        val prefs =
            context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val firstStart = prefs.getBoolean("firstStart", true)
        if (firstStart) {
            createTableOnFirstStart()
        }
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val voteItem:Result = votingItems[position]
        readCursorData(voteItem,holder)
        holder.title.text = voteItem.title
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w200${voteItem.poster_path}")
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.imageView)
        this.re=voteItem
        // holder.bindVote(votingItems!![position])
    }

    override fun getItemCount(): Int {
        return votingItems.size
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
        var title: TextView = itemView.findViewById(R.id.titleTextView)
        var favBtn: Button = itemView.findViewById(R.id.favBtn)
        //var likeCountTextView: TextView = itemView.findViewById(R.id.likeCountTextView)


        init {
           itemView.setOnClickListener(this)

            favBtn.setOnClickListener {
                val position=adapterPosition
                val votingItem:Result= votingItems!![position]
                likeClick(votingItem,favBtn)
            }
        }
        override fun onClick(v: View?) {
            mClickListener?.onClick(re)
        }

    }
    private fun likeClick(votingItem:Result,favBtn: Button) {
        val refLike =
            FirebaseDatabase.getInstance().reference.child("likes")
        val upvotesRefLike = votingItem.id.let { refLike.child(it.toString()) }
        if (votingItem.getFavStatus() == "false") {
            votingItem.setFavStatus("true")
            val c="https://image.tmdb.org/t/p/w200${votingItem.poster_path}"
            votingItem.title.let {
                favDB?.insertIntoTheDatabase(
                    it, c,
                    votingItem.id.toString(), votingItem.getFavStatus()
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
        } else if (votingItem.getFavStatus().equals("true")) {
            votingItem.setFavStatus("false")
            votingItem.id.let { favDB?.remove_fav(it.toString()) }
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
        favDB?.insertEmpty()
        val prefs =
            context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("firstStart", false)
        editor.apply()
    }
    private fun readCursorData(coffeeItem: Result, viewHolder: ViewHolder
    ) {
        val cursor: Cursor? = coffeeItem.id.let { favDB!!.read_all_data(it.toString()) }
        val db: SQLiteDatabase? = favDB!!.getReadableDatabase()
        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val item_fav_status =
                        cursor.getString(cursor.getColumnIndex(FavDB.FAVORITE_STATUS))
                    coffeeItem.setFavStatus(item_fav_status)

                    //check fav status
                    if (item_fav_status != null && item_fav_status == "true") {
                        viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
                    } else if (item_fav_status != null && item_fav_status == "false") {
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
        this.votingItems= votingItems
        this.context=context
    }
    fun updateList(result: List<Result>) {
        this.votingItems = result as MutableList<Result>
        notifyDataSetChanged()
    }
    interface ClickListener {
        fun onClick(re: Result)//no implementation no body
    }
}