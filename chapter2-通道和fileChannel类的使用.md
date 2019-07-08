### 2.4.7 向通道的指定position位置写入数据

- write(ByteBuffer src, long position)

- 不修改此通道的位置

- 如果给定位置大于这个文件的当前位置，则该文件扩大以容纳新的字节。

- 验证

- ```java
  public class Test7_1 {
    public static void main(String[] args) throws IOException, InterruptedException {
      FileOutputStream fosRef = new FileOutputStream(new File("c:\\abc\\a.txt"));
      FileChannel fileChannel = fosRef.getChannel();
      try {
        ByteBuffer buffer = ByteBuffer.wrap("abcde".getBytes());
        // 向通道写入
        fileChannel.write(buffer);
        // 将当前位置置0，同时取消mark标记
        buffer.rewind();
        // 向通道写入
        fileChannel.write(buffer,2);
        System.out.println("C fileChannel.position()=" + fileChannel.position());
      } catch (IOException e) {
        e.printStackTrace();
      }
      
      fileChannel.close();
      fosRef.close();
    }
  }
  ```

- ```bash
  # 输出结果
  ababcde
  ```

- 验证

- ```java
  public class Test7_2 {
    public static void main(String[] args) throws IOException, InterruptedException {
      FileOutputStream fosRef = new FileOutputStream(new File("c:\\abc\\a.txt"));
      FileChannel fileChannel = fosRef.getChannel();
      try {
        ByteBuffer buffer1 = ByteBuffer.wrap("abcde".getBytes());
        ByteBuffer buffer2 = ByteBuffer.wrap("12345".getBytes());
        fileChannel.write(buffer1);
        buffer2.position(1);
        buffer2.limit(3);
        fileChannel.write(buffer2,2);
      } catch (IOException e) {
        e.printStackTrace();
      }
      fileChannel.close();
      fosRef.close();
    }
  }
  ```

- ```bash
  # 输出：
  ab23e
  ```

- 3.验证同步特性

- ```java
  public class Test7_3 {
    private static FileOutputStream fosRef;
    private static FileChannel fileChannel;
    public static void main(String[] args) throws IOException,InterruptedException {
      fosRef = new FileOutputStraem(new File("c:\\abc\\a.txt"));
      fileChannel = fosRef.getChannel();
      Thread thread1 = new Thread() {
        @Override
        public void run() {
          try {
            System.out.println("线程1运行");
            ByteBuffer buffer = ByteBuffer.wrap("12345".getBytes());
            fileChannel.write(buffer,0);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      };
      Thread thread2 = new Thread() {
        @Override
        public void run() {
          try {
            System.out.println("线程2运行");
            ByteBuffer buffer = ByteBuffer.wrap("67890".getBytes());
            fileChannel.write(buffer,0);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      };
      thread1.start();
      thread2.start();
   
      Thread.sleep(3000);
      fileChannel.close();
      fosRef.close();
    }
  }
  ```

- 上面的结果：哪个线程最后运行write()方法，文本里面就是哪个线程写入的数据。

- 4 验证write(ByteBuffer src, long position)方法中的position不变性。

  - 执行wirte(..)方法，不改变position的位置，操作不影响position的值。

  - ```JAVA
    public class Test7_4 {
      public static void main(String[] args) throws IOException,InterruptedException{	
        FileOutputStream fos = new FileOutputStream("c:\\abc\\abc.txt");
        FileChannel fileChannel = fos.getChannel();
        System.out.println("A position: " + fileChannel.position());
        fileChannel.position(3);
        System.out.println("B position: " + fileChannel.position());
        fileChannel.write(ByteBuffer.wrap("abcde".getBytes()),0);
        System.out.println("C position: " + fileChannel.position());  
        fileChannel.close(); 
      }
    }
    ```

  - ```bash
    # 输出结果
    A position：0
    A position：3
    A position：3
    ```

    ##

### 2.4.8 读取通道指定位置的数据

- read(ByteBuffer dst, long position) 方法是将通道的指定位置的字节序列读入给定的缓冲区的当前位置。

- 如果给定的位置大于该文件的当前大小，则不读取任何字节

- 1 验证read(….)方法的返回值的意义。

- a.txt文件的初始内容：abcde

- ```java
  public class Test8_1 {
    private static FileInputStream fisRef;
    private static FileChannel fileChannel;
    
    public static void main(String[] args) throws IOException,InterruptedException {
      fisRef = new FileInputStream(new File("c:\\abc\\a.txt"));
      fileChannel = fisRef.getChannel();
      ByteBuffer byteBuffer = ByteBuffer.allocate(2);
      int readLength = fileChannel.read(byteBuffer,2);
      System.out.println(readLength); // 读取2个字节
      //limit==capacity,同时将当前写位置置为最前端下标为0处
      byteBuffer.clear();
      
      readLength = fileChannel.read(byteBuffer,10);
      System.out.println(readLength);// 到达流的末尾为-1
      byteBuffer.clear();
      
      fileChannel.close();
      fisRef.close();
   }
  }
  ```

  ```bash
  # 运行结果：
  2
  -1
  ```

  - 2 验证read(…) 方法将字节放入ByteBuffer当前位置

  - a.txt文件的初始内容：abcde

  - ```java
    public class Test8_3 {
      private static FileInputStream fisRef;
      private static FileChannel fileChannel;
      public static void main (String[] args) throw IOException, InterruptedException{
        fisRef = new FileInputStream(new File("c:\\abc\\a.txt"));
        fileChannel = fisRef.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        byteBuffer.position(3);
        fileChannel.read(byteBuffer,2);
        byte[] getByteArray = byteBuffer.array();
        for (int i=0; i < getByteArray.length; i++) {
          if (getByteArray[i] == 0) {
            System.out.print("空格");
          } else {
            System.out.print((char)getByteArray[i]);
          }
        }
        fileChannel.close();
        fisRef.close();
      }
     
    }
    ```

  - ```bash
    # 输出：
    空格空格空格cd
    ```

  - 3 验证read(ByteBuffer dst, long position)方法有同步性

  - 4 验证read(ByteBuffer dst, long position)方法从通道读取的数据大于缓冲区容量，

    - 只能写入缓冲区大小的字节。a.txt文件内容：abcde

    - ````java
      // todo
      ````

    - 

