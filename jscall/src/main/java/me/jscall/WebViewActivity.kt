package me.jscall

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import me.jscall.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {


    companion object{
        const val URL_PATH = "file:android_asset/test.html"
        const val JS_FUNC_NAME_TEST = "test"
    }

    private val binding :ActivityWebViewBinding by lazy {
        ActivityWebViewBinding.inflate(layoutInflater)
    }
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // 允许与js交互
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(URL_PATH)
        binding.webView.addJavascriptInterface(AndroidToJS(), JS_FUNC_NAME_TEST)
    }
}

class AndroidToJS{
    @JavascriptInterface
    fun hello(name: String) {
        Log.e("WebView", name)

    }
}