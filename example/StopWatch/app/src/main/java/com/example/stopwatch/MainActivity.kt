package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private var time = 0
    private var isRunning = false
    private var timerTask: Timer? = null

    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener {
            isRunning = !isRunning

            if (isRunning) {
                start()
            } else {
                pause()
            }
        }

        lapButton.setOnClickListener {
            recordLapTime()
        }

        resetFab.setOnClickListener{
            reset()
        }
    }

    private fun start() {
        fab.setImageResource((R.drawable.ic_baseline_pause_24)) // 시작하면 일시정지 버튼으로

        timerTask = timer(period = 10) {// 10ms 마다 표시
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread{
                secTextView.text = "$sec"
                millTextView.text = "$milli"
            }
        }
    }

    private fun pause() {
        fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        timerTask?.cancel()

    }


    private fun recordLapTime() {
        var lapTime = this.time

        /**
         * android의 신같은 존재 context
         * parameter로 context를 던져야 하는 경우가 종종 있는데,
         * MainActivity의 최고 조상은 Context (Object빼고)
         */
        val textView = TextView(this)   // textView 생성 - 설명하자면 매우 길다고 합니다... 저자 왈
        textView.text =  "$lap LAB : ${lapTime / 100}.${lapTime % 100}"

        lapLayout.addView(textView, 0)
        lap++
    }

    private fun reset() {
        timerTask?.cancel()

        time = 0
        isRunning = false
        fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        secTextView.text = "0"
        millTextView.text = "0"

        lapLayout.removeAllViews()
        lap = 1
    }
}