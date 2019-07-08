package chapter2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author kate
 * @create 2019/7/1
 * @since 1.0.0
 */
public class Test13_5_2 {
  public static void main(String[] args) throws IOException, InterruptedException{
    Thread t = new Thread() {
      @Override
      public void run() {
        try {
          RandomAccessFile fileA = new RandomAccessFile("./a.txt", "rw");
          FileChannel fileChannelA = fileA.getChannel();
          System.out.println("B begin");
          fileChannelA.lock(0, 2, false);
          System.out.println("B end");
          fileChannelA.close();
          fileA.close();
        }catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };
    t.start();
    Thread.sleep(2000);
    t.interrupt();
  }
}