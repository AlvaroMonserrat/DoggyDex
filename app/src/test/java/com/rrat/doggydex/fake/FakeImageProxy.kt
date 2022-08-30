package com.rrat.doggydex.fake

import android.graphics.Rect
import android.media.Image
import androidx.camera.core.ImageInfo
import androidx.camera.core.ImageProxy

class FakeImageProxy : ImageProxy {
    override fun close() {
        TODO("Not yet implemented")
    }

    override fun getCropRect(): Rect {
        TODO("Not yet implemented")
    }

    override fun setCropRect(rect: Rect?) {
        TODO("Not yet implemented")
    }

    override fun getFormat(): Int {
        TODO("Not yet implemented")
    }

    override fun getHeight(): Int {
        TODO("Not yet implemented")
    }

    override fun getWidth(): Int {
        TODO("Not yet implemented")
    }

    override fun getPlanes(): Array<ImageProxy.PlaneProxy> {
        TODO("Not yet implemented")
    }

    override fun getImageInfo(): ImageInfo {
        TODO("Not yet implemented")
    }

    override fun getImage(): Image? {
        TODO("Not yet implemented")
    }

}