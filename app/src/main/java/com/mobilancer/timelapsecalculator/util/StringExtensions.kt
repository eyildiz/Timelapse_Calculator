package com.mobilancer.timelapsecalculator.util

import android.text.Spannable
import android.text.Spanned
import androidx.core.text.HtmlCompat

fun String.fromHtml() : Spanned {
    return HtmlCompat.fromHtml(this,HtmlCompat.FROM_HTML_MODE_LEGACY)
}