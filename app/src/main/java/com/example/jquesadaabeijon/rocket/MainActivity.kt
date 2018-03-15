package com.example.jquesadaabeijon.rocket

import android.content.Intent
import android.app.PendingIntent
import android.content.Context
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.jquesadaabeijon.rocket.PrefUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        val nowSeconds: Long
            get() = Calendar.getInstance().timeInMillis / 1000
    }

    enum class TimerState{
        Stopped, Paused, Running
    }

    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds: Long = 0
    private var timerState = TimerState.Stopped

    private var secondsRemaining: Long = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setIcon(R.drawable.ic_timer)
        supportActionBar?.title = "      Falcon Heavy Launcher"
        startTimer()
        timerState =  TimerState.Running

        start.setOnClickListener {v ->
            startTimer()
            timerState =  TimerState.Running
        }
    }

    private fun startTimer(){
        timerState = TimerState.Running

        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                updateCountdownUI()
            }
        }.start()
    }

    private fun updateCountdownUI(){
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        textView_countdown.text = "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0" + secondsStr}"
    }

    private fun onTimerFinished(){
        timerState = TimerState.Stopped

        PrefUtil.setSecondsRemaining(timerLengthSeconds, this)
        secondsRemaining = timerLengthSeconds

        updateCountdownUI()
        val datos = Intent(this, TimerActivity::class.java)
        datos.putExtra("key1", "valor1")
        startActivity(datos)
    }
}
