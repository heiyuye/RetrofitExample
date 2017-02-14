package com.example.liucr.retrofitexample.ui.activities;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.clientlib.ClientHelper;
import com.example.clientlib.client.Device;
import com.example.clientlib.listener.ScanDeviceListener;
import com.example.liucr.retrofitexample.R;

import java.util.List;

import butterknife.Bind;

/**
 * Created by liucr on 2017/2/14.
 */

public class DeviceDebugActivity extends BaseActivity {

    @Bind(R.id.act_device_debug_layout)
    LinearLayout linearLayout;

    @Override
    protected int initLayoutResID() {
        return R.layout.act_device_debug;
    }

    @Override
    protected void initData() {
        newButton(new Device("1111",10));
        ClientHelper.getInstance().scanDevice(new ScanDeviceListener() {
            @Override
            public void onGotByScan(Device device) {
                newButton(device);
                ClientHelper.getInstance().stopScanDevice();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    protected void initView() {

    }

    public void newButton(final Device device) {
        final Button button = new Button(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(device != null){
                    device.sendData(button.getId() + "");
                }
            }
        });
        button.setText(device.getIp());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                linearLayout.addView(button);
            }
        });
    }

}
