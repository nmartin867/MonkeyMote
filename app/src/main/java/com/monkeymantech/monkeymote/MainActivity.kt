package com.monkeymantech.monkeymote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.monkeymantech.monkeymote.ui.main.MainFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val mainFragment: MainFragment by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, mainFragment)
                    .commitNow()
        }
    }
}