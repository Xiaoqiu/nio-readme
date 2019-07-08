package chapter2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author kate
 * @create 2019/7/1
 * @since 1.0.0
 */
public class Test13_3 {
  private static FileOutputStream fileA;
  private static FileChannel fileChannelA;

  public static void main(String[] args) throws IOException, InterruptedException {
     fileA = new FileOutputStream("./a.txt");
     fileChannelA = fileA.getChannel();

    Thread a = new Thread() {
      @Override
      public void run () {
        try {
          fileChannelA.lock(1,2,false);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };
    Thread b = new Thread() {
      @Override
      public void run () {
        try {
          fileChannelA.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };

    a.start();
    Thread.sleep(1);
    b.start();
    Thread.sleep(1000);

    fileA.close();
    fileChannelA.close();
  }
}