package com.example.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.email
import org.jetbrains.anko.sendSMS
import org.jetbrains.anko.share

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }

        webView.loadUrl("https://www.google.com")    // 기본 세팅

        // 글자가 입력될 때 마다 호출 됨
        uriEditText.setOnEditorActionListener { v, actionId, event ->   // 반응한 뷰, 액션ID, 이벤트
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                webView.loadUrl(uriEditText.text.toString())
                true
            } else {
                false
            }
        }

        // 컨텍스트 메뉴 등록
        registerForContextMenu(webView)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    // 옵션 메뉴 생성
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true // 액티비티에 메뉴가 있다고 인식
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        println("test")

        when(item.itemId) {
            R.id.action_google, R.id.action_home -> {
                webView.loadUrl("http://www.google.com")
                return true
            }
            R.id.action_naver -> {
                webView.loadUrl("http://www.naver.com")
                return true
            }
            R.id.action_daum -> {
                webView.loadUrl("http://www.daum.net")
                return true
            }
            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL)                      // 1. 인텐트 정의
                intent.data = Uri.parse("tel:010-8754-0827")        // 2. 인텐트에 데이터를 지정 ('tel:'은 전화번호를 나타내는 국제표준 방법)
                if (intent.resolveActivity(packageManager) != null) {        // 3. 이 인텐트를 수행하는 액티비티가 있는지 검사 (전화앱이 없는 태블릿 같은 경우)
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text -> {
                sendSMS("010-8754-0827", webView.url!!)
                return true
            }
            R.id.action_email -> {
                email("nacom1994@naver.com", "좋은 사이트", webView.url!!)
            }
        }
        return super.onOptionsItemSelected(item)    // 그 외에는 super 동작으로
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_share -> {
                share(webView.url!!)
                return true
            }
            R.id.action_browser -> {
                browse(webView.url!!)
                return true
            }
        }

        return super.onContextItemSelected(item)
    }
}