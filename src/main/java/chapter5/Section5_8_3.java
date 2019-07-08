package chapter5;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author kate
 * @create 2019/7/8
 * @since 1.0.0
 */
public class Section5_8_3 {

  @Test
  public void server() throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress("localhost",7777));
    serverSocketChannel.configureBlocking(false);

    ServerSocketChannel serverSocketChannel12 = ServerSocketChannel.open();
    serverSocketChannel12.bind(new InetSocketAddress("localhost",8888));
    serverSocketChannel12.configureBlocking(false);

    Selector selector1 = Selector.open();
    SelectionKey selectionKey1 = serverSocketChannel.register(selector1,SelectionKey.OP_ACCEPT);
    SelectionKey selectionKey2 = serverSocketChannel12.register(selector1,SelectionKey.OP_ACCEPT);

    boolean isRun = true;
    while (isRun == true) {
      int keyCount = selector1.select();
      Set<SelectionKey> set1 = selector1.keys();
      Set<SelectionKey> set2 = selector1.selectedKeys();
      System.out.println("keyCount=" + keyCount);
      System.out.println("set1=" + set1.size());
      System.out.println("set2=" + set2.size());
      Iterator<SelectionKey> iterator = set2.iterator();
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) key.channel();
        serverSocketChannel1.accept();
//        if (socketChannel == null) {
//          System.out.println("");
//        }
        InetSocketAddress ipAddress = (InetSocketAddress) serverSocketChannel.getLocalAddress();
        System.out.println(ipAddress.getPort() + "被客户端连接了！");
        System.out.println();
        iterator.remove();// 删除已经处理的SelectionKey
      }
    }
    serverSocketChannel.close();
    serverSocketChannel12.close();
  }

  @Test
  public void client1() throws IOException{
    Socket socket = new Socket("localhost",8888);
    socket.close();
  }
  @Test
  public void client2() throws IOException{
    Socket socket = new Socket("localhost",7777);
    socket.close();
  }

}