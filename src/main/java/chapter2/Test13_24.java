package chapter2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author kate
 * @create 2019/7/1
 * @since 1.0.0
 */
public class Test13_24 {
  public static void main(String[] args) throws IOException, InterruptedException{
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    fileChannelA.lock(0,Long.MAX_VALUE,false);
    Thread.sleep(Integer.MAX_VALUE);
  }
}