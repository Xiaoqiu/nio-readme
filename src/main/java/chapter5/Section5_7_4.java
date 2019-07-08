package chapter5;

import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author kate
 * @create 2019/7/4
 * @since 1.0.0
 */
public class Section5_7_4 {
  @Test
  public void test_server4() throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress("localhost",8888));
    SocketChannel socketChannel = serverSocketChannel.accept();
    ByteBuffer byteBuffer = ByteBuffer.allocate(2);
    int readLength = socketChannel.read(byteBuffer);
    while (readLength != -1) {
      System.out.println(new String(byteBuffer.array()));
      // position = 0
      byteBuffer.flip();
      readLength = socketChannel.read(byteBuffer);
    }
    socketChannel.close();
    serverSocketChannel.close();
  }

  @Test
  public void client() throws IOException {
    Socket socket = new Socket();
    socket.connect(new InetSocketAddress("localhost",8888));
    OutputStream outputStream = socket.getOutputStream();
    outputStream.write("hello".getBytes());
    outputStream.close();
    socket.close();
  }

}