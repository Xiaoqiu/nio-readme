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
