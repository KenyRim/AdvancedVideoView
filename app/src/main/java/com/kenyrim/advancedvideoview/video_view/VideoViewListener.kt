package com.kenyrim.advancedvideoview.video_view

import android.media.MediaPlayer


interface VideoViewListener {
    fun mediaPlayerPrepared(var1: MediaPlayer)
    fun mediaPlayerPrepareFailed(var1: MediaPlayer?, var2: String?)
}