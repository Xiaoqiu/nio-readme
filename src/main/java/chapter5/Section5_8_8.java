package chapter5;

import org.junit.Test;

import java.io.IOException;
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
 * @create 2019/7/9
 * @since 1.0.0
 */
public class Section5_8_8 {
  @Test
  public void server() throws IOException,InterruptedException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress("localhost",8888));
    serverSocketChannel.configureBlocking(false);
    Selector selector = Selector.open();
    SelectionKey selectionKey = serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
    boolean isRun = true;
    while (isRun) {
      int keyCount = selector.select();
      Set<SelectionKey> set1 = selector.keys();
      Set<SelectionKey> set2 = selector.selectedKeys();
      System.out.println("keyCountA = " + keyCount);
      System.out.println("set1= " + set1.size());
      System.out.println("set2= " + set2.size());
      System.out.println();
      Iterator<SelectionKey> iterator = set2.iterator();
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        SelectionKey key2 = socketChannel.register(selector,SelectionKey.OP_READ);
        System.out.println("key2.isReadable()= " + ((SelectionKey.OP_READ & ~key2.interestOps()) == 0));
        System.out.println("key2.isWritable()= " + ((SelectionKey.OP_WRITE & ~key2.interestOps()) == 0));
        SelectionKey key3 = socketChannel.register(selector,SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        System.out.println("key3.isReadable()= " + ((SelectionKey.OP_READ & ~key2.interestOps()) == 0));
        System.out.println("key3.isWritable()= " + ((SelectionKey.OP_WRITE & ~key2.interestOps()) == 0));

        System.out.println("keyCountB = " + keyCount);
        System.out.println("set1= " + set1.size());
        System.out.println("set2= " + set2.size());
        System.out.println("key2==key3结果： " + (key2 == key3));
      }
      Thread.sleep(Integer.MAX_VALUE);
    }
    serverSocketChannel.close();
  }

  @Test
  public void client() throws IOException {
    Socket socket = new Socket("localhost",8888);
    socket.getOutputStream().write("12345".getBytes());
    socket.close();
  }

}