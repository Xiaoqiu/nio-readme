package chapter2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * 获得独占锁
 * @author kate
 * @create 2019/7/1
 * @since 1.0.0
 */
public class Test13_23 {
  public static void main(String[] args) throws IOException, InterruptedException{
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    System.out.println("Test13_23 begin " + System.currentTimeMillis());
    fileChannelA.lock(0,Long.MAX_VALUE,false);
    System.out.println("Test13_23 end 拿到锁 " + System.currentTimeMillis());
  }

}