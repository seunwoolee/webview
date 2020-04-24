package com.example.webview

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WebView.apply {
            settings.javaScriptEnabled = true
            this.webViewClient = WebViewClient()
        }
        WebView.loadUrl("http://www.google.com")

        registerForContextMenu(WebView)

        urlEditText.setOnEditorActionListener {_, actionID, _ ->
            if(actionID == EditorInfo.IME_ACTION_SEARCH) {
                WebView.loadUrl(urlEditText.text.toString())
                true
            } else {
                false
            }
        }
    }

    override fun onBackPressed() {
        if(WebView.canGoBack()) {
            WebView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_google, R.id.action_home -> {
                WebView.loadUrl("http://www.google.com")
                return true
            }
            R.id.action_naver -> {
                WebView.loadUrl("http://www.naver.com")
                return true
            }
            R.id.action_daum -> {
                WebView.loadUrl("http://www.daum.net")
                return true
            }
            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:010-1212-1212")
                if(intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text -> {
                sendSMS("010-9894-1131", WebView.url)
                return true
            }
            R.id.action_email -> {
                email("leemoney93@naver.com", WebView.url)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.action_share -> {
                share(WebView.url)
            }
            R.id.action_browser -> {
                browse(WebView.url)
            }
        }

        return super.onContextItemSelected(item)
    }

}
