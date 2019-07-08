package chapter2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author kate
 * @create 2019/6/28
 * @since 1.0.0
 */
public class Test15_1 {
  public static void main(String[] args) throws IOException, InterruptedException {
    File file = new File("./a.txt");
    if (file.exists() == false) {
      file.createNewFile();
    } else {
      file.delete();
    }
    FileOutputStream fileA = new FileOutputStream(file);
    FileChannel fileChannelA = fileA.getChannel();
    long beginTime = System.currentTimeMillis();
    for (int i = 0; i < 5000; i++) {
      fileChannelA.write(ByteBuffer.wrap(("abcd").getBytes()));
      fileChannelA.force(false);
    }
    long endTime = System.currentTimeMillis();
    System.out.println(endTime - beginTime);

    fileChannelA.close();
    fileA.close();

  }
}