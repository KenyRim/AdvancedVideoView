package com.kenyrim.advancedvideoview

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kenyrim.advancedvideoview.video_view.AdvancedVideoView
import com.kenyrim.advancedvideoview.video_view.VideoViewListener


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val frameVideoView = findViewById<View>(R.id.video_view) as AdvancedVideoView
        frameVideoView.setup(Uri.parse("https://www.rendez-vous.ru/video/story_test/3.mp4"))
        frameVideoView.setAdvancedVideoViewListener(object : VideoViewListener {
            override fun mediaPlayerPrepared(var1: MediaPlayer) {
                var1.start()
            }

            override fun mediaPlayerPrepareFailed(var1: MediaPlayer?, var2: String?) {
            }

        })
    }
}