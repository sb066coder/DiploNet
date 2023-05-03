package ru.sb066coder.diplonet.presentation.util

import android.content.res.Resources

object ViewUtil {
/**
 * Extracts the date from the dateTime string. Pass true if need time to leave.
 * */
    fun formatDate(input: String, withTime: Boolean = false): String {
        val dateTime = input.split('T', '.', ignoreCase =  false, limit =  3)
        return String.format("%s %s", dateTime[0], if (withTime) dateTime[1] else "")
    }

    fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}