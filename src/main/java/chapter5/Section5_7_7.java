package chapter5;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * 5.7.7 判断注册的状态
 * @author kate
 * @create 2019/7/4
 * @since 1.0.0
 */
public class Section5_7_7 {
  @Test
  public void server() throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    // 设置为非阻塞
    serverSocketChannel.configureBlocking(false);
    ServerSocket serverSocket = serverSocketChannel.socket();
    serverSocket.bind(new InetSocketAddress("localhost",8888));
    System.out.println("A isRegistered= " + serverSocketChannel.isRegistered());
    Selector selector = Selector.open();
    SelectionKey selectionKey = serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
    System.out.println("B isRegisterted=" + serverSocketChannel.isRegistered());
    serverSocket.close();
    serverSocketChannel.close();
  }
}