### 2.4.9 设置和获得大小

- position(long newPosition) ： 设置此通道的文件位置，但是不更改文件的大小。
- long size(): 返回此通道关联文件的当前大小

```java
public class Test9 {
  public static void main(String[] args) throws IOException {
    ByteBuffer byteBuffer1 = ByteBuffer.wrap("abcd".getBytes());
    ByteBuffer byteBuffer2 = ByteBuffer.wrap("cde".getBytes());
    FileOutputStream fileOutputStream = new FileOutputStream(new File("c:\\abc\\newtxt.txt"));
    FileChannel fileChannel = fileOutputStream.getChannel();
    System.out.println("A position = " + fileChannel.position()
                      + " size= " + fileChannel.size());
    fileChannel.write(byteBuffer1);
    System.out.println("B position = " + fileChannel.position()
                      + " size= " + fileChannel.size());
    fileChannel.position(2);
    System.out.println("C position = " + fileChannel.position()
                      + " size= " + fileChannel.size());
    fileChannel.write(byteBuffer2);
    System.out.println("D position = " + fileChannel.position()
                      + " size= " + fileChannel.size());
    fileChannel.close();
    fileOutputStream.flush();
    fileOutputStream.close();
  }
}
```

```bash
A position = 0 size = 0
B position = 4 size = 4
C position = 2 size = 4
D position = 5 size = 5
# 生成的文本内容：
abcde
```

- 验证：将该位置设置为大于文件当前大小的值，不会改变文件的大小，

  - 试图在这个位置读取字节会返回已经到达文件末尾

  - 试图在这个位置写入字节将导致文件扩大。

  - ```java
    public class Test9_1 {
      public static void main(String[] args) throws IOException,InterruptedException {
        // 可以获取可以读写的通道
        RandomAccessFile file = new RandomAccessFile("c:\\abc\\abc.txt","rw");
        FileChannel fileChannel = file.getChannel();
        System.out.println("A position = " + fileChannel.position() + " size=" 
                           + fileChannel.size());
        System.out.println(fileChannel.read(ByteBuffer.allocate(10),10000));
        fileChannel.position(9);
        System.out.println("B position = " + fileChannel.position() + " size=" 
                           + fileChannel.size());
        fileChannel.write(ByteBuffer.wrap("z".getBytes()));
        System.out.println("C position = " + fileChannel.position() + " size=" 
                           + fileChannel.size());
        fileChannel.size();
        fileChannel.close();
        
      }
    }
    ```

  - ```bash
    # abc.txt文件内容：abcde
    # 程序输出
    A position = 0 sizse=5
    -1
    B position = 9 sizse=5
    C position = 10 sizse=10
    
    # abc.txt内容被更改，新内容:
    abcde空格空格空格空格z
    
    ```

  ### 2.4.10 截断缓冲区

  - truncate(long size): 方法将此通道的文件截取给定大小。
    - 如果给定大小 < 当前文件大小，正常截取，丢弃文件新末尾后面的所有字节。
    -  如果给定大小 > 当前文件大小，不修改文件。
    - 

```java
public class Test10 {
  public static void main(String[] args) throws IOException {
    ByteBuffer byteBuffer1 = ByteBuffer.wrap("12345678".getBytes());
    FileOutputStream fileOutputStream = new FileOutputStream(new File("c:\\abc\\newtxt.txt"));
    FileChannel fileChannel = fileOutputStream.getChannel();
    fileChannel.write(byteBuffer1);
    System.out.println("A size=" + fileChannel.size() + " position=" + fileChannel.position());
    fileChannel.truncate(3);
    System.out.println("A size=" + fileChannel.size() + " position=" + fileChannel.position());
    fileChannel.close();
    // 而FileOutputStream及其父类没有缓冲区，即在使用时不需要调用flush()。
    fileOutputStream.flush();
    fileOutputStream.close();
  }
}
```

```bash
# 程序运行结果
A size=8 position=8
B size=3 position=3
# 生成文本内容：123
```

- 验证给定大小 >= 该文件当前大小

- ```java
  public class Test10_1 {
    public static void main(String[] args) throws IOException {
      ByteBuffer byteBuffer1 = ByteBuffer.wrap("12345678".getBytes());
      FileOutputStream fileOutputStream = new FileOutputStream(new File("c:\\abc\\abc.txt"));
      FileChannel fileChannel = fileOutputStream.getChannel();
      fileChannel.write(byteBuffer);
      System.out.println("A size=" + fileChannel.size() + " position=" + fileChannel.position());
      fileChannel.truncate(3000); // 很大的值
      System.out.println("B size=" + fileChannel.size() + " position=" + fileChannel.position());
      fileChannel.close();
      fileOutputStream.flush();
      fileOutputStream.close();
    }
  }
  ```

