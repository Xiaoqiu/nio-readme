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
public class Test13_19 {
  public static void main (String[] args) throws IOException, InterruptedException {
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    fileChannelA.lock(6,2,false);
    fileChannelA.write(ByteBuffer.wrap("1".getBytes()));
    fileChannelA.write(ByteBuffer.wrap("2".getBytes()));
    fileChannelA.write(ByteBuffer.wrap("3".getBytes()));
    fileChannelA.write(ByteBuffer.wrap("4".getBytes()));
    fileChannelA.write(ByteBuffer.wrap("5".getBytes()));
    fileChannelA.write(ByteBuffer.wrap("6".getBytes()));
    // 此行出现异常
    fileChannelA.write(ByteBuffer.wrap("7".getBytes()));
    fileChannelA.write(ByteBuffer.wrap("8".getBytes()));

  }
}