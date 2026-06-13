package com.sixyears.onestory.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.animation.ValueAnimator
import kotlin.random.Random

/**
 * Lightweight custom view that draws floating heart particles rising
 * from the bottom of the screen with gentle horizontal drift.
 * Pure Canvas drawing — no external animation libraries required.
 */
class FloatingHeartsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private data class Particle(
        var x: Float,
        var y: Float,
        var size: Float,
        var speed: Float,
        var drift: Float,
        var alpha: Int,
        var rotation: Float
    )

    private val particles = mutableListOf<Particle>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#FFB3C6")
    }
    private var animator: ValueAnimator? = null
    private val particleCount = 14

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0 && particles.isEmpty()) {
            initParticles(w, h)
        }
    }

    private fun initParticles(w: Int, h: Int) {
        particles.clear()
        repeat(particleCount) {
            particles.add(randomParticle(w, h, randomizeY = true))
        }
    }

    private fun randomParticle(w: Int, h: Int, randomizeY: Boolean): Particle {
        return Particle(
            x = Random.nextFloat() * w,
            y = if (randomizeY) Random.nextFloat() * h else h.toFloat() + 40f,
            size = 14f + Random.nextFloat() * 22f,
            speed = 0.6f + Random.nextFloat() * 1.6f,
            drift = (Random.nextFloat() - 0.5f) * 1.2f,
            alpha = 60 + Random.nextInt(120),
            rotation = Random.nextFloat() * 360f
        )
    }

    fun start() {
        if (animator?.isRunning == true) return
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 16
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                updateParticles()
                invalidate()
            }
            start()
        }
    }

    fun stop() {
        animator?.cancel()
        animator = null
    }

    private fun updateParticles() {
        val w = width
        val h = height
        if (w == 0 || h == 0) return

        for (p in particles) {
            p.y -= p.speed
            p.x += p.drift
            p.rotation += 0.5f
            if (p.y < -p.size) {
                val fresh = randomParticle(w, h, randomizeY = false)
                p.x = fresh.x
                p.y = fresh.y
                p.size = fresh.size
                p.speed = fresh.speed
                p.drift = fresh.drift
                p.alpha = fresh.alpha
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (p in particles) {
            paint.alpha = p.alpha
            drawHeart(canvas, p)
        }
    }

    private fun drawHeart(canvas: Canvas, p: Particle) {
        val path = Path()
        val s = p.size
        canvas.save()
        canvas.translate(p.x, p.y)
        canvas.rotate(p.rotation)

        // Simple heart shape using two circles + a triangle, scaled by s
        path.moveTo(0f, s * 0.3f)
        path.cubicTo(-s * 0.6f, -s * 0.4f, -s * 1.1f, s * 0.3f, 0f, s * 0.9f)
        path.cubicTo(s * 1.1f, s * 0.3f, s * 0.6f, -s * 0.4f, 0f, s * 0.3f)
        path.close()

        canvas.drawPath(path, paint)
        canvas.restore()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }
}
