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
public class Test13_11 {
  public static void main(String[] args) throws IOException, InterruptedException{
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    ByteBuffer byteBuffer = ByteBuffer.allocate(10);
    fileChannelA.read(byteBuffer);
    byteBuffer.rewind();
    for (int i = 0; i < byteBuffer.limit(); i++) {
      System.out.println((char)byteBuffer.get());
    }

    //
    fileChannelA.write(ByteBuffer.wrap(("write").getBytes()));

  }
}