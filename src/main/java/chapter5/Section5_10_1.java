package chapter5;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * @author kate
 * @create 2019/7/10
 * @since 1.0.0
 */
public class Section5_10_1 {
  @Test
  public void server() throws IOException,InterruptedException {
    DatagramChannel channel = DatagramChannel.open();
    channel.configureBlocking(false);
    // 打开的DatagramChannel可以在UDP端口8888上接收数据包。
    channel.bind(new InetSocketAddress("localhost",8888));
    Selector selector = Selector.open();
    SelectionKey selectionKey = channel.register(selector,SelectionKey.OP_READ);
    boolean isRun = true;
    while (isRun) {
      selector.select();
      Set<SelectionKey> selectionKeySet = selector.selectedKeys();
      Iterator<SelectionKey> iterator = selectionKeySet.iterator();
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        if (key.isReadable()) {
          channel = (DatagramChannel) key.channel();
          ByteBuffer buffer = ByteBuffer.allocate(1000);
          channel.receive(buffer);
          System.out.println(new String(buffer.array(),0,buffer.position()));
        }
        iterator.remove();
      }
    }
    channel.close();
  }

  @Test
  public void client() throws IOException,InterruptedException {
    DatagramChannel channel = DatagramChannel.open();
    channel.configureBlocking(false);

    Selector selector = Selector.open();
    SelectionKey selectionKey = channel.register(selector,SelectionKey.OP_WRITE);
    int keyCount = selector.select();
    Set<SelectionKey> selectionKeySet = selector.selectedKeys();
    Iterator<SelectionKey> iterator = selectionKeySet.iterator();
    while (iterator.hasNext()) {
      SelectionKey key = iterator.next();
      if (key.isWritable()) {
        ByteBuffer buffer = ByteBuffer.wrap("from client".getBytes());
        channel.send(buffer,new InetSocketAddress("localhost",8888));
        channel.close();
      }
    }
    System.out.println("client end!");
  }
}