- ```bash
  # abc.txt文件默认内容：12345678
  A size=8 position=8
  B size=8 position=8
  
  # abc.txt文件的内容不变，依然是12345678
  ```

- 

### 2.4.11 将数据传输到其他可写入字节通道

- long transferTo(position, count, WritableByteChannel dest)
- 

### 2.4.12 将字节从给定可读取字节通道传输到此通道的文件中
- long transferFrom(ReadableByteChannel src, position, count)
    - 就是将数据从ReadableByteChannel通道中读取出来
    - src: 源通道
    - position : 文件中的位置，从此位置开始传输，不是指src源通道的位置。
    - count: 要传输的最大字节数
- 1 验证给定的位置大于该文件的当前大小，不传任何字节
    - 与transfetTo不同，方法transferFrom不能使FileChannel通道对应的文件大小增长。
```java
// a.txt文件的初始内容： abcdefg
// b.txt: 123456789
public class Test12_1 {
  public static void main(String[] args) throws IOException {
    RandomAccessFile fileA = new RandomAccessFile("./a.txt",rw);
    RandomAccessFile fileB = new RandomAccessFile("./b.txt",rw);
    FileChannel fileChannelA = fileA.getChannel();
    FileChannel fileChannelB = fileB.getChannel();
    fileChannelB.position(4);
    long readLength = fileChannelA.transferFrom(fileChannelB, 100, 2);
    System.out.println(readLength);
    fileChannelA.close();
    fileChannelB.close();
    fileA.close();
    fileB.close();
  }
}
// a.txt文件中的数据不变
```

- 2 正常传输数据测试
```java
// a.txt : abcdefg
// b.txt : 123456789
public class Test12_2 {
  public static void main(String[] args) throws IOException {
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    RandomAccessFile fileB = new RandomAccessFile("./b.txt","rw");

    FileChannel fileChannelA = fileA.getChannel();
    FileChannel fileChannelB = fileB.getChannel();
    
    fileChannelB.position(4);
    long readLength = fileChannelA.transferFrom(fileChannelB,3,2);
    System.out.println(readLength);
    
    System.out.println("A position: " + fileChannelA.position());
    System.out.println("B position: " + fileChannelB.position());
    
    fileChannelA.close();
    fileChannelB.close();
    fileA.close();
    fileB.close();
  }
}
```
```bash
# 执行后a.txt : abc56fg
2
A position: 0
B position: 6

```
- 1）count > src.remaining, 通的的src.remaining字节数传输到当前通的的position位置。
- 2）count < src.remaining, 则count个字节传输到当前通的的position位置。

```java
// 验证 1）count > src.remaining
// a.txt 1234567890
// b.abcdefg
public class Test12_3 {
  public static void main(String[] args) throws IOException {
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    RandomAccessFile fileB = new RandomAccessFile("./b.txt","rw");
    
    FileChannel fileChannelA = fileA.getChannel();
    FileChannel fileChannelB = fileB.getChannel();
    
    fileChannelB.position(2);
    fileChannelA.transferFrom(fileChannelB,1,200);
    System.out.println(readLength);
    
    fileChannelA.close();
    fileChannelB.close();
    fileA.close();
    fileB.close();
    
  }
}

// a.txt被写后： 1cdefg7890

```

```java
// 验证 1）count < src.remaining
// a.txt 1234567890
// b.abcdefg

public class Test12_4 {
  public static void main (String[] args) throws IOException {
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    RandomAccessFile fileB = new RandomAccessFile("./b.txt","rw");
    
    FileChannel fileChannelA = fileA.getChannel();
    FileChannel fileChannelB = fileB.getChannel();
    
    fileChannelB.position(2);
    
    long readLength = fileChannelA.transferFrom(fileChannelB,1,2);
    System.out.println(readLength);
    
    fileChannelA.close();
    fileChannelB.close();
    
    fileA.close();
    fileB.close();
    
  }
}
// a.txt运行后：1cd4567890

```
### 2.4.13 执行锁定操作
- FileLock lock(long position, long size, boolean shared)
    - 获取此通道的文件给定区域上的锁定。
    - 如果这个方法期间，另一个线程关闭通道，抛异常AsynchronousCloseException
    - 如果在等待获取锁的同时中断了调用线程，状态设置为中断，并抛出异常FileLockInterruptionException

- 1  FileLock lock(long position, long size, boolean shared)同步的
- 2 验证AsynchronousCloseException异常发生，
    - lock方法调用期间如果另一个线程关闭此通道，则抛出异常
```java
public class Test13_3 {
  private static FileOutputStream fileA;
  private static FileChannel fileChannelA;
  
  public static void main(String[] args) throws IOException, InterruptedException {
    FileOutputStream fileA = new FileOutputStream("./a.txt");
    FileChannel fileChannelA = fileA.getChannel();
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
```
    
- 3 验证FileLockInterruptionException异常发生
    - 如果在等待获取锁的同事中断调用线程，则状态设置为中断并抛出异常
    - 线程在获得锁时，感应到自身已经被中断，则抛出FileLockInterruptionException
