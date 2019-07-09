package chapter5;

import org.junit.Test;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;

/**
 * @author kate
 * @create 2019/7/9
 * @since 1.0.0
 */
public class Section5_8_10 {

  @Test
  public void test() throws IOException {
    SelectorProvider provider1 = SelectorProvider.provider();
    SelectorProvider provider2 = Selector.open().provider();

    System.out.println(provider1);
    System.out.println(provider2);
  }
}