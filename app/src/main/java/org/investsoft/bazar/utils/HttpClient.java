package org.investsoft.bazar.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Despairs on 15.01.16.
 */
public class HttpClient {

    public enum HttpRequestType {
        POST, GET, PUT
    }

    private final String url;

    public HttpClient(String url) {
        Log.i("BAZAR", "Init HttpClient: URL = " + url);
        this.url = url;
    }

    public String sendPostRequest(String request) {
        return sendRequest(request, HttpRequestType.POST);
    }

    public String sendPutRequest(String request) {
        return sendRequest(request, HttpRequestType.PUT);
    }

    public String sendGetRequest() {
        return sendRequest(null, HttpRequestType.GET);
    }

    //@TODO TargetAPI..
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String sendRequest(String request, HttpRequestType methodType) {
        String response = null;
        Long start = System.currentTimeMillis();
        Log.i("BAZAR", "Send " + methodType.toString() + " request: " + request);
        try {
            //@TODO Конфиг
            URL _url = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(30000);
            conn.setRequestMethod(methodType.toString());
            conn.setDoInput(true);
            if (!methodType.equals(HttpRequestType.GET)) {
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                //Пишем запрос
                try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
                    out.write(request.getBytes("UTF-8"));
                    out.flush();
                }
            }
            // Получаем ответ
            try (DataInputStream in = new DataInputStream(conn.getInputStream());
                 StringWriter writer = new StringWriter()) {
                IOUtils.copy(in, writer);
                response = writer.toString();
            }
            Long stop = System.currentTimeMillis();
            Log.i("BAZAR", "[Duration: " + (stop - start) + "ms]: Receive response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static Boolean isInternetConnected(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
