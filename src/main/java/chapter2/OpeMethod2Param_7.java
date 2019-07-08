package chapter2;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author kate
 * @create 2019/6/27
 * @since 1.0.0
 */
public class OpeMethod2Param_7 {
  public static void main(String[] args) throws IOException, InterruptedException {
    File file = new File("./aaa.txt");
    Path path = file.toPath();
    FileChannel fileChannel = FileChannel.open(path,StandardOpenOption.DELETE_ON_CLOSE,
        StandardOpenOption.CREATE,StandardOpenOption.WRITE);
    Thread.sleep(20000);
    fileChannel.close();

  }
}