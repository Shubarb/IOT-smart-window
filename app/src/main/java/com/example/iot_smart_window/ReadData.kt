package com.example.iot_smart_window

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.sql.DatabaseMetaData

class ReadData : AppCompatActivity() {

    private lateinit var database : DatabaseReference
//    private var humidity = 0
//    private var temp = 0.0f
//    private var gas = 0
    private var prgHumi: ProgressBar? = null
    private var prgTemp: ProgressBar? = null
    private var prgGas: ProgressBar? = null
    private var txtGas: TextView? = null
    private var txtTemp: TextView? = null
    private var txtHumi: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_data)


        database = Firebase.database.getReference("Indoor")
        database?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                    val humidity = snapshot.child("Humidity").value.toString()
                    val temp = snapshot.child("Temperature").value.toString().toFloat()
                    val gas = snapshot.child("Gas").value.toString().toInt()
                    Log.d("oo","$humidity , $temp, $gas")
                    prgHumi?.setProgress(humidity.toInt())
                    txtHumi?.setText("$humidity %")

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "loadPost:onCancelled", error.toException())

            }

        })

        prgHumi = findViewById(R.id.pgbHumi)
//        prgTemp = findViewById(R.id.pgbTemp)
//        prgGas = findViewById(R.id.pgbGas)
        txtHumi = findViewById(R.id.txtPrgHumi)
//        txtTemp = findViewById(R.id.txtprgTemp)
//        txtGas = findViewById(R.id.txtprgGas)


//        prgTemp?.setProgress(temp!!.toInt())
//        txtTemp?.setText("$temp %")
//        prgGas?.setProgress(gas!!)
//        txtGas?.setText("$gas %")
    }

    private fun getData() {

    }
}