```java
public class Test13_5_1 {
  public static void main(String[] args) throws IOException, InterruptedException{
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    System.out.println("A begin");
    fileChannelA.lock(0, 2, false);
    System.out.println("A end");
    Thread.sleep(20000);
    fileChannelA.close();
    fileA.close();
  }
}
```
```java
public class Test13_5_2 {
  public static void main(String[] args) throws IOException, InterruptedException{
    Thread t = new Thread() {
      public void run() {
        RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
        FileChannel fileChannelA = fileA.getChannel();
        System.out.println("B begin");
        fileChannelA.lock(0, 2, false);
        System.out.println("B end");
        fileChannelA.close();
        fileA.close();
      }
    };
    t.start();
    Thread.sleep(2000);
    t.interrupt();
  }
}
```
```bash
# 运行结果
B begin
java.nio.channels.FileLockInterruptionException
	at sun.nio.ch.FileChannelImpl.lock(FileChannelImpl.java:1092)
	at chapter2.Test13_5_2$1.run(Test13_5_2.java:22)
	
```
- 4 验证共享锁自己不能写---验证失败！！！
- 5 验证共享锁别人不能写---验证失败！！！
- 6 验证共享锁自己能读
- 7 验证共享锁别人能读
```java
public class Test13_10 {
  public static void main(String[] args) throws IOException, InterruptedException{
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    fileChannelA.lock(1, 2, true);
    Thread.sleep(Integer.MAX_VALUE);
  }
}
```
```java
public class Test13_11 {
  public static void main(String[] args) throws IOException, InterruptedException{
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    ByteBuffer byteBuffer = ByteBuffer.allocate(10);
    fileChannelA.read(byteBuffer);
    byteBuffer.rewind();
    for (int i = 0; i < byteBuffer.limit(); i++) {
         System.out.println((char)byteBuffer.get());
       }
  }
}
```

- 8 验证独占锁自己能写
- 9 验证独占锁别人不能写 -- 验证失败！！！
- 10 验证独占锁自己能读

- 11 验证独占锁别人不能读 --- 验证失败！！！
```java
public class Test13_16 {
  // 独占锁别人不能读
  public static void main(String[] args) throws IOException, InterruptedException{
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    fileChannelA.lock(1, 2, false);
    Thread.sleep(Integer.MAX_VALUE);
  }
}
```
```java
public class Test13_17 {
  // 独占锁别人不能读
  public static void main(String[] args) throws IOException, InterruptedException{
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    ByteBuffer byteBuffer = ByteBuffer.allocate(10);
    fileChannelA.read(byteBuffer);
    byteBuffer.rewind();
    for (int i = 0; i < byteBuffer.limit(); i++) {
      System.out.println((char)byteBuffer.get());
    }
  }
}
```

- 12 验证 lock()方法参数position和size的含义 -todo

- 13 提前锁定 (验证失败！！) 
- FileLock lock(long position, long size, boolean shared): 可以实现提前锁定，也就是当文件大小小于指定的position时，是可以提前在position位置处加锁。
```java
// a.txt: abcde
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
  }
}
```
- 14 验证共享锁与共享锁之前是非互斥关系
- 共享锁之间，独占锁之间，共享锁与独占锁之间的关系：
    - 1）共享锁与共享锁之间非互斥
    - 2）共享锁与独占锁之间互斥
    - 3）独占锁与共享锁之间互斥
    - 4）独占锁与独占锁之间互斥
- 验证共享锁与共享锁之前是非互斥关系    
```java
// 获得共享锁
public class Test13_20 {
  public static void main(String[] args) throws IOException, InterruptedException{
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    fileChannelA.lock(0,Long.MAX_VALUE,true);
    Thread.sleep(Integer.MAX_VALUE);
  }
}
```
```java
// 获得共享锁
// Test13_20获得的是共享锁，所以Test13_21也可以获得共享锁
public class Test13_21 {
  public static void main(String[] args) throws IOException, InterruptedException{
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    System.out.println("Test13_21 begin " + System.currentTimeMillis());
    fileChannelA.lock(0,Long.MAX_VALUE,true);
    System.out.println("v end 拿到锁 " + System.currentTimeMillis());
  }
}
/*
Test13_21 begin 1561950012551
Test13_21 end 拿到锁 1561950012566
*/
```
- 15 验证共享锁与独占锁之间是互斥关系
```java
// 获得共享锁
public class Test13_22 {
  public static void main(String[] args) throws IOException, InterruptedException{
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    fileChannelA.lock(0,Long.MAX_VALUE,true);
    Thread.sleep(Integer.MAX_VALUE);
  }
}
```

```java
// 获得独占锁
// 由于Test13_22获得共享锁，所以Test13_23获取独占锁线程阻塞
public class Test13_23 {
  public static void main(String[] args) throws IOException, InterruptedException{
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    System.out.println("Test13_23 begin " + System.currentTimeMillis());
    fileChannelA.lock(0,Long.MAX_VALUE,false);
    System.out.println("Test13_23 end 拿到锁 " + System.currentTimeMillis());
  }
}
/**
* 运行结果：Test13_23 begin 1561949328250
*/
```
- 16 验证独占锁与共享锁之间是互斥关系
```java
public class Test13_24 {
  public static void main(String[] args) throws IOException, InterruptedException{
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    fileChannelA.lock(0,Long.MAX_VALUE,false);
    Thread.sleep(Integer.MAX_VALUE);
  }
}


```
```java
// 由于Test13_24获得了独占锁，Test13_25获取共享锁失败，阻塞线程。
public class Test13_25 {
  public static void main(String[] args) throws IOException, InterruptedException{
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    System.out.println("Test13_25 begin " + System.currentTimeMillis());
    fileChannelA.lock(0,Long.MAX_VALUE,true);
    System.out.println("Test13_25 end " + System.currentTimeMillis());
  }
}
/**
* 运行结果：Test13_25 begin 1561948170978
*/

```
- 17 测试独占锁与独占锁之间是互斥关系
```java
public class Test13_26 {
  public static void main(String[] args) throws IOException, InterruptedException{
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    fileChannelA.lock(0,Long.MAX_VALUE,false);
    System.out.println("Test13_26 begin " + System.currentTimeMillis());
    Thread.sleep(Integer.MAX_VALUE);
    System.out.println("Test13_26 end " + System.currentTimeMillis());    
  }
}
/**
* 运行结果： 
* Test13_26 begin 1561947832251
*/
```
```java
// Test13_26已经获得了文件的独占锁，所以Test13_27是无法获得锁的，线程阻塞。
public class Test13_27 {
  public static void main(String[] args) throws IOException, InterruptedException{
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    fileChannelA.lock(0,Long.MAX_VALUE,false);
    System.out.println("Test13_27 begin " + System.currentTimeMillis());
    Thread.sleep(Integer.MAX_VALUE);
    System.out.println("Test13_27 end " + System.currentTimeMillis());    
  }
}
/**
* 运行结果： 
* Test13_27 begin 1561947838731
*/
```
### 2.4.14 FileLock lock()方法的使用
- FileLock lock(long position, long size, boolean shared) : 可以对文件某个区域进行部分锁定。
- FileLock lock()：获取对此通道的文件的独占锁，是对文件的整天进行锁定。
    - 与这个方法相同：fc.lock(OL,Long.MAX_VALUE,false)
    
