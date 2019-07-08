package chapter5;

import org.junit.Test;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author kate
 * @create 2019/7/5
 * @since 1.0.0
 */
public class Section5_7_19 {
  @Test
  public void server() throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    SocketChannel socketChannel = SocketChannel.open();

    int value1 = serverSocketChannel.validOps();
    int value2 = socketChannel.validOps();

    System.out.println("value1 : " + value1);
    System.out.println("value2 : " + value2);
    System.out.println();
    // 输出为0表示支持这个选项
    // ServerSocketChannel只支持OP_ACCEPT
    System.out.println(SelectionKey.OP_ACCEPT & ~serverSocketChannel.validOps());
    System.out.println(SelectionKey.OP_CONNECT & ~serverSocketChannel.validOps());
    System.out.println(SelectionKey.OP_READ & ~serverSocketChannel.validOps());
    System.out.println(SelectionKey.OP_WRITE & ~serverSocketChannel.validOps());
    System.out.println();

    // SocketChannel支持OP_CONNECT，OP_READ，OP_WRITE
    System.out.println(SelectionKey.OP_ACCEPT & ~socketChannel.validOps());
    System.out.println(SelectionKey.OP_CONNECT & ~socketChannel.validOps());
    System.out.println(SelectionKey.OP_READ & ~socketChannel.validOps());
    System.out.println(SelectionKey.OP_WRITE & ~socketChannel.validOps());
    socketChannel.close();
    serverSocketChannel.close();

  }
}