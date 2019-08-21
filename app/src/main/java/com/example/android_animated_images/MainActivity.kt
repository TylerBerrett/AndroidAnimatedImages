package com.example.android_animated_images

import android.annotation.SuppressLint
import android.graphics.ImageDecoder
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    val listOfGifs = arrayListOf(R.drawable.light_bulb_gif, R.drawable.record)
    var pointer = 0

    fun incrementPointer() {
        pointer++
        if(pointer >= listOfGifs.size){
            pointer = 0
        }
    }

    fun decrementPointer() {
        pointer--
        if(pointer < listOfGifs.size){
            pointer = listOfGifs.size - 1
        }
    }

    private fun lightBulbGif(id: Int) {
        val lightBulbGif = ContextCompat.getDrawable(this, id)
        imageHolder.setImageDrawable(lightBulbGif)
        (lightBulbGif as AnimationDrawable).start()
    }

    private fun recordGif(id: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val animatedGif = ImageDecoder.decodeDrawable(ImageDecoder.createSource(resources, id))
            imageHolder.setImageDrawable(animatedGif)
            (animatedGif as AnimatedImageDrawable).start()
        }
    }




    private lateinit var imageHolder: ImageView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.button_back -> {
                decrementPointer()
                imageHolder.setImageDrawable(ContextCompat.getDrawable(this, listOfGifs[pointer]))
                return@OnNavigationItemSelectedListener true
            }
            R.id.button_play -> {
                if(!nav_view.menu.findItem(R.id.button_play).isCheckable){
                    nav_view.menu.findItem(R.id.button_play).title = "play"
                    nav_view.menu.findItem(R.id.button_play).isCheckable = true
                    nav_view.menu.findItem(R.id.button_play).icon = ContextCompat.getDrawable(this, R.drawable.play_button)
                    imageHolder.setImageDrawable(ContextCompat.getDrawable(this, listOfGifs[pointer]))
                }
                else{
                    nav_view.menu.findItem(R.id.button_play).icon = ContextCompat.getDrawable(this, R.drawable.pause_button)
                    nav_view.menu.findItem(R.id.button_play).title = "pause"
                    nav_view.menu.findItem(R.id.button_play).isCheckable = false
                    when(pointer){
                        1 -> recordGif(listOfGifs[pointer])
                        0 -> lightBulbGif(listOfGifs[pointer])
                    }
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.button_forward -> {
                incrementPointer()
                imageHolder.setImageDrawable(ContextCompat.getDrawable(this, listOfGifs[pointer]))
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

        pausePlay()




    }



    private fun pausePlay() {
        button_play_pause.tag = 0
        button_play_pause.setOnClickListener {




            if (it.tag == 0) {
                it.tag = 1
                //plays
                val playToPause = ContextCompat.getDrawable(this, R.drawable.avd_play_to_pause)
                button_play_pause.setImageDrawable(playToPause)
                (playToPause as Animatable).start()
                when(pointer){
                    1 -> recordGif(listOfGifs[pointer])
                    0 -> lightBulbGif(listOfGifs[pointer])
                }

            }
            //pause
            else {
                it.tag = 0
                val pauseToPlay = ContextCompat.getDrawable(this, R.drawable.avd_pause_to_play)
                button_play_pause.setImageDrawable(pauseToPlay)
                (pauseToPlay as Animatable).start()
                imageHolder.setImageDrawable(ContextCompat.getDrawable(this, listOfGifs[pointer]))
            }
        }
    }
}
