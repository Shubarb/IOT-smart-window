package com.example.ledshubarb

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.KeyEvent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.util.Pair as UtilPair
import android.widget.*
import androidx.core.widget.doOnTextChanged
import com.example.ledshubarb.class_app.Users
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputLayout.END_ICON_CLEAR_TEXT
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    private var btnCallSignIn: Button? = null
    private var btnAccept: RelativeLayout? = null
    private var imgLogin: ImageView? = null
    private var txtLogo: TextView? = null
    private var txtSlogan: TextView? = null
    private var edtUserName: TextInputEditText? = null
    private var edtPassword: TextInputEditText? = null
    private var edtFullname: TextInputEditText? = null
    private var edtEmail: TextInputEditText? = null
    private var edtPhone: TextInputEditText? = null

    private var layoutFullname: TextInputLayout? = null
    private var layoutUsername: TextInputLayout? = null
    private var layoutEmail: TextInputLayout? = null
    private var layoutPhone: TextInputLayout? = null
    private var layoutPassword: TextInputLayout? = null

    private var users: Users? = null

    private var database : DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //Hooks
        btnCallSignIn = findViewById(R.id.login_screen);
        imgLogin = findViewById(R.id.logo_imageResis);
        txtLogo = findViewById(R.id.logo_nameResis);
        txtSlogan = findViewById(R.id.slogan_nameResis);
        edtUserName = findViewById(R.id.usernameResis);
        edtPassword = findViewById(R.id.passwordResis);
        edtPhone = findViewById(R.id.phonenumber);
        edtFullname = findViewById(R.id.fullname);
        edtEmail = findViewById(R.id.email);
        btnAccept = findViewById(R.id.rltResis);
        layoutUsername = findViewById(R.id.usernameLayout)
        layoutFullname = findViewById(R.id.fullnameLayout)
        layoutEmail = findViewById(R.id.emailLayout)
        layoutPhone = findViewById(R.id.phonenumberLayout)
        layoutPassword = findViewById(R.id.passlayout)


        database = FirebaseDatabase.getInstance().getReference("User")

        //EditText Enter
        edtUserName!!.doOnTextChanged { text, start, before, count ->
            layoutUsername!!.isCounterEnabled = true
            layoutUsername!!.isEndIconVisible = true
            layoutUsername!!.isStartIconVisible = true
            layoutUsername!!.isErrorEnabled = true
            layoutUsername!!.setStartIconDrawable(R.drawable.ic_baseline_person_gray_24)
            if(text!!.length > 9){
                layoutUsername!!.error = "No More"
            }else if(text.length < 10){
                layoutUsername!!.error = null
            }
            layoutUsername!!.setEndIconMode(END_ICON_CLEAR_TEXT)

        }

        edtFullname!!.doOnTextChanged { text, start, before, count ->
            layoutFullname!!.isEndIconVisible = true
            layoutFullname!!.isStartIconVisible = true
            layoutFullname!!.setStartIconDrawable(R.drawable.ic_baseline_account_circle_24)
            layoutFullname!!.setEndIconMode(END_ICON_CLEAR_TEXT)
            if(text!!.length > 0){
                layoutFullname!!.error = null
                layoutFullname!!.isErrorEnabled = false
            }
        }

        edtEmail!!.doOnTextChanged { text, start, before, count ->
            layoutEmail!!.isEndIconVisible = true
            layoutEmail!!.isStartIconVisible = true
            layoutEmail!!.setStartIconDrawable(R.drawable.ic_baseline_email_24)
            layoutEmail!!.setEndIconMode(END_ICON_CLEAR_TEXT)
            if(text!!.length > 0){
                layoutEmail!!.error = null
                layoutEmail!!.isErrorEnabled = false
            }
        }

        edtPhone!!.doOnTextChanged { text, start, before, count ->
            layoutPhone!!.isEndIconVisible = true
            layoutPhone!!.isStartIconVisible = true
            layoutPhone!!.setStartIconDrawable(R.drawable.ic_baseline_phone_24)
            layoutPhone!!.setEndIconMode(END_ICON_CLEAR_TEXT)
            if(text!!.length > 0){
                layoutPhone!!.error = null
                layoutPhone!!.isErrorEnabled = false
            }
        }

        edtPassword!!.doOnTextChanged { text, start, before, count ->
            layoutPassword!!.isEndIconVisible = true
            layoutPassword!!.isStartIconVisible = true
            layoutPassword!!.setStartIconDrawable(R.drawable.ic_baseline_lock_24)
            if(text!!.length > 0){
                layoutPassword!!.error = null
                layoutPassword!!.isErrorEnabled = false
            }
        }

        //Button Click
        btnAccept!!.setOnClickListener{
            val username = edtUserName!!.text.toString()
            val fullname = edtFullname!!.text.toString()
            val phonenumber =edtPhone!!.text.toString()
            val email = edtEmail!!.text.toString()
            val password = edtPassword!!.text.toString()
            users = Users(fullname,username,password,phonenumber,email)

            if(username == ""){
                layoutUsername!!.error = "Enter Your User!!"
            }
            if(fullname == ""){
                layoutFullname!!.error = "Enter Your Fullname!!"
            }
            if(phonenumber == ""){
                layoutPhone!!.error = "Enter Your Phone Number!!"
            }
            if(email == ""){
                layoutEmail!!.error = "Enter Your Email!!"
            }
            if(password == ""){
                layoutPassword!!.error = "Enter Your Password!!"
            }
            if(username != "" && fullname != "" && phonenumber != "" && email != "" && password != "") {
                database!!.child("Account").child(username).setValue(users)
                val intent = Intent(this, Login::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val options = ActivityOptions.makeSceneTransitionAnimation(
                        this,
                        UtilPair.create(imgLogin!!, "logo_image"),
                        UtilPair.create(txtLogo!!, "logo_text"),
                        UtilPair.create(txtSlogan!!, "logo_desc"),
                        UtilPair.create(edtUserName!!, "username_tran"),
                        UtilPair.create(edtPassword!!, "password_tran"),
                        UtilPair.create(btnAccept!!, "button_login"),
                        UtilPair.create(btnCallSignIn!!, "button_signup")
                    )
                    startActivity(intent, options.toBundle())
                }
            }
        }

        btnCallSignIn!!.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val options = ActivityOptions.makeSceneTransitionAnimation(this,
                    UtilPair.create(imgLogin!!, "logo_image"),
                    UtilPair.create(txtLogo!!, "logo_text"),
                    UtilPair.create(txtSlogan!!, "logo_desc"),
                    UtilPair.create(edtUserName!!, "username_tran"),
                    UtilPair.create(edtPassword!!, "password_tran"),
                    UtilPair.create(btnAccept!!, "button_login"),
                    UtilPair.create(btnCallSignIn!!, "button_signup")
                )
                startActivity(intent, options.toBundle())
            }
        }
    }

}