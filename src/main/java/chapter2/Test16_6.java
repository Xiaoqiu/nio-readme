package chapter2;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author kate
 * @create 2019/6/28
 * @since 1.0.0
 */
public class Test16_6 {
  public static void main(String[] args) throws IOException, InterruptedException {
    File file = new File("./a.txt");
    RandomAccessFile fileA = new RandomAccessFile(file, "rw");
    FileChannel fileChannelA = fileA.getChannel();
    MappedByteBuffer buffer = fileChannelA.map(FileChannel.MapMode.READ_WRITE,0,100);
    long beginTime = System.currentTimeMillis();
    for (int i = 0; i < 100; i++) {
      buffer.put("a".getBytes());
      buffer.force();
    }
    long endTime = System.currentTimeMillis();
    System.out.println(endTime - beginTime);
    fileChannelA.close();
    fileA.close();
  }
}