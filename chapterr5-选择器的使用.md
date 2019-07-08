### 5.7.20 执行Connect连接操作
- public abstract boolean connect(SocketAddress remote): 连接到远程通道Socket.
- 此通道非阻塞：立即返回false,并以后调用finishConnect()方法来验证连接是否完成。（连接不是立即建立）
- 此通道阻塞：这个方法调用将会阻塞，直到建立连接或者发送IO错误。

### 5.7.21 判断此通道上是否在进行连接操作
- public abstract boolean isConnectPending()方法：判断此通道上是否正在进行连接操作。
- true: 发起了连接操作，但是没有调用finishConnect()方法，完成连接。
- 在accept()方法和close()方法之间，总是返回true

### 5.7.22 完成套接字通道的连接过程
- public abstract boolean finishConnect() ：sokectChannel设置为非阻塞，调用connect()方法，发起非阻塞连接，如果连接失败，
调用finishConnect()抛出IOException
- 已经连接了，立即返回true,如果是此通道非阻塞，连接没完成，返回false
- 如果通道是阻塞模式，连接完成或者失败之前都将阻塞此方法。如果连接成功之后返回true,连接失败抛出异常。


### 5.7.23 类FileChannel中的long transferTo(position, count, Writable)


### 5.8.1 验证public abstract int select() 方法具有阻塞性
- 选择一组健，其相应的通道已经为IO操作准备就绪
- 这个方法会阻塞，当调用选择器的wakeup()方法，或者当前线程已中断，方法才返回。
- 返回值，表示添加到就绪操作集的健数目，不为0。为0表示，就绪操作集中的内容没有变化。
- 大多数情况下服务端使用while(true)无限循环接收客户端请求。
    - 如果出现select()方法不出现阻塞的情况，造成死循环。
    - 下一节解释其原因和解决方法

### 5.8.2 select()方法不阻塞的原因和解决方法
- 因为server端没有对accept事件处理，select()一直检测到有准备好的通道。
- 一直没处理，一直死循环。
- 解决办法就是将accept事件消化处理

### 5.8.3 出现重复消费的情况
- 如果两个不同的通道注册到相同的选择器，出现重复消费情况

### 5.8.5 验证产生的set1和set2关联的各自对象一直是同一个

### 5.8.6 int selector.select()方法返回值的含义
- 更新准备就绪的操作集健的数目
- =0 ： set2中的元素不改变
- !=0 : set2中添加SelectionKey个数
```java
  // 代表有多少channel处于就绪了
      int keyCount = selector1.select();
      // 获取此selector中已经注册(可能已经cancelled但尚未deregister)的所有选择键.此集合不能被外部修改.
      Set<SelectionKey> set1 = selector1.keys();
      // 即前一次操作期间,已经准备就绪的通道所对应的选择键.此集合为keys的子集
      // 返回此选择器当前已选择键集合.此集合不能被add,但可以remove操作.
      Set<SelectionKey> set2 = selector1.selectedKeys();
```

### 5.8.7 从已经准备就绪的健集合中获得通道中的数据
















