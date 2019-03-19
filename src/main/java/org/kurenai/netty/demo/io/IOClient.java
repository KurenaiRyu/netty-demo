package org.kurenai.netty.demo.io;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * 客户端
 *
 * @author kurenai
 * @date 2019-03-19
 */
public class IOClient {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8080);
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                        Thread.sleep(2000);
                    } catch (Exception e) {
                    }
                }
            } catch (IOException e) {
            }
        }).start();
    }
}
