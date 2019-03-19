package org.kurenai.netty.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author kurenai
 * @date 2019-03-21
 */
public class NIOServer {
    public static void main(String[] args) {
        try {
            //创建一个服socket并打开
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //监听绑定8090端口
            serverSocketChannel.socket().bind(new InetSocketAddress(8090));
            //设置为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            while (true) {
                //获取请求连接
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    ByteBuffer buf1 = ByteBuffer.allocate(1024);
                    socketChannel.read(buf1);
                    buf1.flip();
                    if (buf1.hasRemaining())
                        System.out.println(">>>服务端收到数据：" + new String(buf1.array()));
                    buf1.clear();
                    //构造返回的报文，分为头部和主体，实际情况可以构造复杂的报文协议，这里只演示，不做特殊设计。
                    ByteBuffer header = ByteBuffer.allocate(6);
                    header.put("[head]".getBytes());
                    ByteBuffer body = ByteBuffer.allocate(1024);
                    body.put("i am body!".getBytes());
                    header.flip();
                    body.flip();
                    ByteBuffer[] bufferArray = {header, body};
                    socketChannel.write(bufferArray);

                    socketChannel.close();
                } else {
                    Thread.sleep(1000);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
