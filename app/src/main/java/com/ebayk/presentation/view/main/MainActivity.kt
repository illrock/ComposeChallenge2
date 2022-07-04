package com.ebayk.presentation.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.ebayk.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //todo installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupTransparentStatusBar()
    }

    private fun setupTransparentStatusBar() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}