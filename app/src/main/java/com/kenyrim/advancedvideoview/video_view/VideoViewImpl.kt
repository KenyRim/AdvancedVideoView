package com.kenyrim.advancedvideoview.video_view

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.widget.VideoView


class VideoViewImpl : VideoView, Impl, OnPreparedListener {
    private var placeholderView: View? = null
    private var videoUri: Uri? = null
    private var listener: VideoViewListener? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun init(var1: View?, var2: Uri?) {
        this.placeholderView = var1
        this.videoUri = var2
        setOnPreparedListener(this)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setOnPreparedListener(this)
    }

    override fun onResume() {
        this.setVideoURI(videoUri)
        start()
    }

    override fun onPause() {
        placeholderView!!.visibility = View.VISIBLE
        stopPlayback()
    }

    override fun setAdvancedVideoViewListener(var1: VideoViewListener?) {
        this.listener = var1
    }

    override fun onPrepared(mediaPlayer: MediaPlayer) {
        mediaPlayer.isLooping = true
        mediaPlayer.setOnInfoListener(InfoListener(placeholderView!!))
        if (listener != null) {
            listener!!.mediaPlayerPrepared(mediaPlayer)
        }
    }
}
