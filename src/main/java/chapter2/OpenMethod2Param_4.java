package chapter2;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author kate
 * @create 2019/6/27
 * @since 1.0.0
 */
public class OpenMethod2Param_4 {
  public static void main (String[] args) throws IOException {
    File file = new File("./aaa.txt");
    Path path = file.toPath();
    FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ);
    byte[] byteArray = new byte[(int) file.length()];
    ByteBuffer buffer = ByteBuffer.wrap(byteArray);
    fileChannel.read(buffer);
    fileChannel.close();

    byteArray = buffer.array();
    for (int i = 0; i < byteArray.length; i++) {
      System.out.print((char)byteArray[i]);
    }
  }
}