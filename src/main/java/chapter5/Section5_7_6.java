package chapter5;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 *  5.7.6 执行注册操作与获得SelectionKey对象
 * @author kate
 * @create 2019/7/4
 * @since 1.0.0
 */
public class Section5_7_6 {
  @Test
  public void server() throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    // 必须设置为非阻塞，不然会出现阻塞异常
    serverSocketChannel.configureBlocking(false);
    ServerSocket serverSocket = serverSocketChannel.socket();
    serverSocket.bind(new InetSocketAddress("localhost",8888));

    // 核心代码开始
    Selector selector = Selector.open();
    SelectionKey key = serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
    // 核心代码-结束
    System.out.println("selector=" + selector);
    System.out.println("key=" + key);
    serverSocket.close();
    serverSocketChannel.close();

  }
}