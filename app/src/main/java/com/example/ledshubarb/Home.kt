package com.example.ledshubarb

import android.content.SharedPreferences
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.androidtesst.view.ViewPageAdapter
import com.example.ledshubarb.util.BitmapConverter
import com.example.ledshubarb.view.`interface`.ProfileListener
import com.example.ledshubarb.view.ui.*
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import java.lang.Exception

class Home : AppCompatActivity(){

    private lateinit var mViewPager: FrameLayout
    private lateinit var mTabLayout: TabLayout
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mNavigationView: NavigationView
    private lateinit var mToolbar: Toolbar
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mHeaderLayout: View
    private lateinit var mAvatarImg: ImageView
    private lateinit var mNameText: TextView
    private lateinit var mEmailText: TextView
    private lateinit var mEditbtn: ImageView
    private lateinit var mShowAllReminder: ImageView
    private lateinit var mGenderText: TextView
    private lateinit var mDateOfBirthText: TextView
    private lateinit var mSharedPreferences: SharedPreferences
    private lateinit var mViewPageAdapter: ViewPageAdapter

    private lateinit var mHomeFragment: HomeFragment
    private lateinit var mMessageFragment: MessageFragment
    private lateinit var mNotificationFragment: NotificationFragment
    private lateinit var mAccountFragment: AccountFragment
    private lateinit var mEditProfileFragment: EditProfileFragment

    private var imgBitmap: Bitmap? = null
    private val imgConverter : BitmapConverter = BitmapConverter()

    private var ID_Home = 1
    private var ID_Message = 2
    private var ID_Notification = 3
    private var ID_Account = 4
    private var name = ""
    private var bottomNavigation: MeowBottomNavigation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Fragment View
        mHomeFragment = HomeFragment()
        mMessageFragment = MessageFragment()
        mNotificationFragment = NotificationFragment()
        mAccountFragment = AccountFragment()
        mEditProfileFragment = EditProfileFragment()

        //callback fragment
//        mEditProfileFragment.setProfileListener(this)

        mViewPager = findViewById(R.id.viewPager)

        //BottomNavigation
        bottomNavigation = findViewById(R.id.bottomNavigation)
//        bottomNavigation!!.show(1)
        bottomNavigation!!.add(MeowBottomNavigation.Model(ID_Home, R.drawable.ic_baseline_home_24))
        bottomNavigation!!.add(MeowBottomNavigation.Model(ID_Message, R.drawable.ic_baseline_message_24))
        bottomNavigation!!.add(MeowBottomNavigation.Model(ID_Notification, R.drawable.ic_baseline_notifications_24))
        bottomNavigation!!.add(MeowBottomNavigation.Model(ID_Account, R.drawable.ic_baseline_account_circle_24))
        bottomNavigation!!.setCount(ID_Account, "4")
        bottomNavigation!!.setCount(ID_Message, "99")
        bottomNavigation!!.setCount(ID_Notification, "10")

        showFirstFragment()
        showListenNameItem()
        selectItemMenu()
    }

    private fun showListenNameItem() {
        bottomNavigation!!.setOnShowListener {
            when (it.id) {
                ID_Home -> {
                    name = "Home"
                }
                ID_Message -> {
                    name = "Message"
                }
                ID_Notification -> {
                    name = "Notification"
                }
                ID_Account -> {
                    name = "Account"
                }
                else -> {
                    name = ""
                }
            }
        }
    }

    private fun showFirstFragment() {
        replace(HomeFragment())
        bottomNavigation!!.show(ID_Home, true)
    }

    private fun selectItemMenu(){
        bottomNavigation!!.setOnClickMenuListener {
//            Toast.makeText(this,name,Toast.LENGTH_SHORT).show()
            when(it.id){
                ID_Home -> {replace(HomeFragment())}
                ID_Message -> {replace(MessageFragment())}
                ID_Notification -> {replace(NotificationFragment())}
                ID_Account -> {replace(AccountFragment())}
                else -> {

                }
            }
        }
    }

    private fun replace(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.viewPager,fragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun onBackPressed() {
        if(this.mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            this.mDrawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }
}