package com.example.clientlib.client;

import android.os.Message;

import com.example.clientlib.listener.ScanDeviceListener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by liucr on 2017/2/13.
 */

public class UDPClientThread extends Thread {

    static final String BROADCAST_IP = "224.0.0.1";
    //监听的端口号
    static final int BROADCAST_PORT = 1234;
    private InetAddress inetAddress;
    //多点广播套接字
    MulticastSocket multicastSocket = null;
    private ScanDeviceListener scanDeviceListener;

    //服务端的局域网IP,或动态获得IP.
    private String ip;

    //创建一个 InetAddress .要使用多点广播,需要让一个数据报标有一组目标主机地址,
    //其思想便是设置一组特殊网络地址作为多点广播地址,第一个多点广播地址都被看作是一个组,
    //当客户端需要发送.接收广播信息时,加入该组就可以了.IP协议为多点广播提供这批特殊的IP地址,

    public UDPClientThread() {

    }

    public void scanDevice(ScanDeviceListener scanDeviceListener) {
        this.scanDeviceListener = scanDeviceListener;
        this.start();
    }

    @Override
    public void run() {
        try {

            /**
             * 1.实例化MulticastSocket对象,并指定端口
             * 2.加入广播地址，MulticastSocket使用public void joinGroup(InetAddress mcastaddr)
             * 3.开始接收广播
             * 4.关闭广播
             */

            multicastSocket = new MulticastSocket(BROADCAST_PORT);
            inetAddress = InetAddress.getByName(BROADCAST_IP);
            multicastSocket.joinGroup(inetAddress);
            byte buf[] = new byte[1024];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);


            while (true) {
                multicastSocket.receive(dp);
                Thread.sleep(3000);
                ip = new String(buf, 0, dp.getLength());
                multicastSocket.leaveGroup(inetAddress);

                if (scanDeviceListener != null) {
                    Device device = new Device();
                    device.setIp(ip);
                    device.setPort(BROADCAST_PORT);
                    scanDeviceListener.onGotByScan(device);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (scanDeviceListener != null) {
                scanDeviceListener.onError(e);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            if (scanDeviceListener != null) {
                scanDeviceListener.onError(e);
            }
        }
    }

    public void stopScan() {
        if (multicastSocket != null) {
            multicastSocket.close();
        }
    }

}
