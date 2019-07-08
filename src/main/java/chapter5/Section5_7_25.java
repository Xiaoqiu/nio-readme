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
 * 5.7.25 传输大文件
 * @author kate
 * @create 2019/7/5
 * @since 1.0.0
 */
public class Section5_7_25 {
  @Test
  public void server() throws IOException {
    ServerSocketChannel channel = ServerSocketChannel.open();
    channel.configureBlocking(false);
    channel.bind(new InetSocketAddress("localhost",8088));

    Selector selector = Selector.open();
    channel.register(selector, SelectionKey.OP_ACCEPT);
    boolean isRun = true;
    while (isRun == true) {
      selector.select();
      Set<SelectionKey> set = selector.selectedKeys();
      Iterator<SelectionKey> iterator = set.iterator();
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        iterator.remove();
        if (key.isAcceptable()) {

        }
      }
    }
  }
}