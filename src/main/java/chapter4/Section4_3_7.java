package chapter4;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 4.3.7 开启半读与半写状态
 * @author kate
 * @create 2019/7/4
 * @since 1.0.0
 */
public class Section4_3_7 {
  // 屏蔽输入流
  @Test
  public void server() throws IOException {
    ServerSocket serverSocket = new ServerSocket(8088);
    Socket socket = serverSocket.accept();
    InputStream inputStream = socket.getInputStream();
    System.out.println("A = " + inputStream.available());
    byte[] byteArray = new byte[2];
    int readLength = inputStream.read(byteArray);
    System.out.println("server取得的数据： " + new String(byteArray,0,readLength));
    socket.shutdownInput();// 屏蔽InputStream,到达流的结尾
    System.out.println("B = " + inputStream.available());
    readLength = inputStream.read(byteArray); // -1
    System.out.println("readLength = " + readLength);
    // 再次使用getInputStream方法出现异常：
    socket.getInputStream();
    socket.close();
    serverSocket.close();
  }

  @Test
  public void client() throws IOException{
    Socket socket = new Socket("localhost", 8088);
    OutputStream out = socket.getOutputStream();
    out.write("abcdefg".getBytes());
    socket.close();
  }

  // 屏蔽输出流

}