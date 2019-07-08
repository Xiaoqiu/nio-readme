package chapter2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @author kate
 * @create 2019/7/1
 * @since 1.0.0
 */
public class Test14_2 {
  public static void main(String[] args) throws IOException,InterruptedException {
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    System.out.println("B begins");
    // 使用的是共享锁
    FileLock fileLock = fileChannelA.tryLock(0,5,true);
    System.out.println("B end 获得了锁 fileLock= " + fileLock);
    fileA.close();
    fileChannelA.close();
  }
}