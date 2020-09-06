package com.ivonicchio.catbreedsapp.core.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

fun Fragment.configFragmentToolbar(toolbar: Toolbar) {
    val parentActivity = activity
    if (parentActivity is AppCompatActivity) {
        parentActivity.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            toolbar.setNavigationOnClickListener{
                onBackPressed()
            }
        }
    }
}