package chapter5;

import com.sun.security.ntlm.Server;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.file.StandardOpenOption;

/**
 * 5.7.24 public static SocketChannel open() 方法已经执行了connect方法，设置SocketOption不会有效果。
 * @author kate
 * @create 2019/7/5
 * @since 1.0.0
 */
public class Section5_7_24 {
  @Test
  public void server() throws IOException {
    ServerSocket serverSocket = new ServerSocket(8088);
    Socket socket = serverSocket.accept();
    InputStream inputStream = socket.getInputStream();
    byte[] byteArray = new byte[1024];
    int readLength = inputStream.read(byteArray);
    while (readLength != -1) {
      System.out.println(new String(byteArray,0,readLength));
      readLength = inputStream.read(byteArray);
    }
    //inputStream.close();
    //socket.close();
    //serverSocket.close();
  }

  //错误的客户端
  @Test
  public void client1() throws IOException{
    SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost",8088));
    //open(InetSocketAddress inet)方法已经调用了connect()方法，修改Option不会生效
    socketChannel.setOption(StandardSocketOptions.SO_RCVBUF,1234);
    socketChannel.write(ByteBuffer.wrap("我来自客户端".getBytes()));
    socketChannel.close();
  }
  //正确的客户端
  @Test
  public void client2() throws IOException{
    SocketChannel socketChannel = SocketChannel.open();
    // open()方法没有调用connect()这时候设置参数有效。
    socketChannel.setOption(StandardSocketOptions.SO_RCVBUF,1234);
    socketChannel.connect(new InetSocketAddress("localhost",8088));
    socketChannel.write(ByteBuffer.wrap("我来自客户端".getBytes()));
    socketChannel.close();
  }
}