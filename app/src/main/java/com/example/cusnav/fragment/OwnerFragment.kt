package com.example.cusnav.fragment

import android.os.Bundle
import android.text.TextUtils.replace
import android.transition.ChangeBounds
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.cusnav.R
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.ismaeldivita.chipnavigation.sample.util.applyWindowInsets
import com.ismaeldivita.chipnavigation.sample.util.colorAnimation

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OwnerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OwnerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_owner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //private val container by lazy { findViewById<ViewGroup>(R.id.container) }
        //private val title by lazy { findViewById<TextView>(R.id.title) }
       // private val button by lazy { findViewById<ImageView>(R.id.expand_button) }
        //private val menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_menu) }
        val menuV: ChipNavigationBar = requireActivity().findViewById(R.id.bottom_menu_vertical)
        val button: ImageView = requireActivity().findViewById(R.id.expand_button)
        val container: ViewGroup= requireActivity().findViewById(R.id.containerV)
        val title:TextView=requireActivity().findViewById(R.id.titleV)
        var lastColor: Int = 0
        menuV.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(id: Int) {
                val option = when (id) {

                    R.id.homeV ->{
                        val sFragment = OwnerHomeFragment()
                        val fragmentOwner = getFragmentManager()?.beginTransaction()?.apply {
                            replace(R.id.frame_layoutV, sFragment)

                        }
                        // cv.visibility=View.VISIBLE

                        if (fragmentOwner != null) {
                            fragmentOwner.commit()
                        }
                        R.color.home to ""

                    }
                    R.id.activityV ->{
                        val manageFrag= ManageFragment()
                        val fragManage=getFragmentManager()?.beginTransaction()?.apply {
                            replace(R.id.frame_layoutV,manageFrag)
                        }
                        if (fragManage != null) {
                            fragManage.commit()
                        }

                        R.color.favorites to ""
                    }


                    else -> R.color.white to ""
                }
                val color = ContextCompat.getColor(requireContext(), option.first)
                container.colorAnimation(lastColor, color)
                lastColor = color
                title.text = option.second
            }
        })

        button.setOnClickListener {
            if (menuV.isExpanded()) {
                TransitionManager.beginDelayedTransition(container, ChangeBounds())
                menuV.collapse()
            } else {
                TransitionManager.beginDelayedTransition(container, ChangeBounds())
                menuV.expand()
            }
        }

        if (!isRemoving) {
            menuV.setItemSelected(R.id.homeV)
            // cv.visibility=View.VISIBLE
        }
    }

}
