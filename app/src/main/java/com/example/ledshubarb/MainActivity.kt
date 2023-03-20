package com.example.ledshubarb

import android.app.ActivityOptions
import android.util.Pair as UtilPair
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var SPLASH_SCREEN: Long = 2500

    private var topAnim: Animation? = null
    private var bottomAnim: Animation? = null
    private var imgLogo: ImageView? = null
    private var txtLogo: TextView? = null
    private var txtSlogan: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        //Animation
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation)
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation)

        //Hooks
        imgLogo = findViewById(R.id.imgLogo)
        txtLogo = findViewById(R.id.txtLogo)
        txtSlogan = findViewById(R.id.txtSlogan)

        //Set animation
        imgLogo!!.animation = topAnim
        txtLogo!!.animation = bottomAnim
        txtSlogan!!.animation = bottomAnim

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, Login::class.java)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val options = ActivityOptions.makeSceneTransitionAnimation(this,
                    UtilPair.create(imgLogo!!, "logo_image"),
                    UtilPair.create(txtSlogan!!, "logo_desc"),
                    UtilPair.create(txtLogo!!, "logo_text"))
                startActivity(intent, options.toBundle())
            }
        }, SPLASH_SCREEN)



    }
}