package com.monkeymantech.monkeymote

import android.app.Application
import com.monkeymantech.monkeymote.modules.fragmentModule
import com.monkeymantech.monkeymote.modules.networkModule
import com.monkeymantech.monkeymote.modules.prefModule
import com.monkeymantech.monkeymote.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MonkeyMote: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@MonkeyMote)
            modules(listOf(fragmentModule, viewModelModule, prefModule, networkModule))
        }
    }
}