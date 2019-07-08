package chapter5;

import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;

/**
 * 5.7.1 获得ServerSocketChannel 与 ServerSocket socket对象
 * @author kate
 * @create 2019/7/4
 * @since 1.0.0
 */
public class Section5_7_1{
  @Test
  public void server() throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    ServerSocket serverSocket = serverSocketChannel.socket();
    serverSocket.bind(new InetSocketAddress("localhost",8888));
    Socket socket = serverSocket.accept();
    InputStream inputStream = socket.getInputStream();
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
    char[] charArray = new char[1024];
    int readLength = inputStreamReader.read(charArray);
    while (readLength != -1) {
      String newString = new String(charArray,0,readLength);
      System.out.println(newString);
      readLength = inputStreamReader.read(charArray);
    }
    inputStream.close();
    inputStreamReader.close();
    socket.close();
    serverSocket.close();
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
