package com.exam.wspage

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import com.exam.netstatelib.NetWorkMonitorManager
import com.exam.netstatelib.NetWorkState
import com.exam.wslib.databinding.ActivityWebSocketBinding
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class WebSocketActivity : AppCompatActivity() {
    private val mWebSocketHandler = Handler { msg ->
        appendMsgDisplay(msg.obj as String)
        false
    }
    private val binding: ActivityWebSocketBinding by lazy{
        ActivityWebSocketBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    private var mOkHttpClient: OkHttpClient? = null
    private fun webSocketConnect() {
        mOkHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
            .url(TEST_URL)
            .build()
        val listener: ClientWebSocketListener = ClientWebSocketListener()
        mOkHttpClient!!.newWebSocket(request, listener)
        mOkHttpClient!!.dispatcher.executorService.shutdown()
    }

    private var mWebSocket: WebSocket? = null
    fun connect(view: View?) {
        webSocketConnect()
    }

    fun sendMsg(view: View?) {
        if (mWebSocket != null) {
            if (binding.inputMsg.getText() != null) {
                val text: String = binding.inputMsg.getText().toString()
                mWebSocket!!.send(text)
            }
        } else {
            appendMsgDisplay("no connection")
        }
    }

    fun close(view: View?) {
        if (null != mWebSocket) {
            mWebSocket!!.close(1000, "client say bye")
            mWebSocket = null
        }
    }

    fun clear(view: View?) {
        binding.tvMsg.setText("")
    }

    private inner class ClientWebSocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            mWebSocket = webSocket
            showServerMsg("connected")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            showServerMsg(text)
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            showServerMsg(bytes.utf8())
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            if (null != mWebSocket) {
                mWebSocket!!.close(1000, "close")
                mWebSocket = null
            }
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            val message = Message.obtain()
            message.obj = reason
            message.what = 2
            mWebSocketHandler.sendMessage(message)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            val message = Message.obtain()
            message.obj = t.localizedMessage
            message.what = 3
            mWebSocketHandler.sendMessage(message)
        }
    }

    private fun showServerMsg(msg: String) {
        val message = Message.obtain()
        message.obj = msg
        message.what = 1
        mWebSocketHandler.sendMessage(message)
    }

    private fun appendMsgDisplay(msg: String) {
        Log.d(TAG, "msg :$msg")
        val textBuilder = StringBuilder()
        if (!TextUtils.isEmpty(binding.tvMsg.getText())) {
            textBuilder.append(binding.tvMsg.getText().toString())
            textBuilder.append("\n")
        }
        textBuilder.append(msg)
        textBuilder.append("\n")
        binding.tvMsg.setText(textBuilder.toString())
        binding.tvMsg.post(Runnable { binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN) })
    }

    override fun onStart() {
        super.onStart()
        NetWorkMonitorManager.getInstance().register(this)
    }

    override fun onStop() {
        super.onStop()
        NetWorkMonitorManager.getInstance().unregister(this)
    }

    //    @NetWorkMonitor(monitorFilter = {NetWorkState.GPRS,NetWorkState.NONE})
    fun onNetWorkStateChange(netWorkState: NetWorkState) {
        Log.i("barry", "onNetWorkStateChange >>> :" + netWorkState.name)
    }

    companion object {
        private const val SERVER_URL = "ws://52.81.65.206:9800/ws"
        private const val TEST_URL = "wss://echo.websocket.org"
        private const val TAG = "barry"
    }
}