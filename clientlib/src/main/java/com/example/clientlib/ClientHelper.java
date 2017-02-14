package com.example.clientlib;

import com.example.clientlib.client.UDPClientThread;
import com.example.clientlib.listener.ScanDeviceListener;

/**
 * Created by liucr on 2017/2/14.
 */

public class ClientHelper {

    private static ClientHelper instance;

    private UDPClientThread udpClientThread;

    public static ClientHelper getInstance() {
        if (instance == null) {
            synchronized (ClientHelper.class) {
                if (instance == null) {
                    instance = new ClientHelper();
                }
            }
        }
        return instance;
    }

    public void scanDevice(ScanDeviceListener scanDeviceListener) {
        udpClientThread = new UDPClientThread();
        udpClientThread.scanDevice(scanDeviceListener);
    }

    public void stopScanDevice() {
        if (udpClientThread != null) {
            udpClientThread.stopScan();
        }
    }
}
