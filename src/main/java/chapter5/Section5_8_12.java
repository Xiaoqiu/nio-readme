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
 * @create 2019/7/9
 * @since 1.0.0
 */
public class Section5_8_12 {
  @Test
  public void test() throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress("localhost",8888));
    serverSocketChannel.configureBlocking(false);

    Selector selector = Selector.open();
    SelectionKey selectionKey = serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
    boolean isRun = true;
    while (isRun) {
      System.out.println("while (isRun) " + System.currentTimeMillis());
      int keyCount = selector.select(5000);
      Set<SelectionKey> selectionKeySet = selector.selectedKeys();
      Iterator<SelectionKey> iterator = selectionKeySet.iterator();
      while (iterator.hasNext()) {
        System.out.println("进入while");
        SelectionKey key = iterator.next();
        if (key.isAcceptable()) {
          ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) key.channel();
          Socket socket = serverSocketChannel.socket().accept();
          socket.close();
        }
        iterator.remove();
      }
    }
    serverSocketChannel.close();
  }
}