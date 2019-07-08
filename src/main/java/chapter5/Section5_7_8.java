package chapter5;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * 5.7.8 将通道设置为非阻塞模式再注册到选择器
 * @author kate
 * @create 2019/7/4
 * @since 1.0.0
 */
public class Section5_7_8 {
  @Test
  public void server() throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    // 必须设置为非阻塞，才能注册到选择器
    serverSocketChannel.configureBlocking(false);
    serverSocketChannel.bind(new InetSocketAddress("localhost",8888));
    Selector selector = Selector.open();
    System.out.println("selector=" + selector);
    System.out.println("A serverSocketChannel.isRegistered()= " + serverSocketChannel.isRegistered());
    // 表示这个通道接受socket连接
    SelectionKey selectionKey = serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
    System.out.println("B serverSocketChannel.isRegistered()= " + serverSocketChannel.isRegistered());
    serverSocketChannel.close();
  }
}