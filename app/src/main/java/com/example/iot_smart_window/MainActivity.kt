package com.example.iot_smart_window

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.github.lzyzsd.circleprogress.CircleProgress
import com.github.lzyzsd.circleprogress.DonutProgress
import com.google.android.material.chip.Chip
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var databaseIndoor : DatabaseReference
    private lateinit var databaseOutdoor : DatabaseReference
    private lateinit var databaseMode : DatabaseReference
    private var humidity : String? = null
    private var temp : String? = null
    private var light : String? = null
    private var rain : String? = null
    private var gas : String? = null
    private var mode : String? = null
    private var prgHumi: DonutProgress? = null
    private var prgTemp: DonutProgress? = null
    private var prgGas: CircleProgress? = null
    private var prgLight: DonutProgress? = null
    private var prgRain: DonutProgress? = null
    private var switch: Switch? = null
    private var sbTimeAction: SeekBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prgHumi = findViewById(R.id.pgbHumi)
        prgRain = findViewById(R.id.pgbRain)
        prgLight = findViewById(R.id.pgbLight)
        prgTemp = findViewById(R.id.pgbTemp)
        prgGas = findViewById(R.id.pgbGas)
        switch = findViewById(R.id.mode)
        prgGas = findViewById(R.id.pgbGas)
        sbTimeAction = findViewById(R.id.sbWindowTimeSpeed)

        databaseIndoor = Firebase.database.getReference("ControlWindow")
        databaseIndoor?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                humidity = snapshot.child("Indoor").child("Humidity").value.toString()
                temp = snapshot.child("Indoor").child("Temperature").value.toString()
                gas = snapshot.child("Indoor").child("Gas").value.toString()
                Log.d("oo","$humidity , $temp, $gas")

                prgHumi?.setProgress(humidity!!.toFloat())
                prgGas?.setProgress(gas!!.toInt())
                prgTemp?.setProgress(temp!!.toFloat())


            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "loadPost:onCancelled", error.toException())

            }

        })

        databaseOutdoor = Firebase.database.getReference("ControlWindow")
        databaseOutdoor?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                light = snapshot.child("Outdoor").child("Light").value.toString()
                rain = snapshot.child("Outdoor").child("Rain").value.toString()
                Log.d("oo","$rain , $light, ")

                prgLight?.setProgress(light!!.toFloat())
                prgRain?.setProgress(rain!!.toFloat())

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "loadPost:onCancelled", error.toException())

            }

        })

        databaseMode = Firebase.database.getReference("ControlWindow")
        databaseMode?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mode = snapshot.child("Window").child("Mode").value.toString()
                Log.d("oo","$mode")
                if(mode == "Auto"){
                    switch!!.isChecked = true
                    Toast.makeText(applicationContext,"Auto",Toast.LENGTH_SHORT).show()
                }else if(mode == "Handmade"){
                    switch!!.isChecked = false
                    Toast.makeText(applicationContext,"Handmade",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(applicationContext,"Error mode",Toast.LENGTH_SHORT).show()
                }

                switch!!.setOnCheckedChangeListener({ _ , isChecked ->
                    if (isChecked == true){
                        databaseMode.child("Window").child("Mode").setValue("Auto")
                    }else if(isChecked == false)
                        databaseMode.child("Window").child("Mode").setValue("Handmade")
                    })
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "loadPost:onCancelled", error.toException())

            }

        })

        sbTimeAction!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                TODO("Not yet implemented")
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

        })




    }

}