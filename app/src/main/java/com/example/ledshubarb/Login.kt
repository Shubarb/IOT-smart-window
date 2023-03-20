
package com.example.ledshubarb

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import com.example.ledshubarb.class_app.Users
import com.google.android.material.textfield.TextInputEditText
import android.util.Pair as UtilPair
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*

class Login : AppCompatActivity() {

    private var btnCallSignUp: Button? = null
    private var btnLogin: RelativeLayout? = null
    private var imgLogin: ImageView? = null
    private var imgFinger: ImageView? = null
    private var txtLogo: TextView? = null
    private var txtSlogan: TextView? = null
    private var edtUserName: TextInputEditText? = null
    private var edtPassword: TextInputEditText? = null
    private var database : DatabaseReference? = null
    private var user : ArrayList<Users> ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Hooks
        btnCallSignUp = findViewById(R.id.signup_screen);
        imgLogin = findViewById(R.id.logo_image);
        imgFinger = findViewById(R.id.fingerprint);
        txtLogo = findViewById(R.id.logo_name);
        txtSlogan = findViewById(R.id.slogan_name);
        edtUserName = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.rlt);

        database = FirebaseDatabase.getInstance().getReference("User").child("Account")
        user = arrayListOf()

        //Event
        btnCallSignUp!!.setOnClickListener{
            val intent = Intent(this, SignUp::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val options = ActivityOptions.makeSceneTransitionAnimation(this,
                    UtilPair.create(imgLogin!!, "logo_image"),
                    UtilPair.create(txtLogo!!, "logo_text"),
                    UtilPair.create(txtSlogan!!, "logo_desc"),
                    UtilPair.create(edtUserName!!, "username_tran"),
                    UtilPair.create(edtPassword!!, "password_tran"),
                    UtilPair.create(btnLogin!!, "button_login"),
                    UtilPair.create(btnCallSignUp!!, "button_signup"))
                startActivity(intent, options.toBundle())
            }
        }

        btnLogin!!.setOnClickListener{
            val username = edtUserName!!.text.toString()
            val password = edtPassword!!.text.toString()
            val intent = Intent(this, Home::class.java)
            var flag = false
            database!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.e("tag", "onDatchangeshu")
                    if (snapshot.exists()) {
                        for (usesnapshot in snapshot.children) {
                            val x = usesnapshot.getValue(Users::class.java)
                            user!!.add(x!!)
                        }
                        Log.e("tag","Listuser: $user")
                        for (i in 0 until user!!.size) {
                            if(username == "${user!!.get(i).userNames}" && password == "${user!!.get(i).passWords}") {
                                Toast.makeText(
                                    applicationContext,
                                    "${user!!.get(i).userNames} , ${user!!.get(i).passWords}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                flag = true
                                break
                            }else flag = false
                        }

                        if (flag == true){
                            startActivity(intent)
                        }else{
                            Toast.makeText(
                                applicationContext,
                                "Password or Username false",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }else{
                        Log.e("tag","error")
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e("TAG", "loadPost:onCancelled", error.toException())

                }

            })

        }

        imgFinger!!.setOnClickListener {
            Toast.makeText(this,"FingerPrint coming soon", Toast.LENGTH_SHORT).show()
        }

    }
}