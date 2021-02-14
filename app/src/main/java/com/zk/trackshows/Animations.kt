package com.zk.trackshows

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zk.trackshows.PulseAnimationDefinition.pulseDefinition
import com.zk.trackshows.PulseAnimationDefinition.pulsePropKey

@ExperimentalAnimationApi
@Composable
fun AnimatedBottomNavigationTransition(
    enter: EnterTransition,
    exit: ExitTransition,
    initiallyVisible: Boolean? = false,
    content: @Composable () -> Unit
) {

    AnimatedVisibility(
        visible = true,
        enter = enter,
        exit = exit,
        initiallyVisible = initiallyVisible ?: false
    ) {
        // Content that needs to appear/disappear goes here:
        content.invoke()
    }
}

@ExperimentalAnimationApi
fun bottomNavigationEnterTransitions(): EnterTransition {
    return slideInVertically(
        // Start the slide from 40 (pixels) above where the content is supposed to go, to
        // produce a parallax effect
        initialOffsetY = { -40 }
    ) + fadeIn(initialAlpha = 0.3f)
}

@ExperimentalAnimationApi
fun bottomNavigationExitTransitions(): ExitTransition {
    return slideOutVertically() + fadeOut()


}

@Composable
fun PulseAplphaPlaceHolder() {
    val colors = MaterialTheme.colors.primary

    val pulseAnim = transition(
        initState = PulseAnimationDefinition.PulseState.INITIAL,
        definition = pulseDefinition,
        toState = PulseAnimationDefinition.PulseState.FINAL,

    )

    val pulseMagnitude = pulseAnim[pulsePropKey]
    Log.d("Zivi", "pulseMagnitude $pulseMagnitude")

    Box(modifier = Modifier
        .preferredWidth(pulseMagnitude.dp)
        .preferredHeight(300.dp)
        .padding(12.dp)
        //.alpha(pulseMagnitude)
        .clip(RoundedCornerShape(12.dp))
        .background(color = Color.LightGray)
    )



}
object PulseAnimationDefinition {
    enum class PulseState {
        INITIAL, FINAL
    }

    val pulsePropKey = FloatPropKey("pulsePropKey")

    val pulseDefinition = transitionDefinition<PulseState> {
        state(PulseState.INITIAL) { this[pulsePropKey] = 170f }
        state(PulseState.FINAL) { this[pulsePropKey] = 190f }

        transition(
            PulseState.INITIAL to PulseState.FINAL
        ) {
            pulsePropKey using infiniteRepeatable(
                animation = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                ), repeatMode = RepeatMode.Reverse
            )
        }
    }
}

