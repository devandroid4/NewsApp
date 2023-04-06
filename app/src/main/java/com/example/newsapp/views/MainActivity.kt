package com.example.newsapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.localDb.AppSessionManager
import com.example.newsapp.views.fragments.LoginFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var appSessionManager:AppSessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        appSessionManager = AppSessionManager(this)
        showFragment(LoginFragment.newInstance())
        val view: View = binding.root
        setContentView(view)
    }
    private fun showFragment(fragment: Fragment){
        val fram = supportFragmentManager.beginTransaction()
        fram.setCustomAnimations(
            R.anim.slide_in,  // enter
            R.anim.fade_out,  // exit
            R.anim.fade_in,  // popEnter
            R.anim.slide_out // popExit
        )
        fram.replace(R.id.fragment_container,fragment)
        fram.commit()
    }
}
