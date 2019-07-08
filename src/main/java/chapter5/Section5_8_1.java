package chapter5;

import org.junit.Test;
import sun.nio.ch.SelectionKeyImpl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @author kate
 * @create 2019/7/6
 * @since 1.0.0
 */
public class Section5_8_1 {
  // 验证select()方法的阻塞性
  @Test
  public void server() throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    System.out.println("1");
    serverSocketChannel.bind(new InetSocketAddress("localhost",8888));
    System.out.println("2");
    serverSocketChannel.configureBlocking(false);
    System.out.println("3");
    Selector selector1 = Selector.open();
    System.out.println("4");
    SelectionKey selectionKey1 = serverSocketChannel.register(selector1, SelectionKey.OP_ACCEPT);
    System.out.println("5");
    // 这一句会阻塞，直到有一个就绪通道。这里检测的是ACCEPT连接通道。
    int keyCount = selector1.select();
    System.out.println("6 keyCount= " + keyCount);
    serverSocketChannel.close();
    System.out.println("7 end!");
  }

  @Test
  public void client() throws IOException{
    Socket socket = new Socket("localhost",8888);
    socket.close();
  }
}