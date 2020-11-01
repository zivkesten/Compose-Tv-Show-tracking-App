package com.zk.trackshows

import androidx.compose.animation.*
import androidx.compose.runtime.Composable

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

