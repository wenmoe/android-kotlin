package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {    // 액티비티가 시작되면 최초로 호출되는 구문
        super.onCreate(savedInstanceState)  // 부모클래스 생성자 호출 필수
        setContentView(R.layout.activity_main)  // 액티비티(화면)에 표시될 레이아웃 파일 지정

        clickButton.setOnClickListener {
            textView.text = "버튼을 눌렀습니다"
        }
    }
}