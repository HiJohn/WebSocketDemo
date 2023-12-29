package me.jscall

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import me.jscall.databinding.ActivityWebViewBinding
import me.utils.toast
import org.json.JSONObject


class WebViewActivity : AppCompatActivity() {


    companion object {
        const val URL_PATH = "file:android_asset/test.html"
        const val JS_FUNC_NAME_TEST = "test"
    }

    private val binding: ActivityWebViewBinding by lazy {
        ActivityWebViewBinding.inflate(layoutInflater)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // 允许与js交互
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(URL_PATH)
        val jsCall = JsCalls()
        jsCall.context = this
        binding.webView.addJavascriptInterface(jsCall, JS_FUNC_NAME_TEST)

    }

    private fun callJs() {
        val jsonObject = JSONObject()
        jsonObject.put("message", "Hello, world!")
        val jsCode = "javascript:alert(JSON.stringify(data));"
        binding.webView.evaluateJavascript(jsCode, null);
    }

     class JsCalls {
         var context: Context? = null
        @JavascriptInterface
        fun hello(name: String) {
            Log.e("WebView", name)
            Toast.makeText(context, name, Toast.LENGTH_SHORT).show()
        }


    }
}

