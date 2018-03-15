package com.example.jquesadaabeijon.rocket

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.LinearInterpolator

class AnimationActivity : AppCompatActivity() {

    protected lateinit var rocket: View
    protected lateinit var frameLayout: View
    protected var screenHeight = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        rocket = findViewById(R.id.rocket)
        frameLayout = findViewById(R.id.container)
        frameLayout.setOnClickListener { onStartAnimation() }
        onStartAnimation()
    }

    override fun onResume() {
        super.onResume()

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenHeight = displayMetrics.heightPixels.toFloat()
    }

    private fun onStartAnimation() {

        val valueAnimator = ValueAnimator.ofFloat(0f, -screenHeight)

        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            rocket.translationY = value
        }

        valueAnimator.interpolator = LinearInterpolator() as TimeInterpolator?
        valueAnimator.duration = Companion.DEFAULT_ANIMATION_DURATION

        valueAnimator.start()
    }

    companion object {
        val DEFAULT_ANIMATION_DURATION = 2500L
    }
}

