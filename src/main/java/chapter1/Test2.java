package chapter1;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * @author kate
 * @create 2019/5/30
 * @since 1.0.0
 */
public class Test2 {
  public static void main(String[] args) {
    char[] charArray = new char[]{'a', 'b', 'c', 'd', 'e'};
    CharBuffer buffer = CharBuffer.wrap(charArray);
    System.out.println("capacity: " + buffer.capacity() + ";\n limit: " + buffer.limit());
    //第一个不可读写的索引为3
    buffer.limit(3);
    System.out.println("capacity: " + buffer.capacity() + ";\n limit: " + buffer.limit());
    buffer.put(0, '0');
    buffer.put(1, '1');
    buffer.put(2, '2');
    // 不可读写位置3
    buffer.put(3, '3');
    buffer.put(4, '4');
    buffer.put(5, '5');
  }

  @Test
  public void test2() {
    byte[] byteArray = new byte[]{1, 2, 3};
    ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
    System.out.println("position:" + byteBuffer.position()
        + "; limit:" + byteBuffer.limit()
        + "; capacity: " + byteBuffer.capacity()
        + "; mark: " + byteBuffer.mark());
    byteBuffer.position(2);
    byteBuffer.limit(3);
    byteBuffer.mark();
    System.out.println("position:" + byteBuffer.position()
        + "; limit:" + byteBuffer.limit()
        + "; capacity: " + byteBuffer.capacity()
        + "; mark: " + byteBuffer.mark());
    byteBuffer.clear();

    System.out.println("position:" + byteBuffer.position()
        + "; limit:" + byteBuffer.limit()
        + "; capacity: " + byteBuffer.capacity()
        + "; mark: " + byteBuffer.mark());
  }
@Test
public void test3() {
    CharBuffer byteBuffer = CharBuffer.allocate(20);
    System.out.println("position:" + byteBuffer.position()
        + "; limit:" + byteBuffer.limit()
        + "; capacity: " + byteBuffer.capacity()
        + "; mark: " + byteBuffer.mark());
  byteBuffer.put("我是中国人我在深圳");
    System.out.println("position:" + byteBuffer.position()
        + "; limit:" + byteBuffer.limit()
        + "; capacity: " + byteBuffer.capacity()
        + "; mark: " + byteBuffer.mark());
  // 读取缓冲区数据需要做两部：
  // 设置最后一个位置为当前位置
  // 设置当前位置为0
  byteBuffer.limit(byteBuffer.position());
  byteBuffer.position(0);

  System.out.println("position:" + byteBuffer.position()
      + "; limit:" + byteBuffer.limit()
      + "; capacity: " + byteBuffer.capacity()
      + "; mark: " + byteBuffer.mark());

  for (int i = 0; i < byteBuffer.limit(); i++) {
    System.out.println(byteBuffer.get());
  }

  }
}