package chapter5;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author kate
 * @create 2019/7/5
 * @since 1.0.0
 */
public class Section5_7_22 {
  @Test
  public void server() throws IOException,InterruptedException {
    Thread.sleep(8000);
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress("localhost",8888));
    SocketChannel socketChannel = serverSocketChannel.accept();
    socketChannel.close();
    serverSocketChannel.close();
    System.out.println("server end!");
  }

  @Test
  public void client() throws IOException {
    long beginTime = 0;
    long endTime = 0;
    SocketChannel socketChannel = SocketChannel.open();
    // SocketChannel是非阻塞模式
    socketChannel.configureBlocking(false);
    // connectResult == true: 建立连接
    // connectResult == false: 如果通道是非阻塞，连接操作正在进行
    boolean connectResult = socketChannel.connect(new InetSocketAddress("localhost",8888));
    if (connectResult == false) {
      System.out.println("connectResult == false");
      boolean s = socketChannel.finishConnect();
      System.out.println(s);
      while (!s) {
        System.out.println("一直在尝试连接");
      }
    }
    socketChannel.close();
  }
}