package ru.sb066coder.diplonet.presentation

object ViewUtil {

    fun formatDate(input: String, withTime: Boolean = false): String {
        val dateTime = input.split('T', '.', ignoreCase =  false, limit =  3)
        return String.format("%s %s", dateTime[0], if (withTime) dateTime[1] else "")
    }
}