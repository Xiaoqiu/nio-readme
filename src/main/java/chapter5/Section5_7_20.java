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
public class Section5_7_20 {
  @Test
  public void server() throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress("localhost",8888));
    SocketChannel socketChannel = serverSocketChannel.accept();
    socketChannel.close();
    serverSocketChannel.close();
    System.out.println("server end!");
  }


  @Test
  public void client() {
    long beginTime = 0;
    long endTime = 0;
    boolean connectResult = false;
    try {
      // SocketChannel是阻塞模式
      // 在发生错误或者连接到目标之前，connect()方法一直是阻塞的。
      SocketChannel socketChannel = SocketChannel.open();
      beginTime = System.currentTimeMillis();
      connectResult = socketChannel.connect(new InetSocketAddress("localhost",8888));
      endTime = System.currentTimeMillis();
      System.out.println("正常连接耗时： " + (endTime - beginTime) + " connect-Result=" + connectResult);
      socketChannel.close();
    } catch (IOException e) {
      e.printStackTrace();
      endTime = System.currentTimeMillis();
      System.out.println("异常连接耗时： " + (endTime - beginTime) + " connect-Result=" + connectResult);
    }
  }

  @Test
  public void client2() {
    long beginTime = 0;
    long endTime = 0;
    boolean connectResult = false;
    try {
      // 在发生错误或者连接到目标之前，connect()方法一直是阻塞的。
      SocketChannel socketChannel = SocketChannel.open();
      // SocketChannel是非阻塞模式
      socketChannel.configureBlocking(false);
      beginTime = System.currentTimeMillis();
      connectResult = socketChannel.connect(new InetSocketAddress("localhost",8888));
      endTime = System.currentTimeMillis();
      System.out.println("正常连接耗时： " + (endTime - beginTime) + " connect-Result=" + connectResult);
      socketChannel.close();
    } catch (IOException e) {
      e.printStackTrace();
      endTime = System.currentTimeMillis();
      System.out.println("异常连接耗时： " + (endTime - beginTime) + " connect-Result=" + connectResult);
    }
  }

}