package chapter2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author kate
 * @create 2019/6/26
 * @since 1.0.0
 */
public class Test12_3 {
  public static void main(String[] args) throws IOException {
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    RandomAccessFile fileB = new RandomAccessFile("./b.txt","rw");

    FileChannel fileChannelA = fileA.getChannel();
    FileChannel fileChannelB = fileB.getChannel();

    fileChannelB.position(2);
    long readLength = fileChannelA.transferFrom(fileChannelB,1,200);
    System.out.println(readLength);

    fileChannelA.close();
    fileChannelB.close();
    fileA.close();
    fileB.close();
  }
}