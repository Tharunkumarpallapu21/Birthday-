package com.sixyears.onestory.ui.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import com.sixyears.onestory.R

/**
 * Draws a heart shape clipped/filled by an animated "liquid" wave that rises
 * to represent [percent] (0-100). Percentage text is drawn in the center.
 * Pure Canvas — no Lottie dependency required for this specific element.
 */
class LiquidHeartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var targetPercent: Int = 0
    private var animatedPercent: Float = 0f
    private var wavePhase: Float = 0f

    private val heartPath = Path()
    private val fillPath = Path()
    private val clipPath = Path()

    private val outlinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 6f
        color = Color.parseColor("#FFD6DF")
    }

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#FFF0F3")
    }

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.text_primary)
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
    }

    private var fillAnimator: ValueAnimator? = null
    private var waveAnimator: ValueAnimator? = null

    fun setPercent(percent: Int, animate: Boolean = true) {
        targetPercent = percent.coerceIn(0, 100)
        if (animate) {
            fillAnimator?.cancel()
            fillAnimator = ValueAnimator.ofFloat(animatedPercent, targetPercent.toFloat()).apply {
                duration = 1200
                interpolator = DecelerateInterpolator()
                addUpdateListener {
                    animatedPercent = it.animatedValue as Float
                    invalidate()
                }
                start()
            }
        } else {
            animatedPercent = targetPercent.toFloat()
            invalidate()
        }
        startWaveAnimation()
    }

    private fun startWaveAnimation() {
        if (waveAnimator?.isRunning == true) return
        waveAnimator = ValueAnimator.ofFloat(0f, (Math.PI * 2).toFloat()).apply {
            duration = 3000
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                wavePhase = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        buildHeartPath(w.toFloat(), h.toFloat())
        textPaint.textSize = h * 0.18f
    }

    private fun buildHeartPath(w: Float, h: Float) {
        heartPath.reset()
        // Heart drawn within a 24x24 viewbox scaled to (w, h)
        val scaleX = w / 24f
        val scaleY = h / 24f

        heartPath.moveTo(12f * scaleX, 21.35f * scaleY)
        heartPath.cubicTo(
            10.55f * scaleX, 20.03f * scaleY,
            5.4f * scaleX, 15.36f * scaleY,
            2f * scaleX, 8.5f * scaleY
        )
        // Approximate remainder of heart shape with cubic curves
        heartPath.cubicTo(
            2f * scaleX, 5.42f * scaleY,
            4.42f * scaleX, 3f * scaleY,
            7.5f * scaleX, 3f * scaleY
        )
        heartPath.cubicTo(
            9.24f * scaleX, 3f * scaleY,
            10.91f * scaleX, 3.81f * scaleY,
            12f * scaleX, 5.09f * scaleY
        )
        heartPath.cubicTo(
            13.09f * scaleX, 3.81f * scaleY,
            14.76f * scaleX, 3f * scaleY,
            16.5f * scaleX, 3f * scaleY
        )
        heartPath.cubicTo(
            19.58f * scaleX, 3f * scaleY,
            22f * scaleX, 5.42f * scaleY,
            22f * scaleX, 8.5f * scaleY
        )
        heartPath.cubicTo(
            22f * scaleX, 12.28f * scaleY,
            18.6f * scaleX, 15.36f * scaleY,
            13.45f * scaleX, 20.04f * scaleY
        )
        heartPath.close()

        fillPaint.shader = LinearGradient(
            0f, h, 0f, 0f,
            ContextCompat.getColor(context, R.color.primary),
            ContextCompat.getColor(context, R.color.secondary),
            Shader.TileMode.CLAMP
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()
        if (w == 0f || h == 0f) return

        // Draw background heart
        canvas.drawPath(heartPath, bgPaint)

        // Build wave fill clipped to heart shape
        val fillLevel = h - (h * (animatedPercent / 100f))
        fillPath.reset()
        val waveAmplitude = h * 0.015f
        val waveLength = w

        fillPath.moveTo(0f, h)
        fillPath.lineTo(0f, fillLevel)

        var x = 0f
        while (x <= w) {
            val y = fillLevel + waveAmplitude * Math.sin((x / waveLength * 2 * Math.PI) + wavePhase).toFloat()
            fillPath.lineTo(x, y)
            x += 4f
        }
        fillPath.lineTo(w, h)
        fillPath.close()

        canvas.save()
        canvas.clipPath(heartPath)
        canvas.drawPath(fillPath, fillPaint)
        canvas.restore()

        // Outline
        canvas.drawPath(heartPath, outlinePaint)

        // Percentage text in center
        val cx = w / 2f
        val cy = h / 2f - ((textPaint.descent() + textPaint.ascent()) / 2f)
        canvas.drawText("${animatedPercent.toInt()}%", cx, cy, textPaint)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        fillAnimator?.cancel()
        waveAnimator?.cancel()
    }
}
