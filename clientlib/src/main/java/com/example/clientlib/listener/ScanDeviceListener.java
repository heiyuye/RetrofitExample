package com.example.clientlib.listener;

import com.example.clientlib.client.Device;

/**
 * Created by liucr on 2017/2/14.
 */

public interface ScanDeviceListener {

    void onGotByScan(Device device);

    void onError(Exception e);
}
