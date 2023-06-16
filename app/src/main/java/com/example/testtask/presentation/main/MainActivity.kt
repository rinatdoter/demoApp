package com.example.testtask.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.testtask.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null){
            setInitialFragment(MainFragment())
        }
    }

    private fun setInitialFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.app_container, fragment)
            .commit()
    }
}