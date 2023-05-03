package ru.sb066coder.diplonet.presentation.util

import android.text.Editable
import android.text.TextWatcher

class EmptyFieldErrorCanceler(
    private val fieldName: String, private val callback: (String) -> Unit
) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        callback(fieldName)
    }

    override fun afterTextChanged(s: Editable?) {}

}