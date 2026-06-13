package com.sixyears.onestory.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.sixyears.onestory.R
import com.sixyears.onestory.databinding.ActivitySplashBinding
import com.sixyears.onestory.ui.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val handler = Handler(Looper.getMainLooper())

    companion object {
        private const val TOTAL_DURATION_MS = 3000L
        private const val FADE_OUT_START_DELAY_MS = TOTAL_DURATION_MS - 800L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.splashRoot.startAnimation(fadeIn)

        // Start fade-out shortly before the total duration elapses.
        handler.postDelayed({
            val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
            binding.splashRoot.startAnimation(fadeOut)
        }, FADE_OUT_START_DELAY_MS)

        // Navigate after total duration.
        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, TOTAL_DURATION_MS)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
