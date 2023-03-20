package com.example.ledshubarb.view.window

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.ledshubarb.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class WindowRollingFragment : Fragment() {

    private var mUpbutton: ImageView? = null
    private var mDownbutton: ImageView? = null
    private var mStopbutton: ImageView? = null
    private lateinit var databaseWindow : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_window_rolling, container, false)
        mUpbutton = view.findViewById(R.id.upButton)
        mDownbutton = view.findViewById(R.id.downButton)
        mStopbutton = view.findViewById(R.id.stopButton)
        clickEvent()
        return view
    }

    private fun clickEvent(){
        databaseWindow= Firebase.database.getReference("ControlWindow")
        mUpbutton!!.setOnClickListener {
            databaseWindow.child("Window").child("Down").setValue(0)
            databaseWindow.child("Window").child("Up").setValue(1)
        }

        mDownbutton!!.setOnClickListener {
            databaseWindow.child("Window").child("Up").setValue(0)
            databaseWindow.child("Window").child("Down").setValue(1)
        }

        mStopbutton!!.setOnClickListener {
            databaseWindow.child("Window").child("Stop").setValue(1)
            databaseWindow.child("Window").child("Up").setValue(0)
            databaseWindow.child("Window").child("Down").setValue(0)
        }
    }


}