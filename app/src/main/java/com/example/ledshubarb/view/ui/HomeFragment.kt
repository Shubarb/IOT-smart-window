package com.example.ledshubarb.view.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.ledshubarb.R
import com.example.ledshubarb.view.WindowActivity

class HomeFragment : Fragment() {

    private var mtoolbar: Toolbar? = null
    private var mWindowLinear: LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        mtoolbar = view.findViewById(R.id.mtoolbar)
        mWindowLinear= view.findViewById(R.id.window_linear)
        windowClickListener()
//        (activity as AppCompatActivity).setSupportActionBar(mtoolbar)

        return view
    }

    private fun windowClickListener() {
        mWindowLinear!!.setOnClickListener{
            val intent = Intent(requireContext(), WindowActivity::class.java)
            startActivity(intent)
        }
    }
}