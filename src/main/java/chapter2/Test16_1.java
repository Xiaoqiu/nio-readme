package chapter2;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * a.txt文件内容：abcdefg
 * @author kate
 * @create 2019/6/28
 * @since 1.0.0
 */
public class Test16_1 {
  public static void main(String[] args) throws IOException,InterruptedException{
    File file = new File("./a.txt");
    RandomAccessFile fileA = new RandomAccessFile(file,"rw");
    FileChannel fileChannelA = fileA.getChannel();
    MappedByteBuffer buffer = fileChannelA.map(FileChannel.MapMode.READ_ONLY,0,5);
    System.out.println((char)buffer.get() + " position= " + buffer.position());
    System.out.println((char)buffer.get() + " position= " + buffer.position());
    System.out.println((char)buffer.get() + " position= " + buffer.position());
    System.out.println((char)buffer.get() + " position= " + buffer.position());
    System.out.println((char)buffer.get() + " position= " + buffer.position());
    System.out.println();
    buffer = fileChannelA.map(FileChannel.MapMode.READ_ONLY,2,2);
    System.out.println((char)buffer.get() + " position= " + buffer.position());
    System.out.println((char)buffer.get() + " position= " + buffer.position());
    Thread.sleep(500);
    System.out.println();
    //
    System.out.println((char)buffer.get() + "position= " + buffer.position());
    fileA.close();
    fileChannelA.close();

  }
}