### 2.4.15 获取通道文件给定区域的锁定
- FileLock tryLock(long position, long size, boolean shared)： 对此通道的文件给定区域的锁定，此方法不会阻塞。
    - 无论是否已经成功获得请求区域上的锁定，调用总是立即返回。
    - 由于另一个程序保持着一个重叠锁定而无法获取锁定，此方法返回null
    - 由于任何其他原因而无法获取锁定，则抛出相应的异常。
    - 某些操作系统不支持共享锁，自动将对共享锁定的请求转换为独占锁的请求。
    - isShared(): 来测试新获取的锁定是共享的还是独占的。
- FileLock tryLock(long position, long size, boolean shared) 和FileLock lock(long position, long size, boolean shared)方法的区别：
    - 1）tryLock()是非阻塞的
    - 2）lock()是阻塞的
- 测试：
    - 如果Test14_1类中设置为独占锁，Test14_2未获得锁
    - 如果Test14_1类中设置为共享锁，则Test14_2可以获得锁。
```java
// 测试两个方法
// 设置了独占锁
public class Test14_1 {
  public static void main(String[] args) throws  IOException, InterruptedException {
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    System.out.println("A begins");
    FileLock fileLock = fileChannelA.tryLock(0,5,false);
    System.out.println("A end 获得了锁 fileLock= " + fileLock);
    Thread.sleep(Integer.MAX_VALUE);
  }
}

// 运行结果：A begins
//       A end 获得了锁 fileLock= sun.nio.ch.FileLockImpl[0:5 exclusive valid]
```

```java
public class Test14_2 {
  public static void main(String[] args) throws IOException,InterruptedException {
    RandomAccessFile fileA = new RandomAccessFile("./a.txt","rw");
    FileChannel fileChannelA = fileA.getChannel();
    System.out.println("B begins");
    // 使用的是共享锁
    FileLock fileLock = fileChannelA.tryLock(0,5,true);
    System.out.println("B end 获得了锁 fileLock= " + fileLock);
    fileA.close();
    fileChannelA.close();
  }
}
// 运行结果：B begins
//  B end 获得了锁 fileLock= null

```
### 2.4.16 FileLock tryLock() 方法的使用
- FileLock tryLock(long position, long size, boolean shared) : 
- FileLock tryLock() : 获取对此通道的文件的独占锁，是对文件整体的进行锁定。
    - 与这个方法相同：fc.tryLock(OL,Long.MAX_VALUE,false)

### 2.4.17 FileLock类的使用
- FileLock类表示文件区域锁定的标记，每次通过FileChannel类的lock()或者tryLock()方法获取文件上的锁定时，就会创建一个FileLock（文件锁定）对象。
- FileLock对象：记录了在文件上保持锁定的文件通道，锁定的类型，有效性，锁定区域位置和大小。只有锁的有效性是随时间变更的，其他不可变。
- 多个并发线程可以安全地使用FileLock对象
- FileLock类具有平台依赖性，直接映射到底层操作系统的本机锁定机制。
- 有些系统，关闭某个通道会释放java虚拟机在底层文件上保持的所有锁定。
- 强烈建议在某个程序内使用唯一的通道来获取任意给定文件上的所有锁定。

- 1 常见API的使用
```java
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
```
- 2 boolean overlaps(long position, long size)方法的使用
- 判断此锁定是否与给定的锁区域重叠，至少重叠1字节，返回true
```java
public class FileLockAPI_2 {
  public static void main(String[] args) throws IOException {
    File file = new File("./a.txt");
    RandomAccessFile fileA = new RandomAccessFile(file,"rw");
    FileChannel fileChannelA = fileA.getChannel();
    FileLock lock = fileChannelA.lock(1, 10, true);
    System.out.println(lock.overlaps(5,10));
    lock.close();
  }
}
// 运行结果返回：true
```
```java
public class FileLockAPI_3 {
  public static void main(String[] args) throws IOException {
    File file = new File("./a.txt");
    RandomAccessFile fileA = new RandomAccessFile(file,"rw");
    FileChannel fileChannelA = fileA.getChannel();
    FileLock lock = fileChannelA.lock(11, 12, true);
    System.out.println(lock.overlaps(5,10));
    lock.close();
  }
}
// 运行结果返回：false
```


