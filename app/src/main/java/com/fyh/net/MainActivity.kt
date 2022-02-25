package com.fyh.net

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.fyh.net.utils.scopeNetLife

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        scopeNetLife {
            findViewById<TextView>(R.id.load).text = Get<String>("api").await()
        }
    }
}