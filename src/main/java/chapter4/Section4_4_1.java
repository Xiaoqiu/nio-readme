package chapter4;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * 4.4.1 使用UDP实现Socket通信
 * @author kate
 * @create 2019/7/4
 * @since 1.0.0
 */
public class Section4_4_1 {
  @Test
  public void server () {
    try {
      DatagramSocket socket = new DatagramSocket(8888);
      byte[] byteArray = new byte[66000];
      // 接收长度为10的数据，和客户端发送的数据长度一致
      DatagramPacket myPack = new DatagramPacket(byteArray, byteArray.length);
      socket.receive(myPack);
      socket.close();
      System.out.println("包中数据的长度：" + myPack.getLength());
      String getString = new String(myPack.getData(), 0, myPack.getLength());
      FileOutputStream fileOutputStream = new FileOutputStream("./getData.txt");
      fileOutputStream.write(getString.getBytes());
      fileOutputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Test
  public void client() {
    try {
      // 客户端发送的字节长度为10
      // 所以服务端只能取得最大10个数据
      DatagramSocket socket = new DatagramSocket();
      socket.connect(new InetSocketAddress("localhost", 8888));
      String sendString = "";
      for (int i = 0; i < 65000 ; i++) {
        sendString = sendString + "a";
      }
      sendString = sendString + "end";
      byte[] byteArray = sendString.getBytes();
      DatagramPacket myPack = new DatagramPacket(byteArray, byteArray.length);
      socket.send(myPack);
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    }
}