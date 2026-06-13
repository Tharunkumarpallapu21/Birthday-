package com.sixyears.onestory.ui.widget

import android.content.Context
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.widget.AppCompatImageView

/**
 * Minimal pinch-to-zoom and pan ImageView. No external library required.
 */
class ZoomableImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val matrixValues = FloatArray(9)
    private val imageMatrix2 = Matrix()
    private var scaleFactor = 1f
    private val minScale = 1f
    private val maxScale = 4f

    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var isDragging = false

    private val scaleGestureDetector = ScaleGestureDetector(context, object :
        ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val newScale = (scaleFactor * detector.scaleFactor).coerceIn(minScale, maxScale)
            val factor = newScale / scaleFactor
            scaleFactor = newScale
            imageMatrix2.postScale(factor, factor, detector.focusX, detector.focusY)
            imageMatrix = imageMatrix2
            return true
        }
    })

    init {
        scaleType = ScaleType.MATRIX
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchX = event.x
                lastTouchY = event.y
                isDragging = true
            }
            MotionEvent.ACTION_MOVE -> {
                if (isDragging && scaleFactor > 1f) {
                    val dx = event.x - lastTouchX
                    val dy = event.y - lastTouchY
                    imageMatrix2.postTranslate(dx, dy)
                    imageMatrix = imageMatrix2
                    lastTouchX = event.x
                    lastTouchY = event.y
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isDragging = false
                if (scaleFactor <= 1f) {
                    resetZoom()
                }
            }
        }
        return true
    }

    fun resetZoom() {
        scaleFactor = 1f
        imageMatrix2.reset()
        imageMatrix = imageMatrix2
    }
}
