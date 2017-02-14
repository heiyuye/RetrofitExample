package com.example.servicelib;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.servicelib.log.LogUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liucr on 2017/2/13.
 */

public class UDPService extends Service {

    private static String ip; //服务端ip
    private static int BROADCAST_PORT = 1234;
    private static int PORT = 4444;
    private static String BROADCAST_IP = "224.0.0.1";
    private volatile boolean isRuning = true;
    InetAddress inetAddress = null;
    /*发送广播端的socket*/
    MulticastSocket multicastSocket = null;
    private static UDPService instance;
    private UDPBoardcastThread boardcastThread = null;
    private List<Service> mList = new ArrayList<Service>();
    private ExecutorService mExecutorService = null;//thread pool
    private ServerSocket server;

    public static UDPService getInstance() {
        return instance;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        boardcastThread = new UDPBoardcastThread();
        try {
            ip = getAddressIP();
            inetAddress = InetAddress.getByName(BROADCAST_IP);//多点广播地址组
            multicastSocket = new MulticastSocket(BROADCAST_PORT);//多点广播套接字
            multicastSocket.setTimeToLive(1);
            multicastSocket.joinGroup(inetAddress);//加入广播地址组

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    server = new ServerSocket(PORT);
                    mExecutorService = Executors.newCachedThreadPool();
                    Socket client = null;
                    while (true) {
                        client = server.accept();
                        //把客户端放入客户端集合中
                        if (!connectOrNot(client)) {
                            Service service = new Service(client);
                            mList.add(service);
                            LogUtil.d("content  " + "当前连接数" + mList.size());
                            mExecutorService.execute(service); //start a new thread to handle the connection
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        boardcastThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * UDP广播 建立连接使用
     */
    public class UDPBoardcastThread extends Thread {
        public UDPBoardcastThread() {
        }

        @Override
        public void run() {
            DatagramPacket dataPacket = null;
            //将本机的IP（这里可以写动态获取的IP）地址放到数据包里，其实server端接收到数据包后也能获取到发包方的IP的
            byte[] data = ip.getBytes();
            dataPacket = new DatagramPacket(data, data.length, inetAddress, BROADCAST_PORT);
            while (true) {
                if (isRuning) {
                    try {
                        multicastSocket.send(dataPacket);
                        Thread.sleep(3000);
                        System.out.println("再次发送ip地址广播:.....");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    class Service implements Runnable {
        private Socket socket;
        private BufferedReader in = null;
        private String msg = "";

        public Service(Socket socket) {
            this.socket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            try {
                while (true) {
                    if ((msg = in.readLine()) != null) {
                        //当客户端发送的信息为：exit时，关闭连接
                        if (msg.equals("exit")) {
                            mList.remove(socket);
                            in.close();
                            msg = "user:" + socket.getInetAddress()
                                    + "exit total:" + mList.size();
                            socket.close();
                            this.sendmsg();

                            break;
                            //接收客户端发过来的信息msg，然后发送给客户端。
                        } else {
                            msg = socket.getInetAddress() + "（客户发送):" + msg;
                            LogUtil.d(msg);
                            Bundle bundle = new Bundle();
                            bundle.putString("content", msg);
                            Message msg = new Message();
                            msg.setData(bundle);
                            msg.what = 0x1238;
//                            mhandler.sendMessage(msg);

                            this.sendmsg();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 循环遍历客户端集合，给每个客户端都发送信息。
         */
        public void sendmsg() {
            PrintWriter pout = null;
            try {
                if (!connectOrNot(socket)) {
                    pout = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())), true);
                    pout.println("您已经建立连接\n" + msg);
                } else {
                    pout = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())), true);
                    pout.println("服务器的响应消息" + Math.random());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean connectOrNot(Socket socket) {
        int num = mList.size();
        for (int index = 0; index < num; index++) {
            Socket mSocket = mList.get(index).socket;
            if (mSocket.getInetAddress().getHostAddress().equals(socket.getInetAddress().getHostAddress())) {
                return true;

            }
        }
        return false;

    }


    /**
     * 1.获取本机正在使用wifi IP地址
     */
    public String getAddressIP() {
        WifiManager wifimanage = (WifiManager) getSystemService(Context.WIFI_SERVICE);//获取WifiManager
        //检查wifi是否开启
        if (!wifimanage.isWifiEnabled()) {
            wifimanage.setWifiEnabled(true);
        }
        WifiInfo wifiinfo = wifimanage.getConnectionInfo();

        String ip = intToIp(wifiinfo.getIpAddress());
        return ip;
    }

    public String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 24) & 0xFF);
    }
}
