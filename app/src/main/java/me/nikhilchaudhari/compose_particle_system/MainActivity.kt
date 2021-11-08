package me.nikhilchaudhari.compose_particle_system

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import me.nikhilchaudhari.compose_particle_system.ui.theme.ComposeparticlesystemTheme
import me.nikhilchaudhari.quarks.CreateParticles
import me.nikhilchaudhari.quarks.core.PI
import me.nikhilchaudhari.quarks.particle.Acceleration
import me.nikhilchaudhari.quarks.particle.EmissionType
import me.nikhilchaudhari.quarks.particle.Force
import me.nikhilchaudhari.quarks.particle.LifeTime
import me.nikhilchaudhari.quarks.particle.ParticleColor
import me.nikhilchaudhari.quarks.particle.ParticleSize
import me.nikhilchaudhari.quarks.particle.Velocity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeparticlesystemTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    //Fountain()
//                    Meteor()
                    Confetti()
//                    SnowFall()
//                    Explosion()
                }
            }
        }
    }

    @Composable
    fun Fountain() {
        CreateParticles(
            modifier = Modifier
                .fillMaxSize(),
            x = 500L..500L, y = 2000f,
            velocity = Velocity(xDirection = 1f, yDirection = -15f, angle = PI, randomize = true),
            force = Force.Gravity(0.2f),
            acceleration = Acceleration(0f, -4f),
            particleSize = ParticleSize.RandomSizes(10..20),
            particleColor = ParticleColor.RandomColors(listOf(Color.Blue, Color.Cyan)),
            lifeTime = LifeTime(255f, 1f),
            emissionType = EmissionType.FlowEmission(maxParticlesCount = 500),
            durationMillis = 10 * 1000
        )
    }

    @Composable
    fun Confetti() {
        BoxWithConstraints {
            val width = with(LocalDensity.current) { maxWidth.toPx() }

            CreateParticles(
                modifier = Modifier.fillMaxSize(),
                x = 0L..width.toLong(), y = -100f,
                velocity = Velocity(xDirection = 1f, yDirection = -2f, randomize = true),
                force = Force.Gravity(0.2f),
                acceleration = Acceleration(),
                particleSize = ParticleSize.RandomSizes(20..60),
                particleColor = ParticleColor.RandomColors(listOf(Color.White)),
                lifeTime = LifeTime(500f, 2f),
                emissionType = EmissionType.FlowEmission(
                    maxParticlesCount = EmissionType.FlowEmission.INDEFINITE,
                    emissionRate = 0.8f
                ),
                durationMillis = 10 * 1000,
                shapes = listOf(R.drawable.shape_ellipse, R.drawable.shape_stroke, R.drawable.shape_triangle, R.drawable.shape_rectangle),
                rotationSpeed = 0..0
            )
        }
    }


    @Composable
    fun Meteor() {
        CreateParticles(
            modifier = Modifier
                .fillMaxSize(),
            x = 500L..500L, y = 1200f,
            velocity = Velocity(xDirection = 1f, yDirection = 1f, randomize = true),
            force = Force.Wind(-0.2f, -0.1f),
            acceleration = Acceleration(-1f, -2f),
            particleSize = ParticleSize.ConstantSize(100f),
            particleColor = ParticleColor.SingleColor(Color.White),
            lifeTime = LifeTime(255f, 6f),
            emissionType = EmissionType.FlowEmission(maxParticlesCount = EmissionType.FlowEmission.INDEFINITE, emissionRate = 1f),
            durationMillis = 10 * 1000
        )
    }

    @Composable
    fun Explosion() {
        CreateParticles(
            modifier = Modifier
                .fillMaxSize(),
            x = 500L..500L, y = 1000f,
            velocity = Velocity(xDirection = -2f, yDirection = 2f),
            force = Force.Gravity(0.0f),
            acceleration = Acceleration(1f, 1f),
            particleSize = ParticleSize.RandomSizes(10..70),
            particleColor = ParticleColor.RandomColors(listOf(Color.Yellow, Color.Blue, Color.Red, Color.White, Color.Magenta, Color.Green)),
            lifeTime = LifeTime(255f, 0.5f),
            emissionType = EmissionType.ExplodeEmission(numberOfParticles = 300),
            durationMillis = 10 * 1000
        )
    }

    @Composable
    fun SnowFall() {
        CreateParticles(
            modifier = Modifier
                .fillMaxSize(),
            x = 500L..500L, y = -50f,
            velocity = Velocity(xDirection = 1f, yDirection = 1f, randomize = true),
            force = Force.Gravity(0.01f),
            acceleration = Acceleration(),
            particleSize = ParticleSize.RandomSizes(10..30),
            particleColor = ParticleColor.SingleColor(Color.White),
            lifeTime = LifeTime(255f, 0.01f),
            emissionType = EmissionType.FlowEmission(maxParticlesCount = 300, emissionRate = 0.5f),
            durationMillis = 10 * 1000
        )
    }
}
