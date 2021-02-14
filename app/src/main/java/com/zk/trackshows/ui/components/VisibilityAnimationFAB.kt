package com.zk.trackshows.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@ExperimentalAnimationApi
@Composable
fun VisibilityAnimationFAB(bool: Boolean, action: () -> Unit) {
    var expanded by remember(bool) { mutableStateOf(false) }
    FloatingActionButton(
        onClick = {
            action.invoke()
            expanded = !expanded
        },
        modifier = Modifier
    ) {
        Row(Modifier.padding(start = 16.dp, end = 16.dp)) {
            if (!expanded) {
                Icon(
                    imageVector = Icons.Default.Add,
                    Modifier.align(Alignment.CenterVertically)
                )
            }
            AnimatedVisibility(
                expanded,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(modifier = Modifier.padding(start = 8.dp), text = "Followed")
            }
        }
    }

}