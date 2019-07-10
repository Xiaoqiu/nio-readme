package chapter5;

import com.sun.org.apache.bcel.internal.generic.Select;
import com.sun.tools.classfile.Opcode;
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
public class Section5_10_2 {

  @Test
  public void udp_client() throws IOException,InterruptedException {
    DatagramChannel channel = DatagramChannel.open();
    channel.configureBlocking(false);
    // 可以将DatagramChannel“连接”到网络中的特定地址的。由于UDP是无连接的，
    // 连接到特定地址并不会像TCP通道那样创建一个真正的连接。而是锁住DatagramChannel ，让其只能从特定地址收发数据。
    channel.connect(new InetSocketAddress("localhost",8888));
    Selector selector = Selector.open();
    SelectionKey selectionKey = channel.register(selector,SelectionKey.OP_WRITE);
    int keyCount = selector.select();
    Set<SelectionKey> selectionKeySet = selector.selectedKeys();
    Iterator<SelectionKey> iterator = selectionKeySet.iterator();
    while (iterator.hasNext()) {
      SelectionKey key = iterator.next();
      if (key.isWritable()) {
        ByteBuffer buffer = ByteBuffer.wrap("i am from client!".getBytes());
        channel.write(buffer);
        channel.close();
      }
    }
    System.out.println("client end!");
  }
}