# chapter1 - 缓冲区的使用
- ByteBuffer类缓冲区的技术原理：使用byte[]数组进行数据的保存。后续使用指定的API来操作这个数组以达到操作缓冲区的目的。
- 父类：ByteBuffer
- 子类：CharBuffe,DoubbleBuffer,FloatBuffer,LongBuffer,IntBuffer,ShotBuffer


- ByteBuffer,CharBuffe,DoubbleBuffer,FloatBuffer,LongBuffer,IntBuffer,ShotBuffer是抽象类。
- warp()创建这些缓冲区的工厂方法
- HeapByteBuffer类是ByteBuffer的子类
- 调用API处理buf的字节数组中的数据时，执行的是HeapByteBuffer类中重写的方法。
- 源码显示，ByteBuffer类中还是使用byte[]字节数组存储数据。
- ByteBuffer类把数据和缓冲区中的信息封装，便于信息处理。
- 缓冲区核心技术点：
	- capacity(容量)：缓冲区的容量，不能更改。
	- limit(限制)：缓冲区的限制，第一个不应该读取或者写入元素的index(索引)。就是，允许存数据的最大下标。
	- position(位置)：position > limit; 则position设置为新的limit。
	- mark(标记)：mark > limit; 则丢弃该mark.
	- 大小关系：0 <= mark <= position <= limit <= capacity
## 1.4 ByteBuffer类的使用
### 1.4.1 创建堆缓冲区与直接缓冲区
### 1.4.2 直接缓冲区与非直接缓冲区的运行效率比较
### 1.4.3 包装wrap数据的处理
### 1.4.4 put（byte b) 和get() 方法的使用与position自增特性
### 1.4.5 put(byte[] src, int offset, int length) 和 get(byte[] dst, int offset, int length)方法的使用
### 1.4.6 put(byte[] src) get(byte[] dst) 
### 1.4.7 put(int index, byte[] b) get(int index, byte[] b)
### 1.4.8 put(ByteBuffer src) 
### 1.4.9 putType() getType()__
### 1.4.10 slice() arrayOffSet()为非0的测试
### 1.4.11 转换为CharBuffer字符缓冲区以及中文处理
### 1.4.12 转换为其他类型的缓冲区
### 1.4.13 设置与获得字节顺序
### 1.4.14 创建只读缓冲区
### 1.4.15 压缩缓冲区
### 1.4.16 比较缓冲区内容
### 1.4.17 复制缓冲区
### 1.4.18 对缓冲区进行扩容

## 1.5 CharBuffer类的API使用
### 1.5.1 重载append(char)/ append(CharSequence)/ append(CharSequence, start, end)方法的使用
### 1.5.2 读取相对于当前位置的定索引出的字符
### 1.5.3 put(String src) int read(CharBuffer target) 和 subSequence(int stat, int end)方法的使用
### 1.5.4 static CharBuffer wrap(CharSequence csq, int start, int end)

- 将字符序列包装到缓冲区。
- 



### 1.5.5 获取字符缓冲区的长度

- public final int length(): 返回缓冲区的长度。缓冲区还剩下的字符长度。
  - 缓冲区为字符序列：长度 == remainning(), 当前位置到limit之间的字符数。

```java

```



## 1.6 小结































