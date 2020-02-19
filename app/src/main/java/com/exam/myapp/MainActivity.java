package com.exam.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.aware.DiscoverySession;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MainActivity extends AppCompatActivity {

    private Handler mWebSocketHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            appendMsgDisplay((String) msg.obj);

            return false;
        }
    });

    private static final String SERVER_URL = "ws://52.81.65.206:9800/ws";
    private static final String TAG = "barry";
    private EditText etContent;
    private TextView tvMsg;
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView = findViewById(R.id.scroll_view);
        etContent = findViewById(R.id.inputMsg);
        tvMsg = findViewById(R.id.tv_msg);
    }

    private OkHttpClient mOkHttpClient;

    private void webSocketConnect() {
        mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(SERVER_URL)
                .build();
        ClientWebSocketListener listener=new ClientWebSocketListener();
        mOkHttpClient.newWebSocket(request,listener);
        mOkHttpClient.dispatcher().executorService().shutdown();
    }


    private WebSocket mWebSocket;

    public void connect(View view) {
        webSocketConnect();
    }

    public void sendMsg(View view) {
        if (mWebSocket!=null){
            if (etContent.getText()!=null){
                String text = etContent.getText().toString();
                mWebSocket.send(text);
            }
        }else {
            Log.d(TAG,"no connection ");
        }
    }

    public void close(View view) {
        if(null!=mWebSocket){
            mWebSocket.close(1000,"bye");
            mWebSocket=null;
        }
    }

    public void clear(View view) {
        tvMsg.setText("");
    }

    private final class ClientWebSocketListener extends WebSocketListener {
        @Override
        public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
            mWebSocket=webSocket;
            Log.d(TAG,"onOpen response:"+response.message());
            mWebSocket.send("hey i`m client");
        }

        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
            Message message=Message.obtain();
            message.obj=text;
            message.what = 1;
            mWebSocketHandler.sendMessage(message);
        }

        @Override
        public void onMessage(@NotNull WebSocket webSocket, ByteString bytes) {
            Message message=Message.obtain();
            message.obj=bytes.utf8();
            message.what = 1;
            mWebSocketHandler.sendMessage(message);
        }

        @Override
        public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            if(null!=mWebSocket){
                mWebSocket.close(1000,"bye");
                mWebSocket=null;
            }
        }

        @Override
        public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
            Message message=Message.obtain();
            message.obj=reason;
            message.what = 2;
            mWebSocketHandler.sendMessage(message);
        }

        @Override
        public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
            Message message=Message.obtain();
            message.obj= t.getLocalizedMessage();
            message.what = 3;
            mWebSocketHandler.sendMessage(message);
        }
    }

    private void appendMsgDisplay(String msg) {
        StringBuilder textBuilder = new StringBuilder();
        if (!TextUtils.isEmpty(tvMsg.getText())) {
            textBuilder.append(tvMsg.getText().toString());
            textBuilder.append("\n");
        }
        textBuilder.append(msg);
        textBuilder.append("\n");
        tvMsg.setText(textBuilder.toString());
        tvMsg.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}
