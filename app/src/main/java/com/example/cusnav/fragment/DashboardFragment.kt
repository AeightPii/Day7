package com.example.cusnav.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.cusnav.R
import com.example.cusnav.adapter.FavAdapter
import com.example.cusnav.database.FavBikeDB

import com.example.myfavapi.model.FavItem
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import java.util.ArrayList

class DashboardFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var favBikeDB: FavBikeDB? = null
    private val favItemList: MutableList<FavItem>? =
        ArrayList()
    private var favAdapter: FavAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root =
            inflater.inflate(R.layout.fragment_dashboard, container, false)
        favBikeDB = FavBikeDB(activity)
        recyclerView = root.findViewById(R.id.recyclerView)
        root.recyclerView.setHasFixedSize(true)
        root.recyclerView.layoutManager = LinearLayoutManager(activity)

        // add item touch helper
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView) // set swipe to recyclerview
        loadData()
        return root
    }

    private fun loadData() {
        favItemList?.clear()
        val db = favBikeDB!!.readableDatabase
        val cursor = favBikeDB!!.select_all_favorite_list()
        try {
            while (cursor.moveToNext()) {

                val title = cursor.getString(cursor.getColumnIndex(FavBikeDB.ITEM_TITLE))
                val id = cursor.getString(cursor.getColumnIndex(FavBikeDB.KEY_ID))
                val image = cursor.getString(cursor.getColumnIndex(FavBikeDB.ITEM_IMAGE))
                // val description=cursor.getString(cursor.getColumnIndex(FavDB.ITEM_DESCRIPTION))
                val favItem = FavItem(title, id,image)
                favItemList!!.add(favItem)
            }
        } finally {
            if (cursor.isClosed) cursor.close()
            db.close()
        }
        favAdapter = context?.let { FavAdapter(it, favItemList!!) }
        recyclerView!!.adapter = favAdapter
    }

    // remove item after swipe
    private val simpleCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val position = viewHolder.adapterPosition // get position which is swipe
                val favItem = favItemList!![position]
                if (direction == ItemTouchHelper.LEFT) { //if swipe left
                    favAdapter!!.notifyItemRemoved(position) // item removed from recyclerview
                    favItemList.removeAt(position) //then remove item
                    favBikeDB!!.remove_fav(favItem.key_id!!) // remove item from database
                }
            }
        }
}
