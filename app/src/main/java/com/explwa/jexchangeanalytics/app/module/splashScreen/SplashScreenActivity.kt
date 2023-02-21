package com.explwa.jexchangeanalytics.app.module.splashScreen

import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.explwa.jexchangeanalytics.app.module.login.activity.LoginActivity
import com.explwa.jexchangeanalytics.app.utils.BaseActivity

class SplashScreenActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(
            Intent(
                this@SplashScreenActivity,
                LoginActivity::class.java
            )
        )
        installSplashScreen()
        finish()
    }
}