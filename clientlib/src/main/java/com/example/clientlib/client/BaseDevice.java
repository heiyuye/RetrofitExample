package com.example.clientlib.client;

import com.example.clientlib.log.LogUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by liucr on 2017/2/13.
 */

public class BaseDevice extends Thread {

    private int port;

    private String ip;


    private StringBuffer strContent = new StringBuffer();
    private Socket socket = null;
    private String content = "";

    public BaseDevice() {

    }

    public BaseDevice(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void sendData(final String msg) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
//                Thread.sleep(500);
                    InetAddress serverAddr = InetAddress.getByName(ip);
                    Socket s = new Socket(serverAddr, port);
                    s.setSoTimeout(2000);
                    // outgoing stream redirect to socket
                    OutputStream out = s.getOutputStream();
                    // 注意第二个参数据为true将会自动flush，否则需要需要手动操作out.flush()
                    PrintWriter output = new PrintWriter(out, true);
                    output.println(msg);
                    s.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void run() {
        super.run();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream())), true);

            while (true) {
                if (!socket.isClosed()) {
                    if (socket.isConnected()) {
                        if (!socket.isInputShutdown()) {
                            if ((content = in.readLine()) != null) {
                                content += "\n";
                                LogUtil.d("content>> " + content);
//                                mHandler.sendMessage(mHandler.obtainMessage());
                            } else {

                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
