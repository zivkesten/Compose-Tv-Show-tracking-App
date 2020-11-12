package com.zk.trackshows.components

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

const val base_url_for_images = "https://image.tmdb.org/t/p/w500"

@Composable
fun NetworkImageComponentPicasso(url: String?, modifier: Modifier) {
    // Source code inspired from - https://kotlinlang.slack.com/archives/CJLTWPH7S/p1573002081371500.
    // Made some minor changes to the code Leland posted.
    var image by remember { mutableStateOf<ImageAsset?>(null) }
    var drawable by remember { mutableStateOf<Drawable?>(null) }
    val imagePath = base_url_for_images + url
    onCommit(imagePath) {
        val picasso = Picasso.get()
        val target = object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                // TODO(lmr): we could use the drawable below
                drawable = placeHolderDrawable
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                drawable = errorDrawable
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                image = bitmap?.asImageAsset()
            }
        }
        picasso
                .load(imagePath)
                .into(target)

        onDispose {
            image = null
            drawable = null
            picasso.cancelRequest(target)
        }
    }

    val theImage = image
    val theDrawable = drawable
    if (theImage != null) {
        // Image is a pre-defined composable that lays out and draws a given [ImageAsset].
        Image(asset = theImage, modifier = modifier)
    } else if (theDrawable != null) {
        Canvas(modifier = modifier) {
            drawIntoCanvas { canvas ->
                theDrawable.draw(canvas.nativeCanvas)
            }
        }
    }
}