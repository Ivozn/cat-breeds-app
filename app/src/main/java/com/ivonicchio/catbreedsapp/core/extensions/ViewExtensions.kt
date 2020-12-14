package com.ivonicchio.catbreedsapp.core.extensions

import android.view.View
import android.view.animation.DecelerateInterpolator

fun View.fadeInAnimation(){
    this.alpha = 0f
    this.visibility = View.VISIBLE
    this.animate().alpha(1f).setDuration(1500)
        .setInterpolator(DecelerateInterpolator()).start()
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}