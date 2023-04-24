package com.example.favoriterepos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showSplashScreen()

    }


    private fun showSplashScreen() {
        supportFragmentManager.
        beginTransaction().
        replace(R.id.fragment_container,
        SplashFragment()).commit()
    }

}