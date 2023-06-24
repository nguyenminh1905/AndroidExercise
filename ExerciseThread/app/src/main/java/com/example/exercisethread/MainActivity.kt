package com.example.exercisethread

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import com.example.exercisethread.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var number: Int = 0
    private val handler = Handler(Looper.getMainLooper())

    private val increaseRunnable = object : Runnable {
        override fun run() {
            number++
            binding.number.text = number.toString()
            updateNumberColor()
            handler.postDelayed(this, 50) // speed of increasing
        }
    }

    private val decreaseRunnable = object : Runnable {
        override fun run() {
            number--
            binding.number.text = number.toString()
            updateNumberColor()
            handler.postDelayed(this, 50) // speed of decreasing
        }
    }

    private val resetRunnable = object : Runnable {
        override fun run() {
            if (number > 0) {
                number--
            } else if (number < 0) {
                number++
            }
            binding.number.text = number.toString()
            updateNumberColor()
            if (number != 0) {
                handler.postDelayed(this, 50) // start resetting
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var lastY = 0f

        binding.root.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastY = motionEvent.y
                }

                MotionEvent.ACTION_MOVE -> {
                    handler.removeCallbacks(resetRunnable)
                    val deltaY = lastY - motionEvent.y
                    if (deltaY > 0) {
                        handler.removeCallbacks(decreaseRunnable)
                        handler.post(increaseRunnable)
                    } else {
                        handler.removeCallbacks(increaseRunnable)
                        handler.post(decreaseRunnable)
                    }
                    lastY = motionEvent.y
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    handler.removeCallbacks(increaseRunnable)
                    handler.removeCallbacks(decreaseRunnable)
                    handler.postDelayed(resetRunnable, 5000) // Start resetting after 5 seconds
                }
            }
            true
        }

        binding.button1.setOnClickListener {
            handler.removeCallbacks(decreaseRunnable)
            handler.removeCallbacks(resetRunnable)
            number--
            binding.number.text = number.toString()
            updateNumberColor()
            handler.postDelayed(resetRunnable, 5000) // Start resetting after 5 seconds
        }

        binding.button2.setOnClickListener {
            handler.removeCallbacks(increaseRunnable)
            handler.removeCallbacks(resetRunnable)
            number++
            binding.number.text = number.toString()
            updateNumberColor()
            handler.postDelayed(resetRunnable, 5000) // Start resetting after 5 seconds
        }
    }

    private fun updateNumberColor() {
        if (number % 100 == 0 && number != 0) {
            val color = Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            binding.number.setTextColor(color)
        }
    }
}