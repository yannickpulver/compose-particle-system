package me.nikhilchaudhari.quarks

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import me.nikhilchaudhari.quarks.emitters.ParticleExplodeEmitter
import me.nikhilchaudhari.quarks.emitters.ParticleFlowEmitter
import me.nikhilchaudhari.quarks.particle.*

@Composable
fun CreateParticles(
    modifier: Modifier = Modifier,
    x: LongRange = 0L..0L,
    y: Float = 0f,
    velocity: Velocity = Velocity(xDirection = 1f, yDirection = 1f),
    force: Force = Force.Gravity(0.0f),
    acceleration: Acceleration = Acceleration(0f, 0f),
    particleSize: ParticleSize = ParticleSize.ConstantSize(),
    particleColor: ParticleColor = ParticleColor.SingleColor(),
    lifeTime: LifeTime = LifeTime(255f, 1f),
    emissionType: EmissionType = EmissionType.ExplodeEmission(),
    durationMillis: Int = 10000,
    shapes: List<Int> = listOf(),
    rotationSpeed: IntRange = 0..1,
    onFinished: ()->Unit = {},
) {
    val context = LocalContext.current

    val bitmaps = shapes.mapNotNull { drawable ->
        BitmapUtils.bitmapFromDrawableRes(context, drawable)
    }

    val dt = remember { mutableStateOf(0f) }

    var previousTime by remember { mutableStateOf(System.nanoTime()) }

    val emitter = remember {
        val particleConfigData = ParticleConfigData(
            x,
            y,
            velocity,
            force,
            acceleration,
            particleSize,
            particleColor,
            lifeTime,
            emissionType,
            bitmaps,
            rotationSpeed
        )
        when (emissionType) {
            is EmissionType.ExplodeEmission -> {
                ParticleExplodeEmitter(emissionType.numberOfParticles, particleConfigData)
            }
            is EmissionType.FlowEmission -> {
                ParticleFlowEmitter(
                    durationMillis,
                    emissionType,
                    particleConfigData,
                )
            }
        }
    }
    var finished = false
    LaunchedEffect(Unit) {
        while (!finished) {
            withFrameNanos {
                dt.value = ((it - previousTime) / 1E7).toFloat()
                previousTime = it
            }
        }
    }

    Canvas(modifier) {
        emitter.render(this)
        emitter.applyForce(force.createForceVector())
        finished = emitter.update(dt.value)
        if (finished)onFinished()

    }
}

