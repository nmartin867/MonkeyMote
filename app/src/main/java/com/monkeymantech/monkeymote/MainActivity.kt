package com.monkeymantech.monkeymote

import android.content.Context
import android.net.wifi.WifiManager
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

        fun test(){
            val wifi = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val mCastLock = wifi.createMulticastLock("multicastLock")
            mCastLock.setReferenceCounted(true)
            mCastLock.acquire()
        }
    }
}