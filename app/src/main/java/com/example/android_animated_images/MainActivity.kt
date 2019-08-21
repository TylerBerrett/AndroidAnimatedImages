package com.example.android_animated_images

import android.annotation.SuppressLint
import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var imageHolder: ImageView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.button_back -> {
                //imageHolder.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.button_play -> {
                //imageHolder.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.button_forward -> {
                //imageHolder.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        imageHolder = findViewById(R.id.image_gif_holder)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val animatedGif = ImageDecoder.decodeDrawable(ImageDecoder.createSource(resources, R.drawable.record))
            imageHolder.setImageDrawable(animatedGif)
            (animatedGif as AnimatedImageDrawable).start()
        }

    }
}
