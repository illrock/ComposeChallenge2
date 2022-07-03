package com.ebayk.presentation.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ebayk.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //todo installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}