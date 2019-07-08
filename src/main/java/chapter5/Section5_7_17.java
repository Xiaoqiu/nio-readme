package chapter5;

import org.junit.Test;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;

/**
 * @author kate
 * @create 2019/7/5
 * @since 1.0.0
 */
public class Section5_7_17 {
  @Test
  public void test() throws IOException {
    SelectorProvider provider1 = SelectorProvider.provider();
    System.out.println(provider1);
    ServerSocketChannel serverSocketChannel = null;
    serverSocketChannel = ServerSocketChannel.open();
    SelectorProvider provider2 = serverSocketChannel.provider();
    System.out.println(provider2);
    serverSocketChannel.close();
  }
}