### 2.4.18 强制将所有对通道文件的更新写入包含文件的存储设备
- void force(boolean metaData) ：强制将所有对此通道的文件更新写入包含该文件的内存设备。
- 1 void force(boolean metaData) 方法的性能:
    - FileChannel类write()方法： 数据写入操作系统内核缓存中，在某一时间点再将内核缓存中的数据批量地同步到硬盘中。同步的时间由操作系统决定。
    - force()方法：同步时间不让操作系统决定，强制把操作系统内核缓存中的数据同步到硬盘。
    - force(): 只是进最大能力同步。不一定成功。    
```java
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
      //fileChannelA.force(false);
    }
    long endTime = System.currentTimeMillis();
    System.out.println(endTime - beginTime);
    
    fileChannelA.close();
    fileA.close();
    
  }
}
// 不执行force() : 254
// 执行force() : 487
```    
- 2 布尔参数metaData的作用
    - metaData: 
        - false: 只需要对文件内容的更新写入存储设备
        - true: 文件内容，元数据更新，都要写入存储设备。
    - 在Linux系统中，无论传入什么，都会更新文件的元数据，最终调用的就是fsync()方法。    
### 2.4.19 将通道文件区域直接映射到内存
- MappedByteBuffer map(FileChannel.MapMode mode, long position, long size): 将通道的文件区域直接映射到内存中，
可以通过3中模式将"文件区域"映射到"内存"中。
- 1）只读: 试图修改得到的缓冲区，将导致抛出ReadOnlyBufferException异常。（MapMode.READ_ONLY）
- 2）读取/写入 : 对得到的缓冲区的更改最终将传播到文件，该更改对映射到同一文件的其他程序不一定可见。（MapMode.READ_WRITE）
- 3）专用 : 对得到的缓冲区的更改不会传播到文件，并且该更改对映射到统一文件的其他程序也不可见，相反，会创建缓冲区已修改部分的专用副本。(MapMode.PRIVATE)
- 对于只读映射关系，此通道必须可以进行读取操作，对于读取、写入或者专用映射关系，此通道必须可以进行读取和写入操作。
- 返回的缓冲区，position=0,limit=capacity=size,标记不确定
- 映射关系建立后，不依赖所用的文件通道。
- 与read(), write()方法比较，文件映射到内存开销更大。通常相对较大的文件映射到内存中才是值得的。
- 参数：
    - 1） mode: 
    - 2) position: 文件中的位置，从这个位置开始
    - 3） 映射的区域大小。
    
- 1 MapMode和MappedByteBuffer类的介绍
    - MappedByteBuffer类： 直接字节缓冲区，文件的内存映射区域。通过map()方法创建的。
    - 此类用特定于内存映射文件区域的操作扩展ByteBuffer类
    - 映射的字节缓冲区和它表示的文件映射关系在该缓冲区成为垃圾回收缓冲区之前都一直保持有效。
    - 对于父类ByteBuffer新增的方法：
        - force() : 将此缓冲区所做的内容梗概强制写入包含映射文件的存储设备中。
        - isLoad() ：判断次缓冲区的内容是否位于物理内存中
        - load() ：将缓冲区的内容加载到物理内存中
            
- 2 map(MapMode mode, long position, long size)方法的使用
```java
// a.txt文件内容：abcdefg
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
    // 项目程序代码出现异常，因为超出了映射的范围
    System.out.println((char)buffer.get() + " position= " + buffer.position());
    fileA.close();
    fileChannelA.close();
  }
}
```
- 3 只读模式（READ_ONLY)的测试
```java
public class Test16_2 {
  public static void main(String[] args) throws IOException, InterruptedException{
    File file = new File("./a.txt");
    RandomAccessFile fileA = new RandomAccessFile(file,"rw");
    FileChannel fileChannelA = fileA.getChannel();
    MappedByteBuffer buffer = fileChannelA.map(FileChannel.MapMode.READ_ONLY,0,5);
    // 此出现异常，因为是只读的，不允许更改数据
    buffer.putChar('1');
  }
}
```
- 4 可写可读模式（READ_WRITE）的测试
```java
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
// 运行后
a position= 1
b position= 2
c position= 3
d position= 4
e position= 5
// a.txt内容被改为：opqrs

```
- 5 专用模式（PRIVATE）的测试
   - 对文件的更改只针对当前的MappedByteBuffer可视，并不更爱底层文件。
```java
// a.txt文件内容：abcde
public class Test16_4 {
    public static void main(String[] args) throws IOException, InterruptedException {
      File file = new File("./a.txt");
      RandomAccessFile fileA = new RandomAccessFile(file,"rw");
      FileChannel fileChannelA = fileA.getChannel();
      MappedByteBuffer buffer = fileChannelA.map(FileChannel.MapMode.PRIVATE,0,5);
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
// 运行后：a.txt的内容未被更改：abcde
```
- 6 MappedByteBuffer类的force()方法使用
    - 把此缓冲区所做的内容更改强制写入包含映射文件的存储设备中。
    - 如果文件位于本地存储设备，返回可以保证写入是否成功。
    - 如果文件不在本地设备，返回结果无法保证写入是否成功。
    - 如果缓冲区不是以读写模式映射的（FileChannel.MapMode.Read_write）,调此方法无效。
    - 调用该方法程序运行效率会下降。
