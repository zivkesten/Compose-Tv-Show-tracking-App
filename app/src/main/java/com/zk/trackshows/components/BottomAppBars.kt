package com.zk.trackshows.components

import androidx.compose.foundation.AmbientContentColor
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zk.trackshows.ui.main.NavigationItems


@Composable
fun BottomNavigationOnlySelectedLabelComponent(
    selectedNavigationItem: NavigationItems,
    setSelectedNavigationItem: (NavigationItems) -> Unit
) {
    val items = NavigationItems.values()
    // Reacting to state changes is the core behavior of Compose. We use the state composable
    // that is used for holding a state value in this composable for representing the current
    // value of the selectedIndex. Any composable that reads the value of counter will be recomposed
    // any time the value changes. This ensures that only the composables that depend on this
    // will be redraw while the rest remain unchanged. This ensures efficiency and is a
    // performance optimization. It is inspired from existing frameworks like React.
    var selectedIndex by remember { mutableStateOf(0) }
    // BottomNavigation is a component placed at the bottom of the screen that represents primary
    // destinations in your application.
    BottomNavigation() {
        items.forEach {  navigationItem ->
            // A composable typically used as part of BottomNavigation. Since BottomNavigation
            // is usually used to represent primary destinations in your application,
            // BottomNavigationItem represents a singular primary destination in your application.
            BottomNavigationItem(
                icon = {
                    // Simple composable that allows you to draw an icon on the screen. It
                    // accepts a vector asset as the icon.
                    Icon(asset = navigationItem.icon)
                },
                label = {
                    // Text is a predefined composable that does exactly what you'd expect it to -
                    // display text on the screen. It allows you to customize its appearance using the
                    // style property.
                    Text(text = stringResource(navigationItem.title))
                },
                selected = navigationItem == selectedNavigationItem,
                // Update the selected index when the BottomNavigationItem is clicked
                alwaysShowLabels = false,
                onClick = { setSelectedNavigationItem(navigationItem) },
                selectedContentColor = AmbientContentColor.current,
                unselectedContentColor = AmbientContentColor.current,
                modifier = Modifier.padding(4.dp)

            )
        }
    }
}

// Android Studio lets you preview your composable functions within the IDE itself, instead of
// needing to download the app to an Android device or emulator. This is a fantastic feature as you
// can preview all your custom components(read composable functions) from the comforts of the IDE.
// The main restriction is, the composable function must not take any parameters. If your composable
// function requires a parameter, you can simply wrap your component inside another composable
// function that doesn't take any parameters and call your composable function with the appropriate
// params. Also, don't forget to annotate it with @Preview & @Composable annotations.
//@Preview
//@Composable
//fun BottomNavigationAlwaysShowLabelComponentPreview() {
//    BottomNavigationAlwaysShowLabelComponent()
//}
//
//@Preview
//@Composable
//fun BottomNavigationOnlySelectedLabelComponentPreview() {
//    BottomNavigationOnlySelectedLabelComponent()
//}
