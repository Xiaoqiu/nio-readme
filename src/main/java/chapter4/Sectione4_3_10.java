package chapter4;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author kate
 * @create 2019/7/4
 * @since 1.0.0
 */
public class Sectione4_3_10 {
  /**
   *101164
   *96226
   */
  @Test
  public void server() {
    try {
      ServerSocket serverSocket = new ServerSocket(8888);
      Socket socket = serverSocket.accept();
      InputStream inputStream = socket.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
      char[] charArray = new char[1024];

      int readLength = inputStreamReader.read(charArray);
      long beginTime = System.currentTimeMillis();
      while (readLength != -1) {
        System.out.println(new String(charArray,0,readLength));
        readLength =  inputStreamReader.read(charArray);
      }
      long endTime = System.currentTimeMillis();
      System.out.println(endTime - beginTime);
      socket.close();
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void client() {
    try {
      Socket socket = new Socket();
      // 打印发送缓冲区的大小
      System.out.println("A client socket.getSendBufferSize= " + socket.getSendBufferSize());
      socket.setSendBufferSize(1024 * 1024);
      System.out.println("B client socket.getSendBufferSize= " + socket.getSendBufferSize());
      socket.connect(new InetSocketAddress("localhost",8888));
      OutputStream outputStream = socket.getOutputStream();
      for (int i = 0; i < 5000000; i++) {
        outputStream.write("123456789123456789123456789".getBytes());
        System.out.println(i + 1);
      }
      outputStream.write("end!".getBytes());
      outputStream.close();
      socket.close();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}