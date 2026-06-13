package com.sixyears.onestory.ui.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.random.Random

/**
 * Simple confetti rain: colored rectangles falling and rotating.
 */
class ConfettiView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private data class Confetto(
        var x: Float,
        var y: Float,
        var size: Float,
        var speed: Float,
        var drift: Float,
        var rotation: Float,
        var rotationSpeed: Float,
        var color: Int
    )

    private val colors = listOf(
        Color.parseColor("#FF4D6D"),
        Color.parseColor("#FF758F"),
        Color.parseColor("#FFD6DF"),
        Color.parseColor("#FFC2D1"),
        Color.parseColor("#FFFFFF")
    )

    private val confetti = mutableListOf<Confetto>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var animator: ValueAnimator? = null
    private val count = 60

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0 && confetti.isEmpty()) {
            repeat(count) {
                confetti.add(randomConfetto(w, h, randomizeY = true))
            }
        }
    }

    private fun randomConfetto(w: Int, h: Int, randomizeY: Boolean): Confetto {
        return Confetto(
            x = Random.nextFloat() * w,
            y = if (randomizeY) Random.nextFloat() * h else -20f,
            size = 6f + Random.nextFloat() * 10f,
            speed = 2f + Random.nextFloat() * 4f,
            drift = (Random.nextFloat() - 0.5f) * 2f,
            rotation = Random.nextFloat() * 360f,
            rotationSpeed = (Random.nextFloat() - 0.5f) * 10f,
            color = colors.random()
        )
    }

    fun start() {
        if (animator?.isRunning == true) return
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 16
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                update()
                invalidate()
            }
            start()
        }
    }

    fun stop() {
        animator?.cancel()
        animator = null
    }

    private fun update() {
        val w = width
        val h = height
        if (w == 0 || h == 0) return
        for (c in confetti) {
            c.y += c.speed
            c.x += c.drift
            c.rotation += c.rotationSpeed
            if (c.y > h + 20) {
                val fresh = randomConfetto(w, h, randomizeY = false)
                c.x = fresh.x
                c.y = fresh.y
                c.size = fresh.size
                c.speed = fresh.speed
                c.drift = fresh.drift
                c.color = fresh.color
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (c in confetti) {
            paint.color = c.color
            canvas.save()
            canvas.translate(c.x, c.y)
            canvas.rotate(c.rotation)
            canvas.drawRect(-c.size / 2, -c.size / 4, c.size / 2, c.size / 4, paint)
            canvas.restore()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }
}
