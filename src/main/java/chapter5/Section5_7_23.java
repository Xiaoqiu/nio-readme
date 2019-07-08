package chapter5;

import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author kate
 * @create 2019/7/5
 * @since 1.0.0
 */
public class Section5_7_23 {

  @Test
  public void test() throws IOException{
    RandomAccessFile file = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannel = file.getChannel();
    ByteBuffer byteBuffer =  ByteBuffer.allocate(100);
    for (int i = 0; i < 1214126; i++) {
      byteBuffer = ByteBuffer.wrap("a".getBytes());
      byteBuffer.rewind();
      fileChannel.write(byteBuffer);
    }
    byteBuffer = ByteBuffer.wrap("end".getBytes());
    fileChannel.write(byteBuffer);
   fileChannel.close();
  }

  /**
   * 使用IO多路复用发送数据到客户端
   */
  @Test
  public void server() throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    SocketChannel socketChannel = null;
    // 非阻塞通道
    serverSocketChannel.configureBlocking(false);
    serverSocketChannel.bind(new InetSocketAddress("localhost",8888));

    Selector selector = Selector.open();
    // 把serverSocketChannel注册到选择器
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    boolean isRun = true;
    //
    while (isRun == true) {
      selector.select();
      // 获取一批SelectionKey
      Set<SelectionKey> set = selector.selectedKeys();
      Iterator<SelectionKey> iterator = set.iterator();
      //遍历这批SelectionKey
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        // 遍历后删除key
        iterator.remove();
        // 判断key的类别: 接收连接
        if (key.isAcceptable()) {
          // 获取一个连接socketChannel
          socketChannel = serverSocketChannel.accept();
          // 配置这个socketChannel为非阻塞
          socketChannel.configureBlocking(false);
          // 把这个获得的连接socketChannel再注册到选择器，操作类型是写入
          socketChannel.register(selector,SelectionKey.OP_WRITE);
        }
        // 判断key的类别：写入
        if (key.isWritable()) {
          RandomAccessFile file = new RandomAccessFile("./a.txt","rw");
          //
          System.out.println("file.length() = " + file.length());
          FileChannel fileChannel = file.getChannel();
          // 读取此通道文件中给的字节，将其写入目标通道中。
          fileChannel.transferTo(0, file.length(),socketChannel);
          fileChannel.close();
          file.close();
          socketChannel.close();
        }
      }
    }
    serverSocketChannel.close();
  }

  /**
   * 使用IO多路复用接收服务端数据
   * @throws Exception
   */
  @Test
  public void client() throws Exception{
    SocketChannel socketChannel = SocketChannel.open();
    // 配置为非阻塞通道
    socketChannel.configureBlocking(false);
    // 非阻塞，不马上链接，调用finishConnect()方法后确定连接是否建立。
    socketChannel.connect(new InetSocketAddress("localhost",8888));
    Selector selector = Selector.open();
    // 注册这个通道到选择器
    socketChannel.register(selector,SelectionKey.OP_CONNECT);
    boolean isRun = true;
    // 循环获取
    while (isRun == true) {
      System.out.println("begin selector");
      if (socketChannel.isOpen()) {
        // 获取一批key
        selector.select();
        System.out.println(" end selector");
        Set<SelectionKey> set = selector.selectedKeys();
        Iterator<SelectionKey> iterator = set.iterator();
        // 遍历每个key
        while (iterator.hasNext()) {
          SelectionKey key = iterator.next();
          // 操作了这个通道后，删除该通道的key
          iterator.remove();
          if (key.isConnectable()) {
            // 非阻塞通道，finishConnect()要等到连接建立返回true或者连接失败抛出异常，一直返回false;
            // 如果是false,就是连接中，继续调用获取连接结果。直到连接成功返回true,或者连接失败抛出异常
            while (!socketChannel.finishConnect()) {
            }
            // 连接成功后，把通道注册到选择器，管道操作类型变为可读
            socketChannel.register(selector,SelectionKey.OP_READ);
          }
          // 遍历到可读的key的通道
          if (key.isReadable()) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(20000);
            int readLength = socketChannel.read(byteBuffer);
            byteBuffer.flip();
            long count = 0;
            while (readLength != -1) {
              count = count + readLength;
              readLength = socketChannel.read(byteBuffer);
              System.out.println("count= " + count + " readLength= " + readLength);
              byteBuffer.clear();
            }
            System.out.println("读取结束");
            socketChannel.close();
          }
        }
      } else {
        break;
      }
    }
  }
}