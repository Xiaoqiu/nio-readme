package chapter5;

import com.sun.org.apache.bcel.internal.generic.Select;
import com.sun.security.ntlm.Server;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author kate
 * @create 2019/7/8
 * @since 1.0.0
 */
public class Section5_8_7 {
  @Test
  public void server() throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress("localhost",8888));
    serverSocketChannel.configureBlocking(false);
    Selector selector = Selector.open();

    SelectionKey selectionKey = serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
    boolean isRun = true;
    while (isRun) {
      int keyCount = selector.select();
      Set<SelectionKey> set = selector.selectedKeys();
      Iterator<SelectionKey> iterator = set.iterator();
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        if (key.isAcceptable()) {
          ServerSocketChannel channel = (ServerSocketChannel) key.channel();
          ServerSocket serverSocket = channel.socket();
          Socket socket = serverSocket.accept();
          InputStream inputStream = socket.getInputStream();
          byte[] byteArray = new byte[1000];
          int readLength = inputStream.read(byteArray);
          while (readLength != -1) {
            System.out.println(new String(byteArray,0,readLength));
            readLength = inputStream.read(byteArray);
          }
          inputStream.close();
          socket.close();
          iterator.remove();
          }
        }
      }
      serverSocketChannel.close();
    }


  @Test
  public void client() throws IOException{
    Socket socket = new Socket("localhost",8888);
    OutputStream outputStream = socket.getOutputStream();
    outputStream.write("i am kate, who are you!".getBytes());
    socket.close();
  }

}
