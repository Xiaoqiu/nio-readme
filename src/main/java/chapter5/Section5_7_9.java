package chapter5;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * 5.7.9 使用configureBlocking(false)方法解决异常
 * @author kate
 * @create 2019/7/5
 * @since 1.0.0
 */
public class Section5_7_9 {
  @Test
  public void server() throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress("localhost",8888));

    System.out.println("A isBlocking = " + serverSocketChannel.isBlocking());
    // 调整通道阻塞模式
    // 在任意时刻调用，方法返回后，通道阻塞模式才受影响。
    serverSocketChannel.configureBlocking(false);
    System.out.println("B isBlocking = " + serverSocketChannel.isBlocking());

    Selector selector = Selector.open();
    System.out.println("selector=" + selector);
    System.out.println("A serverSocketChannel.isRegistered()= " + serverSocketChannel.isRegistered());
    // 监听通道接受socket连接
    SelectionKey selectionKey = serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
    System.out.println("B serverSocketChannel.isRegistered()= " + serverSocketChannel.isRegistered());

    serverSocketChannel.close();

  }
}