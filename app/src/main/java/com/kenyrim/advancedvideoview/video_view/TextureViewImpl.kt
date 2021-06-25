package com.kenyrim.advancedvideoview.video_view

import android.content.Context
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.media.MediaPlayer.OnBufferingUpdateListener
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.util.AttributeSet
import android.view.Surface
import android.view.TextureView
import android.view.TextureView.SurfaceTextureListener
import android.view.View

class TextureViewImpl : TextureView, Impl, OnPreparedListener, SurfaceTextureListener,
    OnBufferingUpdateListener {
    private var placeholderView: View? = null
    private var videoUri: Uri? = null
    private var listener: VideoViewListener? = null
    private var surface: Surface? = null
    private var mediaPlayer: MediaPlayer? = null
    private var prepared = false
    private var startInPrepare = false

    constructor(context: Context?) : super(context!!) {
        this.surfaceTextureListener = this
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        this.surfaceTextureListener = this
    }

    override fun init(var1: View?, var2: Uri?) {
        this.placeholderView = var1
        this.videoUri = var2
        if (prepared) {
            release()
        }
        if (surface != null) {
            prepare()
        }
    }

    override fun onPrepared(mp: MediaPlayer) {
        mp.isLooping = true
        if (startInPrepare) {
            mp.start()
            startInPrepare = false
        }
        prepared = true
        if (listener != null) {
            listener!!.mediaPlayerPrepared(mp)
        }
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        this.surface = Surface(surface)
        if (!prepared && videoUri != null) {
            prepare()
        }
    }

    private fun prepare() {
        try {
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setDataSource(this.context, videoUri!!)
            mediaPlayer!!.setSurface(surface)
            mediaPlayer!!.setOnPreparedListener(this)
            mediaPlayer!!.setOnInfoListener(InfoListener(placeholderView!!))
            mediaPlayer!!.setOnBufferingUpdateListener(this)
            mediaPlayer!!.prepare()
        } catch (var2: Exception) {
            if (listener != null) {
                listener!!.mediaPlayerPrepareFailed(mediaPlayer, var2.toString())
            }
            removeVideo()
        }
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}
    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        removeVideo()
        return false
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
    override fun onBufferingUpdate(mp: MediaPlayer, percent: Int) {
    }

    override fun onResume() {
        if (prepared) {
            mediaPlayer!!.start()
        } else {
            startInPrepare = true
        }
        if (this.isAvailable) {
            onSurfaceTextureAvailable(this.surfaceTexture!!, 0, 0)
        }
    }

    override fun onPause() {
        removeVideo()
    }

    private fun release() {
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
        }
        mediaPlayer = null
        prepared = false
        startInPrepare = false
    }

    private fun removeVideo() {
        if (placeholderView != null) {
            placeholderView!!.visibility = View.VISIBLE
        }
        release()
    }

    override fun setAdvancedVideoViewListener(var1: VideoViewListener?) {
        this.listener = var1
    }


}
