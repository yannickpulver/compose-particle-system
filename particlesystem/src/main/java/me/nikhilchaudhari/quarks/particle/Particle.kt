package me.nikhilchaudhari.quarks.particle

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import me.nikhilchaudhari.quarks.core.Vector2D
import me.nikhilchaudhari.quarks.core.add
import me.nikhilchaudhari.quarks.core.roundTo
import me.nikhilchaudhari.quarks.core.scalarMultiply
import kotlin.math.roundToInt


internal class Particle constructor(
    var initialX: Float = 0f, var initialY: Float = 0f,
    val color: Color = Color.Yellow,
    var size: Float = 25f,
    var velocity: Vector2D = Vector2D(0f, 0f),
    var acceleration: Vector2D = Vector2D(0f, 0f),
    var lifetime: Float = 255f,
    var agingFactor: Float = 20f,
    var shape: Bitmap? = null,
    var rotationSpeed: IntRange = 0..1
) : Vector2D(initialX, initialY) {

    private val originalLife = lifetime
    private var alpha = 1f
    private var rotation = 0f

    fun finished(): Boolean = this.lifetime < 0

    fun applyForce(force: Vector2D) {
        this.acceleration.add(force)
    }

    fun update(dt: Float) {
        lifetime -= agingFactor

        if (lifetime >= 0) {
            this.alpha = (lifetime / originalLife).roundTo(3)
        }

        // Add acceleration to velocity vector
        this.velocity.add(acceleration)

        // add velocity vector to positions
        this.add(velocity, scalar = dt)

        //set acceleration back to 0
        this.acceleration.scalarMultiply(0f)
    }

    private val sizedShape = shape?.let {
        Bitmap.createScaledBitmap(
            it,
            this@Particle.size.roundToInt(),
            this@Particle.size.roundToInt(),
            false
        ).asImageBitmap()
    }

    private var rotationDirectionAndSpeed = needSomeSpeed(rotationSpeed.random())

    private fun needSomeSpeed(random: Int):Int{
        return when(random){
            in -8..0 -> random - 8
            in 1..8 -> random + 8
            else -> random
        }
    }

    fun show(drawScope: DrawScope) {
        rotation = (rotation + rotationDirectionAndSpeed) % 360
        drawScope.rotate(rotation, pivot = Offset(x, y)) {
            sizedShape?.let { bitmap ->
                drawScope.drawImage(
                    image = bitmap,
                    topLeft = Offset(x - (bitmap.width / 2), y - (bitmap.height / 2)),
                    alpha = alpha,
                    colorFilter = ColorFilter.tint(color = color)
                )
            } ?: drawScope.drawCircle(
                color = color,
                radius = this@Particle.size / 2,
                center = Offset(x, y),
                alpha = alpha,
                style = Stroke(width = 10.0f)
            )
        }
    }
}
