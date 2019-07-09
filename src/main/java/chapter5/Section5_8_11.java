package chapter5;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author kate
 * @create 2019/7/9
 * @since 1.0.0
 */
public class Section5_8_11 {
  @Test
  public void test() throws IOException {
    ServerSocketChannel serverSocketChannel1 = ServerSocketChannel.open();
    serverSocketChannel1.bind(new InetSocketAddress("localhost",8888));
    serverSocketChannel1.configureBlocking(false);
    ServerSocketChannel serverSocketChannel2 = ServerSocketChannel.open();
    serverSocketChannel2.bind(new InetSocketAddress("localhost",9999));
    serverSocketChannel2.configureBlocking(false);
    ServerSocketChannel serverSocketChannel3 = ServerSocketChannel.open();
    serverSocketChannel3.bind(new InetSocketAddress("localhost",7777));
    serverSocketChannel3.configureBlocking(false);

    Selector selector = Selector.open();
    SelectionKey selectionKey1 = serverSocketChannel1.register(selector,SelectionKey.OP_ACCEPT);
    SelectionKey selectionKey2 = serverSocketChannel2.register(selector,SelectionKey.OP_ACCEPT);
    SelectionKey selectionKey3 = serverSocketChannel3.register(selector,SelectionKey.OP_ACCEPT);

    System.out.println(selectionKey1.hashCode());
    System.out.println(selectionKey2.hashCode());
    System.out.println(selectionKey3.hashCode());
    System.out.println();

    Set<SelectionKey> keySet = selector.keys();
    Iterator<SelectionKey> iterator = keySet.iterator();
    while (iterator.hasNext()) {
      SelectionKey key = iterator.next();
      System.out.println(key.hashCode());

    }
  }

}