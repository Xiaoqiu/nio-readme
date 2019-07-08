package chapter5;

import com.sun.xml.internal.ws.fault.ServerSOAPFaultException;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Set;

/**
 * @author kate
 * @create 2019/7/8
 * @since 1.0.0
 */
public class Section5_8_5 {
  @Test
  public void test1() {
    SimpleDateFormat format = new SimpleDateFormat();
    format = new SimpleDateFormat();
    format = new SimpleDateFormat();
    format = new SimpleDateFormat();
  }

  @Test
  public void test2() throws IOException {
    ServerSocketChannel serverSocketChannel1 = ServerSocketChannel.open();
    serverSocketChannel1.bind(new InetSocketAddress("localhost",7777));
    serverSocketChannel1.configureBlocking(false);
    ServerSocketChannel serverSocketChannel2 = ServerSocketChannel.open();
    serverSocketChannel2.bind(new InetSocketAddress("localhost",8888));
    serverSocketChannel2.configureBlocking(false);

    Selector selector1 = Selector.open();
    SelectionKey selectionKey1 = serverSocketChannel1.register(selector1,SelectionKey.OP_ACCEPT);
    SelectionKey selectionKey2 = serverSocketChannel2.register(selector1,SelectionKey.OP_ACCEPT);

    boolean isRun = true;
    while (isRun == true) {
      int keyCount = selector1.select();
      Set<SelectionKey> set1 = selector1.keys();
      Set<SelectionKey> set2 = selector1.selectedKeys();
      System.out.println("keyCount = " + keyCount);
      System.out.println("set1= " + set1.size());
      System.out.println("set2= " + set2.size());
      System.out.println();
      Iterator<SelectionKey> iterator = set2.iterator();
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
        serverSocketChannel.accept();
      }
    }
    serverSocketChannel1.close();
  }
}