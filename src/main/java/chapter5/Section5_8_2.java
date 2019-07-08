package chapter5;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.junit.Test;
import org.omg.PortableServer.SERVANT_RETENTION_POLICY_ID;

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
public class Section5_8_2 {
  @Test
  public void server() throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress("localhost",8888));
    serverSocketChannel.configureBlocking(false);
    Selector selector1 = Selector.open();
    SelectionKey selectionKey = serverSocketChannel.register(selector1,SelectionKey.OP_ACCEPT);
    boolean isRun = true;
    while (isRun == true) {
      int keyCount = selector1.select();
      Set<SelectionKey> set1 = selector1.keys();
      Set<SelectionKey> set2 = selector1.selectedKeys();
      System.out.println("keyCount = " + keyCount);
      System.out.println("set1 = " + set1.size());
      System.out.println("set2 = " + set2.size());
      System.out.println();
    }
    serverSocketChannel.close();
  }

  @Test
  public void client() throws IOException{
    Socket socket = new Socket("localhost",8888);
    socket.close();
  }

  /**
   * 输出：
   * keyCount= 1
   * set1= 1
   * set2= 1
   *
   * 服务端将就绪的通道事件处理了：
   * int keyCount = selector.select(); 就再次阻塞，不会死循环。
   * @throws IOException
   */
  @Test
  public void server2() throws IOException{
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress("localhost",8888));
    serverSocketChannel.configureBlocking(false);
    Selector selector = Selector.open();
    SelectionKey selectionKey = serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
    boolean isRun = true;
    while (isRun == true) {
      int keyCount = selector.select();
      Set<SelectionKey> set1 = selector.keys();
      Set<SelectionKey> set2 = selector.selectedKeys();
      System.out.println("keyCount= " + keyCount);
      System.out.println("set1= " + set1.size());
      System.out.println("set2= " + set2.size());
      System.out.println();
      Iterator<SelectionKey> iterator = set2.iterator();
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        // 使用accep()方法将事件处理掉
        channel.accept();
      }
    }
    serverSocketChannel.accept();
  }
}