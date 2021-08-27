package com.sg.tedimagepickertest

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import gun0912.tedimagepicker.builder.TedImagePicker

class MainActivity : AppCompatActivity() {

    private lateinit var button:Button
    private lateinit var imageview:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById<Button>(R.id.button)
        imageview = findViewById<ImageView>(R.id.iv)

        button.setOnClickListener {
            TedImagePicker.with(this)
                .start { uri -> showSingleImage(uri) }
        }
    }

    private fun showSingleImage(uri: Uri) {

    }
}