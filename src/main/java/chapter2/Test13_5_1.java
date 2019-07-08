package chapter2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author kate
 * @create 2019/7/1
 * @since 1.0.0
 */
public class Test13_5_1 {
  public static void main(String[] args) throws IOException, InterruptedException{
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    System.out.println("A begin");
    fileChannelA.lock(0, 2, false);
    System.out.println("A end");
    Thread.sleep(20000);
    fileChannelA.close();
    fileA.close();
  }
}