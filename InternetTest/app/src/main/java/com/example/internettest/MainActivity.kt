package com.example.internettest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import okhttp3.*
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1: Button = findViewById(R.id.btnHTTP)
        val button2: Button = findViewById(R.id.btnOkHTTP)
        var url = URL("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1")

        button1.setOnClickListener {
            Thread {
                val connection = url.openConnection() as HttpURLConnection
                try {
                    val data = connection.inputStream.bufferedReader().readText()
                    connection.disconnect()
                    Log.d("Flickr cats", data);
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }

        button2.setOnClickListener {
            val url = URL("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1")
            val okHTTPClient: OkHttpClient = OkHttpClient()

            val request: Request = Request.Builder().url(url).build()
            okHTTPClient.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                }
                override fun onResponse(call: Call?, response: Response?) {
                    val json = response?.body()?.string()
                    val data = json.toString()
                    Log.i("Flickr OKcats", data)
                }
            })
        }
    }
}