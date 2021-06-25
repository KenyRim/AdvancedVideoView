package com.kenyrim.advancedvideoview.video_view

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

class AdvancedVideoView : FrameLayout {
    private var impl: Impl
    private var implType: ImplType? = null
    var placeholderView: View
        private set
    private var videoUri: Uri? = null

    constructor(context: Context) : super(context) {
        placeholderView = createPlaceholderView(context)
        impl = this.getImplInstance(context)
        this.addView(placeholderView)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        placeholderView = createPlaceholderView(context)
        impl = this.getImplInstance(context, attrs)
        this.addView(placeholderView)
    }

    private fun getImplInstance(context: Context): Impl {
        implType = ImplType.TEXTURE_VIEW
        val textureVideoImpl = TextureViewImpl(context)
        this.addView(textureVideoImpl)
        return textureVideoImpl
    }

    private fun getImplInstance(context: Context, attrs: AttributeSet?): Impl {
        implType = ImplType.TEXTURE_VIEW
        val textureVideoImpl = TextureViewImpl(context, attrs)
        this.addView(textureVideoImpl)
        return textureVideoImpl
    }

    fun setup(videoUri: Uri?) {
        this.videoUri = videoUri
        impl.init(placeholderView, videoUri)
    }

    fun setup(videoUri: Uri?, placeholderBackgroundColor: Int) {
        this.videoUri = videoUri
        placeholderView.setBackgroundColor(placeholderBackgroundColor)
        impl.init(placeholderView, videoUri)
    }

    fun setup(videoUri: Uri?, placeholderDrawable: Drawable?) {
        this.videoUri = videoUri
        placeholderView.background = placeholderDrawable
        impl.init(placeholderView, videoUri)
    }

    private fun createPlaceholderView(context: Context): View {
        val placeholder = View(context)
        placeholder.setBackgroundColor(-16777216)
        val params = LayoutParams(-1, -1)
        placeholder.layoutParams = params
        return placeholder
    }

    fun onResume() {
        impl.onResume()
    }

    fun onPause() {
        impl.onPause()
    }

    fun getImplType(): ImplType? {
        return implType
    }

    fun setAdvancedVideoViewListener(listener: VideoViewListener?) {
        impl.setAdvancedVideoViewListener(listener)
    }

    fun setImpl(implType1: ImplType) {
        val implType: ImplType = implType1
        removeAllViews()
        this.implType = implType
        impl = when (implType) {
            ImplType.TEXTURE_VIEW -> {
                val textureViewImpl = TextureViewImpl(context)
                textureViewImpl.init(placeholderView, videoUri)
                this.addView(textureViewImpl)
                textureViewImpl
            }
            ImplType.VIDEO_VIEW -> {
                val videoViewImpl = VideoViewImpl(context)
                videoViewImpl.init(placeholderView, videoUri)
                this.addView(videoViewImpl)
                videoViewImpl
            }
        }
        this.addView(placeholderView)
        onResume()
    }

}