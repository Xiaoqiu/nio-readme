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
public class Section5_8_14 {
private static Selector selector;
  @Test
  public void test() throws IOException,InterruptedException {
    Thread thread = new Thread() {
      @Override
      public void run() {
        try {
          Thread.sleep(2000);
          selector.wakeup();
          Set<SelectionKey> set1 = selector.keys();
          Set<SelectionKey> set2 = selector.selectedKeys();
          System.out.println("执行wakeup()方法之后的selector的信息： " );
          System.out.println("set1= " + set1.size());
          System.out.println("set2= " + set2.size());
        }catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };
    thread.start();

    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress("localhost",8888));
    serverSocketChannel.configureBlocking(false);

    selector = Selector.open();
    SelectionKey selectionKey = serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
    int keyCount = selector.select();
    Set<SelectionKey> selectionKeySet = selector.selectedKeys();
    Iterator<SelectionKey> iterator = selectionKeySet.iterator();

    while (iterator.hasNext()) {
      SelectionKey key = iterator.next();
      if (key.isAcceptable()) {
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        Socket socket = channel.socket().accept();
        socket.close();
      }
    }
    serverSocketChannel.close();
    System.out.println("main end!");

  }

  @Test
  public void client() throws IOException {
    Socket socket = new Socket("localhost",8888);
    socket.getOutputStream().write("12345".getBytes());
    socket.close();
  }
}