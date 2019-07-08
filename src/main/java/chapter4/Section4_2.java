package chapter4;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author kate
 * @create 2019/7/3
 * @since 1.0.0
 */
public class Section4_2 {
  // 4.2.1 accept方法和Timeout

  /**
   * 设置超时时间是4s，accept()方法阻塞4s后抛出异常。
   */
  @Test
  public void test1_server() {
    long beginTime = 0;
    long endTime;
    long catchTime;
    try {
      ServerSocket serverSocket = new ServerSocket(8000);
      System.out.println(serverSocket.getSoTimeout());
      serverSocket.setSoTimeout(4000);
      System.out.println(serverSocket.getSoTimeout());
      System.out.println();

      beginTime = System.currentTimeMillis();
      System.out.println("beginTime = " + beginTime);
      serverSocket.accept();
      endTime = System.currentTimeMillis();
      System.out.println("endTime=" + endTime);
      System.out.println("beginTime - endTime = " + (beginTime - endTime));

    } catch (IOException e) {
     // e.printStackTrace();
       catchTime = System.currentTimeMillis();
       System.out.println("catchTime - beginTime = " + (catchTime - beginTime));

    }
  }

  @Test
  public void test1_client() {
    try {
      Socket socket = new Socket("localhost",8000);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // 4.2.2 构造方法的backlog参数含义

  /**
   * 验证构造方法public ServerSocket(int port, int backlog)
   * backlog: 允许客户端连接请求的个数, 每个连接过来没有accpet()处理的，都会保存到一个操作系统的队列，
   * backlog就是这个队列的容量；
   * 服务端每次accept()，就会从队列中取出一个元素。
   * 客户端每次创建一个Socket对象，就会放入一个元素到队列
   *
   */
  @Test
  public void test2_server() {
   try {
     ServerSocket serverSocket = new ServerSocket(8088,50);
     while (true){

     }
   } catch (IOException e) {
     e.printStackTrace();
   }

  }

  @Test
  public void test2_client(){
   try {
     for (int i = 1; i <= 200; i++) {
       Socket socket = new Socket("localhost",8088);
       System.out.println("socket" + i);
     }
   } catch (IOException e) {
     e.printStackTrace();
   }
  }

  // 4.2.4 构造方法ServerSocket(int port, int backlog, InetAddress bindAddr)方法的使用

  /**
   * bindAddr: 如果一台计算机有两块网卡，每个网卡有不同的IP地址，就可以指定
   * 接收某个IP地址的请求。
   */

  // 4.2.5 绑定到指定的Socket地址


  // 1 服务端是实现端口不允许被复用
  @Test
  public void test3() throws IOException,InterruptedException {
      final Thread server = new Thread() {
        @Override
        public void run() {
          try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(false);
            serverSocket.bind(new InetSocketAddress("localhost",8888));
            Socket socket = serverSocket.accept();
            Thread.sleep(5000);
            // 服务端首先主动关闭连接
            socket.close();
            serverSocket.close();
          } catch (IOException e) {
            e.printStackTrace();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      };
      server.start();
      Thread.sleep(500);
      Thread client = new Thread() {
        @Override
        public void run() {
          try {
            Socket socket = new Socket("localhost",8888);
            Thread.sleep(3000);
            socket.close();
          } catch (IOException e) {
            e.printStackTrace();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      };
  }

  @Test
  public void test4() {
    try {
      ServerSocket serverSocket = new ServerSocket(8888);
      System.out.println("accept begin");
      Socket socket = serverSocket.accept();
      System.out.println("accept begin");
      socket.close();
      serverSocket.close();
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}