```java
public class Test16_5 {
  public static void main(String[] args) throws IOException, InterruptedException {
    File file = new File("./a.txt");
    RandomAccessFile fileA = new RandomAccessFile(file, "rw");
    FileChannel fileChannelA = fileA.getChannel();
    MappedByteBuffer buffer = fileChannelA.map(FileChannel.MapMode.READ_WRITE,0,100);
    long beginTime = System.currentTimeMillis();
    for (int i = 0; i < 100; i++) {
      buffer.put("a".getBytes());
    }
    long endTime = System.currentTimeMillis();
    System.out.println(endTime - beginTime);
    fileChannelA.close();
    fileA.close();
  }
}
// 运行后控制台输出：1
```    
```java
public class Test16_6 {
  public static void main(String[] args) throws IOException, InterruptedException {
    File file = new File("./a.txt");
    RandomAccessFile fileA = new RandomAccessFile(file, "rw");
    FileChannel fileChannelA = fileA.getChannel();
    MappedByteBuffer buffer = fileChannelA.map(FileChannel.MapMode.READ_WRITE,0,100);
    long beginTime = System.currentTimeMillis();
    for (int i = 0; i < 100; i++) {
      buffer.put("a".getBytes());
      buffer.force();
    }
    long endTime = System.currentTimeMillis();
    System.out.println(endTime - beginTime);
    fileChannelA.close();
    fileA.close();
  }
}
// 运行后控制台输出：9
```
- 7 MappedByteBuffer load() 和 boolean isLoad()方法的使用
    - load()方法将缓冲区内容，加载到物理内存中。
    - isLoad() : 判断缓冲区内容，是否加载到物理内存中。
        - 返回结果只是提示不是保证
        - 因为这个方法调用返回之前，地层操作系统可能已经移出了某些缓冲区数据。
```java
public class Test16_7 {
  public static void main(String[] args) throws IOException,InterruptedException {
    File file = new File("./a.txt");
    RandomAccessFile fileA = new RandomAccessFile(file,"rw");
    FileChannel fileChannelA = fileA.getChannel();
    MappedByteBuffer buffer = fileChannelA.map(FileChannel.MapMode.READ_WRITE,0,100);
    System.out.println(buffer + " " + buffer.isLoaded());
    buffer.load();
    System.out.println(buffer + " " + buffer.isLoaded());
    fileChannelA.close();
    fileA.close();
  }
}

```   

### 2.4.20 打开一个文件
- FileChannel open(Path path, OpenOption...option)： 打开一个文件，以便对这个文件进行处理
    - Path : 文件在文件系统中的路径，接口有多种实现， 这里使用File类的toPath()方法获取。
    - OpenOption： 使用StandardOpenOption常量。
- 1 枚举常量CREATE和WRITE的使用
    - CREATE： 
        - 如果文件存在，不会改变原始文件内容。
        - 设置了CREATE_NEW，这个选项会被忽略
    - WRITE: 打开以进行写入访问
```java
public class OpenMethod2Param_1 {
  public static void main(String[] args) throws IOException {
    File file = new File("./aaa.txt");
    Path path = file.toPath();
    // 情况1 ： 运行程序报错，因为不能单独使用CREATE
   // FileChannel fileChannel = FileChannel.open(path,StandardOpenOption.CREATE);
    // 情况2 ： 正常， 
    FileChannel fileChannel = FileChannel.open(path,StandardOpenOption.CREATE
    ,StandardOpenOption.WRITE);
    
    fileChannel.close();
  }
}

```    
- 2 枚举常量APPEND的使用
    - 如果打开文件写入访问，字节将写入文件末尾而不是开始处。
```java
public class OpenMethod2Param_3 {
  public static void main(String[] args) throws IOException{
    File file = new File("./aaa.txt");
    Path path = file.toPath();
    FileChannel fileChannel = FileChannel.open(path,
    StandardOpenOption.APPEND);
    fileChannel.write(ByteBuffer.wrap("123".getBytes()));
    fileChannel.close();
  }
}
// 程序运行后，aaa.txt文件内容变成：abcde123
```    
- 3 枚举常量READ的使用
    - 打开以进行读取访问
```java
// 存在文件aaa.txt 内容：abcde
public class OpenMethod2Param_4 {
  public static void main (String[] args) throws IOException{
    File file = new File("./aaa.txt");
    Path path = file.toPath();
    FileChannel fileChannel = FileChannel.open(path,StandardOpenOption.READ);
    
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
```
- 4 枚举常量TRUNCATE_EXISTING的使用
    - 如果文件存在并且为写入访问打开，则长度被截断为0，
    - 如果只为读取访问打开文件，则忽略此选项。
```java
// 存在文件aaa.txt, 并且初始内容为abcde
public class OpenMethod2Param_5 {
  public static void main(String[] args) throws IOException {
    File file = new File("./aaa.txt");
    Path path = file.toPath();
    FileChannel fileChannel = FileChannel.open(path,StandardOpenOption.TRUNCATE_EXISTING,
    StandardOpenOption.WRITE);
    fileChannel.close();
  }
}
// 程序运行后文件aaa.txt内容为空
```    
- 5 枚举常量CREATE_NEW的使用
    - 创建一个新文件，如果该文件已经存在则失败。
```java
public class OpenMethod3Param_6 {
  public static void main(String[] args) throws IOException {
    File file = new File("./aaa.txt");
    Path path = file.toPath();
    FileChannel fileChannel = FileChannel.open(path,StandardOpenOption.CREATE_NEW,
    StandardOpenOption.WRITE);
    fileChannel.close();
  }
}
```
- 6 枚举常量DELETE_ON_CLOSE的使用
    - 关闭时删除。
    - 调用close方法时，删除文件
    - 未调用close方法时，在java虚拟机终止时尝试删除该文件。
    
