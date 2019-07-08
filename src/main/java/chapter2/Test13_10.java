package chapter2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author kate
 * @create 2019/7/1
 * @since 1.0.0
 */
public class Test13_10 {
  public static void main(String[] args) throws IOException, InterruptedException{
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    fileChannelA.lock(1, 2, true);

    // 共享锁自己能读
    ByteBuffer byteBuffer = ByteBuffer.allocate(10);
    fileChannelA.read(byteBuffer);
    byteBuffer.rewind();
    for (int i = 0; i < byteBuffer.limit(); i++) {
      System.out.println((char)byteBuffer.get());
    }
// 共享锁自己不能写
    fileChannelA.write(ByteBuffer.wrap(("write by myself").getBytes()));

    Thread.sleep(Integer.MAX_VALUE);
  }
}