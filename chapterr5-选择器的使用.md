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

### 5.8.8 对相同的通道注册不同的相关事件返回同一个SelectionKey

### 5.8.9 判断选择器是否为打开状态
- isOpen(): 只有打开时返回true
- close() : 关闭
    - 所有与选择器关联的键无效，键对应的通道取消，健对应的所有资源释放。
    - 已经关闭的选择器，close()方法无效。
    - 已经关闭的选择器，其他方式调用选择器，抛出ClosedSelectorException,
    - 已经关闭的选择器,可以调用wakeup()方法

### 5.8.10 获得SelectorProvider provider对象
- public abstract SelectorProvider provider()：方法的作用是返回创建此通道的提供者

### 5.8.11 返回此选择器的健集
- keys(): 返回选择器的健集合，不可以直接修改集合，
    - 已经取消某个键并且已注销通道后才移除这个建。
    - 试图修改健集合会抛出UnsupportedOperationException
    
### 5.8.12 public abstract int select(long timeout)方法的使用
- 方法是阻塞模式的
- 方法返回条件：
    - 至少选择一个通道，
    - 调用wakeup()，
    - 当前线程中断
    - 给定的超时期限满
- 不提供实时保证
- 返回值：表示更新了准备就绪操作建集合的数目
    
    
### 5.8.13 public abstract int selectNow()方法的使用
- 非阻塞的选择操作
- 选择操作，更新就绪通道的健
- 上次选择操作后，没有通道变成就绪可以选择，直接返回0

### 5.8.14 唤醒操作
- public abstract Selector wakeup() : 

## 5.9 SelectionKey类的使用
### 5.9.1 判断是否允许连接SelectableChannel对象
- public final boolean isAcceptable() : 测试这个健的通道是否已经准备好接受新的套接字连接
- public final boolean isConnectable() : 测试这个健的通道是否已经完成其套接字连接操作
- public abstract SelectableChannel channel() : 返回这个健关联的通道
    - 即使已经取消这个键，仍然能返回这个通道
    
###  5.9.2 判断是否已经准备好进行读取
- public final boolean isReadable() : 测试这个键的通道是否已经准备好进行读取。

###  5.9.3 判断是否已经准备好进行写入
- public final boolean isWritable() : 测试这个键的通道是否已经准备好进行写入。

### 5.9.4 返回SelectionKey关联的选择器
- public abstract Selector selector() : 返回SelectionKey关联的选择器。
    - 即使已经取消这个键，仍然能返回选择器

### 5.9.5 在注册操作时传入attachment附件
- 
### 5.9.6 设置attachment附件
- public final Object attach(Object ob) : 将给定的对象附加到此键

### 5.9.7 获取与设置此键的interest集合
- public abstract int interestOps() : 获取此键的interest集合。
    - 返回的是操作位是对这个键对应的通道有效的。
- public abstract SelectionKey interestOps(int ops) : 设置这个集合的值

### 5.9.8 判断此键是否有效
- public abstract boolean isValid() : 告知这个键是否有效
    - 键被取消之前，有效
    - 通道关闭之前，有效
    - 选择器关闭之前，有效
    
### 5.9.9 获取这个键的ready操作集合
- public abstract int readOps() : 获取这个键的ready操作集合
    - 保证返回的集合仅仅包含对于这个（这个键对应的）通道有效的操作位。

### 5.9.10 取消操作
- public abstract void cancel(): 取消这个（这个键对应的）通道的到选择器的注册
    - 一旦返回，这个键就是无效，并添加这个无效的建，到选择器的已取消键集合中。
    - 下一次select()方法时，所有集合中移除这个无效的键。
    - 如果已经取消了这个键，则调用这个方法无效
    - isValid()返回false
    - 可以任意时间调用cancel()方法
    - 和select()方法并发调用，则可能cancel()方法会阻塞

### 5.10 DatagramChannel类的使用
### 5.10.1 使用DatagramChannel类实现UDP通信
- 服务端操作： bind()
    - 打开的DatagramChannel可以在UDP端口8888上接收数据包。
### 5.10.2 连接操作
- 客户端操作 ： public abstract DatagramChannel connect(SocketAddress ) ： 连接此通道的套接字
- 可以将DatagramChannel“连接”到网络中的特定地址的。由于UDP是无连接的，
- 连接到特定地址并不会像TCP通道那样创建一个真正的连接。而是锁住DatagramChannel ，让其只能从特定地址收发数据。

### 5.10.3 断开连接
- public abstract DatagramChannel disconnect() : 断开这个socket的链接


    















