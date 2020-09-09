package com.monkeymantech.monkeymote.ui.main

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.monkeymantech.monkeymote.R
import org.koin.android.viewmodel.ext.android.viewModel
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.NetworkInterface

class MainFragment : Fragment() {

    val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    suspend fun findServiceProviders() {
        val wifi = activity!!.getApplicationContext().getSystemService(Context.WIFI_SERVICE) as WifiManager
        val multicastLock = wifi!!.createMulticastLock("multicastLock")
        multicastLock.setReferenceCounted(true)
        multicastLock.acquire()

        val udpSocket = DatagramSocket()
        udpSocket.broadcast = true

        val sendData = "\"M-SEARCH * HTTP/1.1\\r\\nHOST:239.255.255.250:1900\\r\\nMAN:\\\"ssdp:discover\\\"\\r\\nST:ssdp:all\\r\\nMX:3\\r\\n\\r\\n\"".toByteArray()
        val sendPacket = DatagramPacket(sendData, sendData.size, InetAddress.getByName("255.255.255.255"), 8888)
        udpSocket.send(sendPacket)

        val interfaces = NetworkInterface.getNetworkInterfaces()
        while(interfaces.hasMoreElements()) {
            val netInterface = interfaces.nextElement()
            if(netInterface.isLoopback || !netInterface.isUp)
                continue
            netInterface.interfaceAddresses.forEach { addr ->
                addr.broadcast?.let { broadcastAddr ->
                    val packet = DatagramPacket(sendData, sendData.size, broadcastAddr, 8888)
                    udpSocket.send(packet)
                }
            }
        }

    }
}