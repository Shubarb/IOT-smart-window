package com.example.ledshubarb.view

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
//import androidx.camera.core.CameraSelector
//import androidx.camera.core.Preview
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.camera.view.PreviewView
import ch.halcyon.squareprogressbar.SquareProgressBar
import com.example.ledshubarb.R
import com.example.ledshubarb.notification.*
import com.example.ledshubarb.view.ui.HomeFragment
import com.example.ledshubarb.view.ui.MessageFragment
import com.example.ledshubarb.view.window.WindowNomalFragment
import com.example.ledshubarb.view.window.WindowRollingFragment
//import com.google.common.util.concurrent.ListenableFuture
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders
import java.util.*

class WindowActivity : AppCompatActivity() {

    private var mSquareProcess: SquareProgressBar? = null
    private var switch: RelativeLayout? = null
    private var mOpenbutton: ImageView? = null
    private var mClosebutton: ImageView? = null
    private var mTickbutton: ImageView? = null
    private var mTxtCount: TextView? = null
    private var mTxtMode: TextView? = null

    private var mNumbTemp: TextView? = null
    private var mNumbHumid: TextView? = null
    private var mNumbRain: TextView? = null
    private var mNumbGas: TextView? = null
    private var mNumbLight: TextView? = null
    private var mtxtRolling: TextView? = null
    private var mtxtNorrmal: TextView? = null

    private var mLinearMode: LinearLayout? = null
    private var mRolling: LinearLayout? = null
    private var mNomal: LinearLayout? = null

    private lateinit var mNomalFragment: WindowNomalFragment
    private lateinit var mRollingFragment: WindowRollingFragment

    private lateinit var databaseOutdoor : DatabaseReference
    private lateinit var databaseMode : DatabaseReference