````java
// 假设不存在文件aaa.txt
public class OpeMethod2Param_7 {
  public static void main(String[] args) throws IOException, InterruptedException {
    File file = new File("./aaa.txt");
    Path path = file.toPath();
    FileChannel fileChannel = FileChannel.open(path,StandardOpenOption.DELETE_ON_CLOSE,
    StandardOpenOption.CREATE,StandardOpenOption.WRITE);
    Thread.sleep(10000);
    fileChannel.close();
  }
}
// 程序创建新的文件aaa.txt, 10s后，程序结束时，则自动删除aaa.txt文件
````
- 7 枚举常量SPARSE的使用
    - 稀疏文件，如果文件系统不支持创建稀疏文件，忽略这个选项。
        - 
    - 普通文件
```java
// 创建一个普通文件，而且文件很大
public class OpenMethod2Param_8 {
  public static void main(String[] args) throws IOException, InterruptedException {
    File file = new File("./aaa.txt");
    Path path = file.toPath();
    FileChannel fileChannel = FileChannel.open(path,StandardOpenOption.CREATE_NEW,StandardOpenOption.WRITE);
    long fileSize = Integer.MAX_VALUE;
    fileSize = fileSize + fileSize + fileSize;
    fileSize = fileSize + fileSize + fileSize;
    fileChannel.position(fileSize);
    fileChannel.write(ByteBuffer.wrap("a".getBytes()));
    fileChannel.close();
   
  }
}
```    
- 程序说明：没有执行上面的代码：C盘空间剩余152GB，
- 执行上面的代码：C盘空间剩余134GB，只对aaa.txt文件写入了一个字符也占用了18G硬盘空间。
- aaa.txt文件大小18G，这个18G文件只有一个字符a有效，其他都不是存储数据的空间。
- 那么，对于那些不存储数据的空间不让其占用硬盘容量，等以后写入有效的数据时，再占用硬盘容量。
- 这样就达到提高硬盘利用率的目的，这个需求可以通过创建一个稀疏文件来实现。
```java
public class OpenMethod2Param_9 {
  public static void main(String[] args) throws IOException,InterruptedException {
    File file = new File("./aaa.txt");
    Path path = file.toPath();
    FileChannel fileChannel = FileChannel.open(path,StandardOpenOption.SPARSE,StandardOpenOption.CREATE_NEW,StandardOpenOption.WRITE);
    long fileSize = Integer.MAX_VALUE;
    fileSize = fileSize + fileSize + fileSize;
    fileChannel.position(fileSize);
    fileChannel.write(ByteBuffer.wrap("a".getBytes()));
    fileChannel.close();
  }
}
// 程序创建的文件只占：64KB

```


- 8 枚举常量SYNC的使用
    - 要求对文件内容或者元数据的每次更新都同步写入底层存储设备
    - 更新内容和元数据
    - 程序效率就降低
```java
// 不适用SYNC同步选项的程序运行时间
public class OpenMethod2Param_10 {
  public static void main(String[] args) throws IOException, InterruptedException {
    File file = new File("./aaa.txt");
    Path path = file.toPath();
    FileChannel fileChannel = FileChannel.open(path,
    StandardOpenOption.CREATE_NEW,StandardOpenOption.WRITE);
    long beginTime = System.currentTimeMillis();
    for (int i = 0; i < 200; i++) {
      fileChannel.write(ByteBuffer.wrap("a".getBytes()));
    }
    long endTime = System.currentTimeMillis();
    System.out.println(endTime - beginTime);
    fileChannel.close();
  }
}

// 程序输出：4
```
```java
// 经过SYNC同步后的运行时间
public class OpenMethod2Param_11 {
  public static void main (String[] args) throws IOException,InterruptedException {
    File file = new File("./aaa.txt");
    Path path = file.toPath();
    FileChannel fileChannel = FileChannel.open(path,StandardOpenOption.SYNC, StandardOpenOption.CREATE_NEW,StandardOpenOption.WRITE);
    long beginTime = System.currentTimeMillis();
    for (int i = 0; i < 200; i++) {
          fileChannel.write(ByteBuffer.wrap("a".getBytes()));
        }
    long endTime = System.currentTimeMillis();
    System.out.println(endTime - beginTime);
    fileChannel.close();
    
  }
}
// 输出：27

  
```
- 9 枚举常量DSYNC的使用
    - 要求对文件内容的每次更新都同步写入底层存储设备
    - 只更新内容，和force(boolean)方法一样

### 2.4.21 判断当前通道是否打开
- public final boolean idOpen() : 判断当前通道是否处于打开状态。
```java
public class IsOpenTest{
  public static void main(String[] args) throws IOException{
    File file = new File("./a.txt");
    RandomAccessFile fileA = new RandomAccessFile(file,"rw");
    FileChannel fileChannelA = fileA.getChannel();
    System.out.println(fileChannel.isOpen());
    fileChannelA.close();
    System.out.println(fileChannel.isOpen());
  }
}
// 输出：true
// 输出：false

```

## 2.5 小结
- 介绍了FileChannel类和MappedByteBuffer类对文件的操作
- 这个不比InputStream和OutputStream效率高
- NIO把线程变成非阻塞，提高效率。
- NIO中非阻塞的特性与Socket有关的通道进行实现的。
- 先掌握Socket的使用，在学习非阻塞特性
- 下面开始Socket的网络编程。



  

















