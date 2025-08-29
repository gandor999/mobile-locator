package com.gandor.mobile_locator.layers.ui.composables

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun AnimatedEntry(
    modifier: Modifier = Modifier,
    animationSpec: FiniteAnimationSpec<Float> = tween(500),
    content: @Composable () -> Unit
) {
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alpha.animateTo(1f, animationSpec = animationSpec)
    }

    Box(
        modifier = modifier.alpha(alpha.value)
    ) {
        content()
    }
}

@Composable
fun PopOutEntry(
    modifier: Modifier = Modifier,
    animationSpec: FiniteAnimationSpec<Float> = tween(500, easing = OvershootInterpolator().toEasing()),
    content: @Composable () -> Unit
) {
    val scale = remember { Animatable(0.8f) }

    LaunchedEffect(Unit) {
        scale.animateTo(1f, animationSpec = animationSpec)
    }

    Box(
        modifier = modifier.scale(scale.value)
    ) {
        content()
    }
}

@Composable
fun SlideInEntry(
    modifier: Modifier = Modifier,
    enterDuration: Int = 500,
    content: @Composable () -> Unit
) {
    val offsetX = remember { Animatable(0.1f) } // start off-screen (100% to the right)

    LaunchedEffect(Unit) {
        offsetX.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = enterDuration, easing = FastOutSlowInEasing)
        )
    }

    Box(
        modifier = modifier
            .graphicsLayer {
                translationX = offsetX.value * size.width // slide relative to width
            }
    ) {
        content()
    }
}

@Composable
fun PopOutEntryAndExit(
    visible: Boolean,
    modifier: Modifier = Modifier,
    initialScale: Float = 0.8f,
    targetScale: Float = 1f,
    enterDuration: Int = 500,
    exitDuration: Int = 300,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(
            initialScale = initialScale,
            animationSpec = tween(
                durationMillis = enterDuration,
                easing = OvershootInterpolator().toEasing()
            )
        ),
        exit = scaleOut(
            targetScale = 0f,
            animationSpec = tween(
                durationMillis = exitDuration,
                easing = FastOutSlowInEasing
            )
        ),
        modifier = modifier
    ) {
        content()
    }
}

@Composable
fun SlideDownEntry(
    visible: Boolean,
    modifier: Modifier = Modifier,
    enterDuration: Int = 500,
    exitDuration: Int = 300,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight / 5 }, // 👈 start above the screen
            animationSpec = tween(
                durationMillis = enterDuration,
                easing = FastOutSlowInEasing
            )
        ),
        exit = scaleOut(
            targetScale = 0f,
            animationSpec = tween(
                durationMillis = exitDuration,
                easing = FastOutSlowInEasing
            )
        ),
        modifier = modifier
    ) {
        content()
    }
}


// helper to bridge Android's OvershootInterpolator
fun OvershootInterpolator.toEasing() = Easing { x -> getInterpolation(x) }