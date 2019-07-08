package chapter2;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * a.txt : abcde
 * @author kate
 * @create 2019/6/28
 * @since 1.0.0
 */
public class Test16_3 {
  public static void main(String[] args) throws IOException, InterruptedException {
    File file = new File("./a.txt");
    RandomAccessFile fileA = new RandomAccessFile(file,"rw");
    FileChannel fileChannelA = fileA.getChannel();
    MappedByteBuffer buffer = fileChannelA.map(FileChannel.MapMode.READ_WRITE,0,5);
    System.out.println((char)buffer.get() + " position= " + buffer.position());
    System.out.println((char)buffer.get() + " position= " + buffer.position());
    System.out.println((char)buffer.get() + " position= " + buffer.position());
    System.out.println((char)buffer.get() + " position= " + buffer.position());
    System.out.println((char)buffer.get() + " position= " + buffer.position());
    buffer.position(0);
    buffer.put((byte)'o');
    buffer.put((byte)'p');
    buffer.put((byte)'q');
    buffer.put((byte)'r');
    buffer.put((byte)'s');
    fileChannelA.close();
    fileA.close();

  }
}