package com.example.loginapplicationnl.ui.splash

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import com.example.loginapplicationnl.base.BaseActivity
import com.example.loginapplicationnl.databinding.ActivitySplashBinding
import com.example.loginapplicationnl.ui.main.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {
    override fun inflateViewBinding(inflater: LayoutInflater) =
        ActivitySplashBinding.inflate(inflater)

    override fun initView() {
        setupAnimationOfViews()
        Handler().postDelayed({
            // Start your app main activity
            val i = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(i)
            // close this activity
            finish()
        }, 3000)
    }

    private fun setupAnimationOfViews() {
        viewBinding.imageViewForeground.post {
            ObjectAnimator.ofFloat(viewBinding.imageViewForeground, "y", viewBinding.imageViewForeground.height.toFloat(), 0f).apply {
                duration = 1000
                interpolator = OvershootInterpolator()
                start()
            }
        }

        viewBinding.text.post {
            ObjectAnimator.ofFloat(viewBinding.text, "alpha", 0f, 1f).apply {
                duration = 1250
                startDelay = 1750L
                interpolator = DecelerateInterpolator(1.2f)
                start()
            }
        }
    }

    override fun initData() {
        // TODO("Not yet implemented")
    }

    override fun getViewModelClass(): Class<SplashViewModel> {
        return SplashViewModel::class.java
    }


}