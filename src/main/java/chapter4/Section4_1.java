package chapter4;

import lombok.Data;
import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author kate
 * @create 2019/7/2
 * @since 1.0.0
 */
public class Section4_1 {

  /**
   * 验证ServerSocket类中的accept()方法具有阻塞性
   */
  @Test
  public void test2_server() {
    try {
      ServerSocket serverSocket = new ServerSocket(8088);
      System.out.println("server阻塞开始= " + System.currentTimeMillis());
      serverSocket.accept();
      System.out.println("server阻塞结束= " + System.currentTimeMillis());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void test2_client() {
    try {
      System.out.println("client连接准备= " + System.currentTimeMillis());
      // 参数: 服务器地址（域名），服务器端口
      // 如果是域名，会使用DNS服务转换为IP再访问服务器端。
      Socket socket = new Socket("localhost",8088);
      System.out.println("client连接结束= " + System.currentTimeMillis());
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 使用Socket类作为客户端来连接www.csdn.net网站
   */
  @Test
  public void test1 () throws IOException {
    Socket socket = null;
    try {
      socket = new Socket("www.csdn.net",80);
      // 改为不存在的域名，会连接失败
      //socket = new Socket("www.csdn111111111.net",80);
      System.out.println("socket连接成功");
    } catch (IOException e) {
      System.out.println("socket连接失败");
      e.printStackTrace();
    } finally {
      socket.close();
    }
  }

  /***
   * 创建一个web服务器
   */
  @Test
  public void test1_server() throws IOException{
    ServerSocket serverSocket = new ServerSocket(8081);
    System.out.println("server阻塞开始= " + System.currentTimeMillis());


    Socket socket = serverSocket.accept();
    System.out.println("server阻塞结束= " + System.currentTimeMillis());
    InputStream inputStream = socket.getInputStream();
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    String getString = "";
    while (!"".equals(getString = bufferedReader.readLine())) {
      System.out.println(getString);
    }
    OutputStream outputStream = socket.getOutputStream();
    outputStream.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
    outputStream.write("<html><body><a href='http://www.baidu.com'> i am baidu.com welcome you</a></body></html>".getBytes());
    outputStream.flush();

   inputStream.close();
   outputStream.close();
   socket.close();
    serverSocket.close();
  }

  /**
   * 4.1.2 验证Socket中的InputStream类的read()方法也具有阻塞特性
   * @throws IOException
   */
  @Test
  public void test3_server() {
    try {
      byte[] byteArray = new byte[1024];
      ServerSocket serverSocket = new ServerSocket(8088);
      System.out.println("accept begin: " + System.currentTimeMillis());
      // 呈现阻塞效果
      Socket socket = serverSocket.accept();
      System.out.println("accept end: " + System.currentTimeMillis());
      InputStream inputStream = socket.getInputStream();
      System.out.println("read begin: " + System.currentTimeMillis());
      // 呈现阻塞效果, 客户端一直没有发送数据，就一直等待，知道客户端关闭后，read方法阻塞停止。
      int readLength = inputStream.read(byteArray);
      System.out.println("read end: " + System.currentTimeMillis());
      inputStream.close();
      socket.close();
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 4.1.2 验证Socket中的InputStream类的read()方法也具有阻塞特性
   */
  @Test
  public void test3_client() {
    try {
      System.out.println("socket begin " + System.currentTimeMillis());
      Socket socket = new Socket("localhost", 8088);
      System.out.println("socket end " + System.currentTimeMillis());
      Thread.sleep(Integer.MAX_VALUE);
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 4.1.3客户端向服务端传递字符串
   */
  @Test
  public void test4_server() {
    try {
       char[] charArray = new char[3];
       ServerSocket serverSocket = new ServerSocket(8088);
      System.out.println("accept begin " + System.currentTimeMillis());
      Socket socket = serverSocket.accept();
      System.out.println("accept end " + System.currentTimeMillis());
      InputStream inputStream = socket.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
      System.out.println("read begin: " + System.currentTimeMillis());
      // 呈现阻塞效果, 客户端一直没有发送数据，就一直等待，知道客户端关闭后，read方法阻塞停止。
      int readLength = inputStreamReader.read(charArray);
      while (readLength != -1 ) {
        String newString = new String(charArray,0,readLength);
        System.out.println(newString);
        readLength = inputStreamReader.read(charArray);
      }
      System.out.println("read end: " + System.currentTimeMillis());
      inputStreamReader.close();
      inputStream.close();
      socket.close();
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 4.1.3客户端向服务端传递字符串
   */
  @Test
  public void test4_client() {
    try {
      System.out.println("socket begin " + System.currentTimeMillis());
      Socket socket = new Socket("localhost", 8088);
      System.out.println("socket end " + System.currentTimeMillis());
      Thread.sleep(3000);
      OutputStream outputStream = socket.getOutputStream();
      outputStream.write("我是外星人".getBytes());
      outputStream.close();
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 4.1.4服务端向客户端传递字符串
   */
  @Test
  public void test4_1_server() {
    try {
      char[] charArray = new char[3];
      ServerSocket serverSocket = new ServerSocket(8088);
      System.out.println("server 阻塞开始= " + System.currentTimeMillis());
      Socket socket = serverSocket.accept();
      System.out.println("server 阻塞结束=" + System.currentTimeMillis());

      OutputStream outputStream = socket.getOutputStream();
      outputStream.write("我是黄晓秋，我来自server端！".getBytes());

      outputStream.close();
      socket.close();
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 4.1.3服务端向客户端传递字符串
   */
  @Test
  public void test4_1_client() {
    try {
      System.out.println("socket begin " + System.currentTimeMillis());
      Socket socket = new Socket("localhost", 8088);
      char[] charArray = new char[3];
      InputStream inputStream = socket.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
      int readLength = inputStreamReader.read(charArray);
      while (readLength != -1) {
        System.out.println( new String(charArray,0,readLength));
        readLength = inputStreamReader.read(charArray);
      }
      System.out.println();
      inputStream.close();
      socket.close();
      System.out.println("socket end " + System.currentTimeMillis());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 4.1.5 允许多次调用write()方法进行写入操作
   *
   *
   */
  @Test
  public void test5_server() {
    try {
      char[] charBuffer = new char[15];
      ServerSocket serverSocket = new ServerSocket(8088);
      System.out.println("server阻塞开始=" + System.currentTimeMillis());
      Socket socket = serverSocket.accept();
      System.out.println("server阻塞结束=" + System.currentTimeMillis());

      InputStream inputStream = socket.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
      int readLength = inputStreamReader.read(charBuffer);
      while (readLength != -1) {
        System.out.println(new String(charBuffer,0,readLength) + " while " + System.currentTimeMillis());
        readLength = inputStreamReader.read(charBuffer);
      }
      inputStream.close();
      socket.close();
      serverSocket.close();
      System.out.println("server端运行结束=" + System.currentTimeMillis());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 4.1.5 允许多次调用write()方法进行写入操作
   * write()方法允许多次被调用，没执行一次就代表传递一次数据
   */
  @Test
  public void test5_client() {
      try {
        System.out.println("client 连接准备 " + System.currentTimeMillis());
        Socket socket = new Socket("localhost", 8088);
        System.out.println("client 连接结束 " + System.currentTimeMillis());
        Thread.sleep(3000);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("我是外星人1".getBytes());
        Thread.sleep(3000);
        outputStream.write("我是外星人2".getBytes());
        Thread.sleep(3000);
        outputStream.write("我是外星人3".getBytes());
        Thread.sleep(3000);
        outputStream.write("我是外星人4".getBytes());
        Thread.sleep(3000);
        outputStream.write("我是外星人5".getBytes());

        System.out.println("client close begin= " + System.currentTimeMillis());
        outputStream.close(); // 表示客户端不再传数据，服务端的循环结束
        socket.close();
        System.out.println("client close end= " + System.currentTimeMillis());

      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
  }

  /**
   *
   */
  @Test
  public void doubleSayString_server() {

  }

  /**
   * 客户端向服务端传递PNG图片文件，练习使用socket传递字节数据
   */
  @Test
  public void beginTransFile_server(){
      try {
        byte[] byteArray = new byte[2048];
        ServerSocket serverSocket = new ServerSocket(8088);
        Socket socket = serverSocket.accept();

        InputStream inputStream = socket.getInputStream();
        int readLength = inputStream.read(byteArray);

        FileOutputStream pngOutputStream = new FileOutputStream(new File("./newqq.png"));
        while (readLength != -1) {
          pngOutputStream.write(byteArray,0,readLength);
          readLength = inputStream.read(byteArray);
        }
        pngOutputStream.close();
        inputStream.close();
        socket.close();
        serverSocket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
  }

  /**
   * 客户端向服务端传递PNG图片文件，练习使用socket传递字节数据
   */
  @Test
  public void beginTransFile_client(){
    try {
      String pngFile = "./qq.png";
      FileInputStream pngStream = new FileInputStream(pngFile);
      byte[] byteArray = new byte[2014];
      System.out.println("socket begin " + System.currentTimeMillis());
      Socket socket = new Socket("localhost",8088);
      System.out.println("socket end " + System.currentTimeMillis());

      OutputStream outputStream = socket.getOutputStream();
      int readLength = pngStream.read(byteArray);
      while (readLength != -1) {
        outputStream.write(byteArray);
        readLength = pngStream.read(byteArray);
      }
      outputStream.close();
      pngStream.close();
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 4.1.9 TCP连接的3次"握手"过程
   */
  @Test
  public void test2_1_server(){
    try {
      ServerSocket serverSocket = new ServerSocket(8088);
      System.out.println("server 阻塞开始= " +System.currentTimeMillis());
      serverSocket.accept();
      System.out.println("server 阻塞结束= " +System.currentTimeMillis());
      Thread.sleep(Integer.MAX_VALUE);
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }
  /**
   * 4.1.9 TCP连接的3次"握手"过程
   * (ip.src==127.0.0.1 and tcp.port=8088) or (ip.dst==127.0.0.1 and tcp.port==8088)
   */
  @Test
  public void test2_1_client() {
    try {
      System.out.println("client连接准备= " + System.currentTimeMillis());
      Socket socket = new Socket("localhost",8088);
      System.out.println("client连接结束= " + System.currentTimeMillis());
      OutputStream outputStream = socket.getOutputStream();
      outputStream.write("111".getBytes());
      outputStream.write("11111".getBytes());
      outputStream.write("1111111111".getBytes());
      Thread.sleep(500000000);
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 4.1.13 结合多线程Thread实现通信
   * 客户端每发起一次新的请求，就把这个请求交给新创建的线程来执行这次业务。
   */
  @Test
  public void socket_thread_server() throws IOException {
    ServerSocket serverSocket = new ServerSocket(8888);
    int runTag = 1;
    while (runTag == 1) {
      Socket socket = serverSocket.accept();
      BeginThread beginThread = new BeginThread(socket);
      beginThread.start();
    }
    serverSocket.close();
  }
  class BeginThread extends Thread {
    private Socket socket;

    public BeginThread(Socket socket) {
      super();
      this.socket = socket;
    }

    @Override
    public void run() {
      try {
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        char[] charArray = new char[1000];
        int readLength = inputStreamReader.read(charArray);
        while (readLength != -1) {
          System.out.println(Thread.currentThread().getName() + " " + new String(charArray,0,readLength));
          readLength = inputStreamReader.read(charArray);
        }
        inputStreamReader.close();
        inputStream.close();
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void socket_thread_client() throws IOException{
    Socket socket = new Socket("localhost",8888);
    OutputStream outputStream = socket.getOutputStream();
    outputStream.write("我是中国人".getBytes());
    outputStream.close();
    socket.close();
  }

  class ReadRunnable implements Runnable {
    private Socket socket;

    public ReadRunnable(Socket socket) {
      super();
      this.socket = socket;
    }

    @Override
    public void run() {
      try {
        InputStream inputStream = socket.getInputStream();
        byte[] byteArray = new byte[100];
        int readLength = inputStream.read(byteArray);
        while (readLength != -1) {
          System.out.println( Thread.currentThread().getName() + " " + new String(byteArray,0,readLength));
          readLength = inputStream.read(byteArray);
        }
        inputStream.close();
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  class Server {
    private ServerSocket serverSocket;
    private Executor pool;

    public Server(int port, int poolSize) {
      try {
        this.serverSocket = new ServerSocket(port);
        this.pool = Executors.newFixedThreadPool(poolSize);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public void startService() {
      try {
        for (;;) {
          Socket socket = serverSocket.accept();
          pool.execute(new ReadRunnable(socket));
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void socker_thread_server2() {
    Server server = new Server(8888,10000);
    server.startService();
  }

  // 4.1.14 服务端与客户端互传对象以及IO流顺序问题
  @Test
  public void test14_server()throws IOException, ClassNotFoundException {
    ServerSocket serverSocket = new ServerSocket(8888);
    Socket socket = serverSocket.accept();
    InputStream inputStream = socket.getInputStream();
    OutputStream outputStream = socket.getOutputStream();

    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

    for (int i = 0; i < 5; i++) {
      Userinfo userinfo = (Userinfo) objectInputStream.readObject();
      System.out.println("在服务端打印" + (i + 1) + "：" + userinfo.toString());

      Userinfo newUserinfo = new Userinfo();
      newUserinfo.setId(i + 1);
      newUserinfo.setUsername("serverUsername" + (i + 1));
      newUserinfo.setPassword("serverPassword" + (i + 1));
      objectOutputStream.writeObject(newUserinfo);
    }
    objectOutputStream.close();
    objectInputStream.close();

    outputStream.close();
    inputStream.close();

    socket.close();
    serverSocket.close();

  }

  @Test
  public void test14_client()throws IOException, ClassNotFoundException {
    Socket socket = new Socket("localhost",8888);
    InputStream inputStream = socket.getInputStream();
    OutputStream outputStream = socket.getOutputStream();

    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

    for (int i = 0; i < 5; i++) {
      Userinfo newUserinfo = new Userinfo();
      newUserinfo.setId(i + 1);
      newUserinfo.setUsername("clientUsername" + (i + 1));
      newUserinfo.setPassword("clientPassword" + (i + 1));

      objectOutputStream.writeObject(newUserinfo);

      Userinfo userinfo = (Userinfo)objectInputStream.readObject();
      System.out.println("在客户端打印" + (i + 1) + ": " + userinfo.toString());
    }

    objectOutputStream.close();
    objectInputStream.close();

    outputStream.close();
    inputStream.close();

    socket.close();
  }

}

