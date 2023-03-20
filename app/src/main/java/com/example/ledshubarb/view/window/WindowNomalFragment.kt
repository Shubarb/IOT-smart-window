package com.example.ledshubarb.view.window

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import ch.halcyon.squareprogressbar.SquareProgressBar
import com.example.ledshubarb.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class WindowNomalFragment : Fragment() {

    private var mOpenbutton: ImageView? = null
    private var mClosebutton: ImageView? = null
    private var mTickbutton: ImageView? = null
    private var mSquareProcess: SquareProgressBar? = null
    private var mTxtCount: TextView? = null
    private lateinit var databaseMode : DatabaseReference
    private var number : String? = null

    var max = 180
    var min = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_window_nomal, container, false)
        mSquareProcess = view.findViewById(R.id.sprogressbar)
        mOpenbutton = view.findViewById(R.id.openButton)
        mTickbutton = view.findViewById(R.id.tickButton)
        mClosebutton = view.findViewById(R.id.closeButton)
        mTxtCount = view.findViewById(R.id.txtCount)

        mSquareProcess!!.setImage(R.drawable.snake)
        databaseMode = Firebase.database.getReference("ControlWindow")
        readData()
        var i = mTxtCount!!.text.toString().toInt()
        controlWindow(i,min,max)

        return view
    }

    fun squareProgressBar(i:Int){
        mSquareProcess!!.setProgress(i)
    }
    fun readData(){
        databaseMode!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                number = snapshot.child("Window").child("Number").value.toString()
                mTxtCount!!.text = number
                squareProgressBar(number!!.toInt())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "loadPost:onCancelled", error.toException())
            }

        })
    }

    fun controlWindow(c:Int,x:Int,y:Int){
        var count = c
        mTickbutton!!.setOnClickListener {
            var i = mTxtCount!!.text.toString().toInt()
            count = i
            if(count > y){
                count = y
                mTxtCount!!.text = "$count"
                squareProgressBar(count)
                databaseMode.child("Window").child("Number").setValue(count)
            }else if(count < x){
                count = x
                mTxtCount!!.text = "$count"
                squareProgressBar(count)
                databaseMode.child("Window").child("Number").setValue(count)
            }else {
                mTxtCount!!.text = "$count"
                squareProgressBar(count)
                databaseMode.child("Window").child("Number").setValue(count)
            }
        }
        mOpenbutton!!.setOnClickListener {
            mClosebutton!!.isEnabled = true
            if(count >= y){
                Toast.makeText(requireContext(),"Window Open 100%", Toast.LENGTH_SHORT).show()
                mOpenbutton!!.isEnabled = false
            }else{
                var i = mTxtCount!!.text.toString().toInt()
                count = i + 1
                mTxtCount!!.text = "$count"
                squareProgressBar(count)
                databaseMode.child("Window").child("Number").setValue(count)
            }
        }
        mClosebutton!!.setOnClickListener {
            mOpenbutton!!.isEnabled = true
            if(count <= x){
                Toast.makeText(requireContext(),"Window Closed", Toast.LENGTH_SHORT).show()
                mClosebutton!!.isEnabled = false
            }else{
                var i = mTxtCount!!.text.toString().toInt()
                count = i - 1
                mTxtCount!!.text = "$count"
                squareProgressBar(count)
                databaseMode.child("Window").child("Number").setValue(count)
            }
        }

    }

}