    private var prgHumi: CircularFillableLoaders? = null
    private var prgTemp: CircularFillableLoaders? = null
    private var prgDust: CircularFillableLoaders? = null
    private var prgLight: CircularFillableLoaders? = null
    private var prgRain: CircularFillableLoaders? = null
    private var humidity : String? = null
    private var temp : String? = null
    private var light : String? = null
    private var rain : String? = null
    private var dust : String? = null
    private var mode : String? = null
    private var number : String? = null

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        findViewById()
        callFragment()
        replace(WindowNomalFragment())
        mtxtNorrmal!!.setTextColor(Color.parseColor("#FFEB3B"))
        databaseOut()
        databaseMode()
        clickEventWindow()
    }


    fun callFragment(){
        mRollingFragment = WindowRollingFragment()
        mNomalFragment  = WindowNomalFragment()
    }

    fun findViewById(){
        mSquareProcess = findViewById(R.id.sprogressbar)
        mOpenbutton = findViewById(R.id.openButton)
        mTickbutton = findViewById(R.id.tickButton)
        mClosebutton = findViewById(R.id.closeButton)
        mLinearMode = findViewById(R.id.linearMode)
        mTxtCount = findViewById(R.id.txtCount)
        mTxtMode = findViewById(R.id.txtAorH)
        switch = findViewById(R.id.btnAorH)
        mtxtNorrmal = findViewById(R.id.txtNormal)
        mtxtRolling = findViewById(R.id.txtRolling)

        prgHumi = findViewById(R.id.pgbHumi)
        prgRain = findViewById(R.id.pgbRain)
        prgLight = findViewById(R.id.pgbLight)
        prgTemp = findViewById(R.id.pgbTemp)
        prgDust = findViewById(R.id.pgbDust)

        mNumbGas = findViewById(R.id.numbGas)
        mNumbHumid = findViewById(R.id.numbHumid)
        mNumbLight = findViewById(R.id.numbLight)
        mNumbRain = findViewById(R.id.numbRain)
        mNumbTemp = findViewById(R.id.numbTemp)

        mRolling = findViewById(R.id.rolling)
        mNomal = findViewById(R.id.nomal)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun databaseMode(){
        databaseMode = Firebase.database.getReference("ControlWindow")
        databaseMode?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mode = snapshot.child("Window").child("Mode").value.toString()
                Log.d("oo","$mode")
                if(mode == "Auto"){
                    switch!!.setBackgroundResource(R.drawable.btnauto)
//                    mTxtCount!!.isEnabled = false
//                    mOpenbutton!!.isEnabled = false
//                    mClosebutton!!.isEnabled = false
//                    mTickbutton!!.isEnabled = false
                    mTxtMode!!.text = "Auto"
//                    Toast.makeText(applicationContext,"Auto",Toast.LENGTH_SHORT).show()
                }else if(mode == "Manual"){
                    switch!!.setBackgroundResource(R.drawable.btnhandmade)
//                    mTxtCount!!.isEnabled = true
//                    mOpenbutton!!.isEnabled = true
//                    mClosebutton!!.isEnabled = true
//                    mTickbutton!!.isEnabled = true
                    mTxtMode!!.text = "Manual"
//                    Toast.makeText(applicationContext,"Handmade",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(applicationContext,"Error mode",Toast.LENGTH_SHORT).show()
                }

                number = snapshot.child("Window").child("Number").value.toString()

                switch!!.setOnClickListener {
                    if (mTxtMode!!.text == "Auto"){
                        mTxtMode!!.text = "Manual"
                        switch!!.setBackgroundResource(R.drawable.btnhandmade)
                        databaseMode.child("Window").child("Mode").setValue("Manual")
                    }
                    else{
                        mTxtMode!!.text = "Auto"
                        switch!!.setBackgroundResource(R.drawable.btnauto)
                        databaseMode.child("Window").child("Mode").setValue("Auto")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "loadPost:onCancelled", error.toException())

            }

        })

    }

    fun databaseOut(){
        databaseOutdoor = Firebase.database.getReference("ControlWindow")
        databaseOutdoor?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                light = snapshot.child("Outdoor").child("Light").value.toString()
                rain = snapshot.child("Outdoor").child("Rain").value.toString()
                humidity = snapshot.child("Outdoor").child("Humidity").value.toString()
                temp = snapshot.child("Outdoor").child("Temperature").value.toString()
                dust = snapshot.child("Outdoor").child("Dust").value.toString()
//                Log.d("oo","$rain , $light, ")

                if(rain!!.toFloat().toInt() > 50) {
                    createNotificationChannel("Warning Rain", "Rain: $rain")
                }
                if(light!!.toFloat().toInt() > 2000) {
                    createNotificationChannel("Warning Light", "Light: $light")
                }
                if(temp!!.toFloat().toInt() > 30) {
                    createNotificationChannel("Warning Temperature", "Temperature: $temp")
                }

                prgHumi?.setProgress(100 - humidity!!.toFloat().toInt())
                mNumbHumid!!.text = "$humidity %"

                val d = dust!!.toFloat().toInt() * 100 / 1000
                prgDust?.setProgress(100 - d)
                mNumbGas!!.text = "$dust ug/m3"

                val t = temp!!.toFloat().toInt() * 100 / 50
                prgTemp?.setProgress(100 - t)
                mNumbTemp!!.text = "$temp °C"

                val l = light!!.toFloat().toInt() * 100 / 10000
                prgLight?.setProgress(100 - l)
                mNumbLight!!.text = "$light lux"

                prgRain?.setProgress(100 - rain!!.toFloat().toInt())
                mNumbRain!!.text = "$rain %"

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "loadPost:onCancelled", error.toException())

            }

        })
    }

    fun clickEventWindow(){
        mRolling!!.setOnClickListener{
            mtxtRolling!!.setTextColor(Color.parseColor("#FFEB3B"))
            mtxtNorrmal!!.setTextColor(Color.parseColor("#03DAC5"))
            replace(WindowRollingFragment())
        }
        mNomal!!.setOnClickListener{
            mtxtNorrmal!!.setTextColor(Color.parseColor("#FFEB3B"))
            mtxtRolling!!.setTextColor(Color.parseColor("#03DAC5"))
            replace(WindowNomalFragment())
        }
    }

    private fun replace(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameControl,fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun createNotificationChannel(title: String, message: String) {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, Notifications::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val contentView = RemoteViews(packageName, R.layout.activity_notifications)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            notificationChannel = NotificationChannel(channelId, description, importance)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                .setContent(contentView)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.air380x380)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.air380x380))
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234, builder.build())
    }

}