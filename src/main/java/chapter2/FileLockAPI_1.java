package chapter2;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @author kate
 * @create 2019/6/28
 * @since 1.0.0
 */
public class FileLockAPI_1 {
  public static void main(String[] args) throws IOException {
    File file = new File("./a.txt");
    RandomAccessFile fileA = new RandomAccessFile(file,"rw");
    FileChannel fileChannelA = fileA.getChannel();
    System.out.println("fileChannelA.hasCode() = " + fileChannelA.hashCode());
    FileLock lock = fileChannelA.lock(1, 10, true);
    System.out.println("A position = " + lock.position()
        + " size= " + lock.size()
        + " isValid= " + lock.isValid()
        + " isShared= " + lock.isShared()
        + " channel().hashCode()= " + lock.channel().hashCode()
        + " acquiredBy().hashCode()= " + lock.acquiredBy().hashCode());
    lock.release();
    lock = fileChannelA.lock(1,10,false);
    System.out.println("B position = " + lock.position()
        + " size= " + lock.size()
        + " isValid= " + lock.isValid()
        + " isShared= " + lock.isShared()
        + " channel().hashCode()= " + lock.channel().hashCode()
        + " acquiredBy().hashCode()= " + lock.acquiredBy().hashCode());
    lock.close();
    fileChannelA.close();
    System.out.println("C position = " + lock.position()
        + " size= " + lock.size()
        + " isValid= " + lock.isValid()
        + " isShared= " + lock.isShared()
        + " channel().hashCode()= " + lock.channel().hashCode()
        + " acquiredBy().hashCode()= " + lock.acquiredBy().hashCode());
  }
}