package ru.sb066coder.diplonet.application

import android.app.Application
import ru.sb066coder.diplonet.auth.AppAuth

class DiploNetApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppAuth.initApp(this)
    }
}