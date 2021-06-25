package com.kenyrim.advancedvideoview.video_view

import android.net.Uri
import android.view.View

internal interface Impl {
    fun init(var1: View?, var2: Uri?)
    fun onResume()
    fun onPause()
    fun setAdvancedVideoViewListener(var1: VideoViewListener?)
}
