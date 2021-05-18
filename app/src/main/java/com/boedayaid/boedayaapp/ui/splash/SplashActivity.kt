package com.boedayaid.boedayaapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.boedayaid.boedayaapp.R
import com.boedayaid.boedayaapp.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val ivsplash: ImageView = findViewById(R.id.iv_splash_screen)
        ivsplash.alpha = 0f
        ivsplash.animate().setDuration(1500).alpha(1f).withEndAction {
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }

}
