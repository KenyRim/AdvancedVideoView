package com.kenyrim.advancedvideoview.video_view

import android.media.MediaPlayer
import android.view.View


class InfoListener(private val placeholderView: View) : MediaPlayer.OnInfoListener {
    override fun onInfo(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        return if (what == 3) {
            placeholderView.visibility = View.GONE
            true
        } else {
            false
        }
    }

}
