package chapter5;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author kate
 * @create 2019/7/8
 * @since 1.0.0
 */
public class Section5_8_6 {
  @Test
  public void server() throws IOException,InterruptedException {
    ServerSocketChannel serverSocketChannel1 = ServerSocketChannel.open();
    serverSocketChannel1.bind(new InetSocketAddress("localhost",7777));
    serverSocketChannel1.configureBlocking(false);

    ServerSocketChannel serverSocketChannel2 = ServerSocketChannel.open();
    serverSocketChannel2.bind(new InetSocketAddress("localhost",8888));
    serverSocketChannel2.configureBlocking(false);

    ServerSocketChannel serverSocketChannel3 = ServerSocketChannel.open();
    serverSocketChannel3.bind(new InetSocketAddress("localhost",9999));
    serverSocketChannel3.configureBlocking(false);

    Selector selector1 = Selector.open();

    SelectionKey selectionKey1 = serverSocketChannel1.register(selector1,SelectionKey.OP_ACCEPT);
    SelectionKey selectionKey2 = serverSocketChannel2.register(selector1,SelectionKey.OP_ACCEPT);
    SelectionKey selectionKey3 = serverSocketChannel3.register(selector1,SelectionKey.OP_ACCEPT);

    boolean isRun = true;
    while (isRun) {
      // 代表有多少channel处于就绪了
      int keyCount = selector1.select();
      // 获取此selector中已经注册(可能已经cancelled但尚未deregister)的所有选择键.此集合不能被外部修改.
      Set<SelectionKey> set1 = selector1.keys();
      // 即前一次操作期间,已经准备就绪的通道所对应的选择键.此集合为keys的子集
      // 返回此选择器当前已选择键集合.此集合不能被add,但可以remove操作.
      Set<SelectionKey> set2 = selector1.selectedKeys();
      System.out.println("keyCount=" + keyCount);
      System.out.println("set1=" + set1.size());
      System.out.println("set2=" + set2.size());
      System.out.println();

      Iterator<SelectionKey> iterator = set2.iterator();
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        serverSocketChannel.accept();
      }
      Thread.sleep(10000);
    }
    serverSocketChannel1.close();
    serverSocketChannel2.close();
    serverSocketChannel3.close();
  }


  @Test
  public void client1() throws IOException{
    Socket socket = new Socket("localhost",7777);
    socket.close();
  }



  @Test
  public void client2() throws IOException{
    Socket socket = new Socket("localhost",8888);
    socket.close();

    Socket socket2 = new Socket("localhost",9999);
    socket2.close();
  }
}