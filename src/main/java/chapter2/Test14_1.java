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
public class Test14_1 {
  public static void main(String[] args) throws IOException, InterruptedException {
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    System.out.println("A begins");
    FileLock fileLock = fileChannelA.tryLock(0,5,false);
    System.out.println("A end 获得了锁 fileLock= " + fileLock);
    Thread.sleep(Integer.MAX_VALUE);
  }
}