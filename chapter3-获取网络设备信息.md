## 3.1 NetworkInterface类的常用方法
### 3.1.1 获得网络接口的基本信息
- 1） 网络设备的索引可能不连续
- 2）isLoopback() 对lo设备返回true,其他为false,系统只有一个回环地址
- 3）isUp() 返回true是因为网络设备正在工作。
- 4）getDisplayName()返回值，是可以有据可查的。（网络接口的硬件名称）

```java
public class Test1 {
  public static void main(String[] args) {
    try {
      Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
      while (networkInterface.hasMoreElements()) {
        NetworkInterface eachNetworkInterface = networkInterface.nextElement();
        System.out.println("getName获得网络设备名称 ： " + eachNetworkInterface.getName());
        System.out.println("getDisplayName获得网络设备显示名称 ： " + eachNetworkInterface.getDisplayName());
        System.out.println("getIndex获得网络接口的索引 ： " + eachNetworkInterface.getIndex());
        System.out.println("isUp是否已经开启并运行 ： " + eachNetworkInterface.isUp());
        System.out.println("isLoopback是否为回调接口 ： " + eachNetworkInterface.isLoopback());
        System.out.println();
        System.out.println();
        
      }
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }
}
```
```bash
# 运行结果
etName获得网络设备名称 ： utun0
getDisplayName获得网络设备显示名称 ： utun0
getIndex获得网络接口的索引 ： 12
isUp是否已经开启并运行 ： true
isLoopback是否为回调接口 ： false


getName获得网络设备名称 ： awdl0
getDisplayName获得网络设备显示名称 ： awdl0
getIndex获得网络接口的索引 ： 8
isUp是否已经开启并运行 ： true
isLoopback是否为回调接口 ： false


getName获得网络设备名称 ： vmnet8
getDisplayName获得网络设备显示名称 ： vmnet8
getIndex获得网络接口的索引 ： 14
isUp是否已经开启并运行 ： true
isLoopback是否为回调接口 ： false


getName获得网络设备名称 ： vmnet1
getDisplayName获得网络设备显示名称 ： vmnet1
getIndex获得网络接口的索引 ： 13
isUp是否已经开启并运行 ： true
isLoopback是否为回调接口 ： false


getName获得网络设备名称 ： en0
getDisplayName获得网络设备显示名称 ： en0
getIndex获得网络接口的索引 ： 6
isUp是否已经开启并运行 ： true
isLoopback是否为回调接口 ： false


getName获得网络设备名称 ： lo0
getDisplayName获得网络设备显示名称 ： lo0
getIndex获得网络接口的索引 ： 1
isUp是否已经开启并运行 ： true
isLoopback是否为回调接口 ： true

```

### 3.1.2 获取MTU大小
- public int getMTU()方法： 返回MTU大小。
- 在网络传输中是以数据包为基本传输单位，MTU (Maximum Transmission Unit,最大传输单元)
- MTU, 来规定网络传输的数据包的大小，单位为字节。
- 以太网的网卡MTU大多数默认为1500字节，IPv6协议中范围（1280-65535）
- MTU设置大小与传输效率有关，如果设置大，传输速度快，延迟大，因为对方需要一点点地处理数据
- 设置小，输出慢，但是发送的数据包数量多了，
- 建议不要随意更改网卡的MTU值。会造成网络传输数据故障，发生丢包的现象。
- MTU = -1 会出现在网络接口禁用的情况下

````java

public class Test2 {
    public static void main(String[] args) {
       try {
          Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
          while(networkInterface.hasMoreElements()) {
            NetworkInterface eachNetworkInterface = networkInterface.nextElement();
            System.out.println("getName获得网络设备名称 ： " + eachNetworkInterface.getName());
            System.out.println("getDisplayName获得网络设备显示名称 ： " + eachNetworkInterface.getDisplayName());
            System.out.println("getMTU获得最大传输单元 ： " + eachNetworkInterface.getMTU());  
            System.out.println();
            System.out.println();
          }
        } catch (SocketException e) {
          e.printStackTrace();
        }
    }
}
````
```bash
## 运行结果
getName获得网络设备名称 ： utun0
getDisplayName获得网络设备显示名称 ： utun0
getMTU获得最大传输单元 ： 2000


getName获得网络设备名称 ： awdl0
getDisplayName获得网络设备显示名称 ： awdl0
getMTU获得最大传输单元 ： 1484


getName获得网络设备名称 ： vmnet8
getDisplayName获得网络设备显示名称 ： vmnet8
getMTU获得最大传输单元 ： 1500


getName获得网络设备名称 ： vmnet1
getDisplayName获得网络设备显示名称 ： vmnet1
getMTU获得最大传输单元 ： 1500


getName获得网络设备名称 ： en0
getDisplayName获得网络设备显示名称 ： en0
getMTU获得最大传输单元 ： 1500


getName获得网络设备名称 ： lo0
getDisplayName获得网络设备显示名称 ： lo0
getMTU获得最大传输单元 ： 16384

```
### 3.1.3 子接口的处理
- 子接口：在不添加新的物理网卡的基础上，基于原有的网络接口设备，再创建一个虚拟的网络接口设备进行通信
- 这个虚拟网络接口：理解成一个由软件模拟的网卡，
- widows操作系统不支持子接口，Linux支持
- public boolean isVirtual(): 判断当前的网络接口是否为 "虚拟子接口"
- 虚拟子接口：
    - 给与不同的设置（IP或MTU）
    - 名称通常是父网络接口名称加上冒号（:），再加上标识该子接口的编号
    - 一个物理网络接口可以存在多个虚拟子接口。
- 虚拟接口
    - 是软件模拟的，没有父网络接口
    - 虚拟子接口也是由软件模拟的，但是又父网络接口
    - 虚拟接口并不定是虚拟子接口，而虚拟子接口一定是虚拟接口
- public NetworkInterface getParent()方法： 获得父接口。（所属的硬件网卡）
```java
public class Test3 {
 public static void main(String[] args) {
     try {
       Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
       while(networkInterface.hasMoreElements()) {
         NetworkInterface eachNetworkInterface = networkInterface.nextElement();
         System.out.println("父接口的hashCode ： " + eachNetworkInterface.hashCode());
         System.out.println("getName获得网络设备名称 ： " + eachNetworkInterface.getName());
         System.out.println("getDisplayName获得网络设备显示名称 ： " + eachNetworkInterface.getDisplayName());
         System.out.println("isVirtual是否为虚拟接口 ： " + eachNetworkInterface.isVirtual());
         System.out.println("getParent获得父接口 ： " + eachNetworkInterface.getParent());
         System.out.println("getSubInterfaces获得子接口信息 ： ");
         Enumeration<NetworkInterface> networkInterfaceSub = eachNetworkInterface.getSubInterfaces();
         while (networkInterfaceSub.hasMoreElements()) {
           NetworkInterface eachnNetworkInterfaceSub = networkInterfaceSub.nextElement();
             System.out.println("getName获得网络设备名称 ： " + eachnNetworkInterfaceSub.getName());
             System.out.println("getDisplayName获得网络设备显示名称 ： " + eachnNetworkInterfaceSub.getDisplayName());
             System.out.println("isVirtual是否为虚拟接口 ： " + eachnNetworkInterfaceSub.isVirtual());
             System.out.println("getParent获得父接口hashCode ： " + eachnNetworkInterfaceSub.getParent().hashCode());
           }
         System.out.println();
         System.out.println();
         }
     } catch (SocketException e) {
       e.printStackTrace();
     }
   }
}
```
  
### 3.1.4 获得硬件地址
- public byte[] getHardwareAddress() : 网卡的硬件地址，定义网络设备的位置，网卡设备的唯一ID.
- 输出的物理地址是十进制的，真正的物理地址是十六进制。
```java
public class Test4 {
  public static void main(String[] args) {
    try {
      Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
      while (networkInterface.hasMoreElements()) {
        NetworkInterface eachNetworkInterface = networkInterface.nextElement();
        System.out.println("getName获得网络设备名称=" + eachNetworkInterface.getName());
        System.out.println("getDisplayName获得网络设备显示名称 ： " + eachNetworkInterface.getDisplayName());
        System.out.println("getHardwareAddress获得网卡的物理地址 ： ");
        byte[] byteArray = eachNetworkInterface.getHardwareAddress();
        if (byteArray != null && byteArray.length != 0) {
          for (int i = 0; i < byteArray.length; i++) {
            System.out.print(byteArray[i] + " ");
          }
          System.out.println();
        }
        System.out.println();
        System.out.println();
      }
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }
}
```
```bash
getName获得网络设备名称=utun0
getDisplayName获得网络设备显示名称 ： utun0
getHardwareAddress获得网卡的物理地址 ： 


getName获得网络设备名称=awdl0
getDisplayName获得网络设备显示名称 ： awdl0
getHardwareAddress获得网卡的物理地址 ： 
-70 66 -25 34 -68 122 


getName获得网络设备名称=vmnet8
getDisplayName获得网络设备显示名称 ： vmnet8
getHardwareAddress获得网卡的物理地址 ： 
0 80 86 -64 0 8 


getName获得网络设备名称=vmnet1
getDisplayName获得网络设备显示名称 ： vmnet1
getHardwareAddress获得网卡的物理地址 ： 
0 80 86 -64 0 1 


getName获得网络设备名称=en0
getDisplayName获得网络设备显示名称 ： en0
getHardwareAddress获得网卡的物理地址 ： 
120 79 67 -104 74 -96 


getName获得网络设备名称=lo0
getDisplayName获得网络设备显示名称 ： lo0
getHardwareAddress获得网卡的物理地址 ： 

```
### 3.1.5 获得IP地址
- getInetAddresses() : 获取绑定到此网络接口的InetAddress列表
- 一个网络接口可以使用多个IP地址
- InetAddress类表示IP地址，有两个子类
    - Inet4Address.java
    - Inet6Address.java
- InetAddress类 没有public的构造方法，不能直接实例化，借助静态方法来实现对象创建。
- 1 获得IP地址的基本信息
- NetworkInterface的getInetAddresses()方法返回值
- InetAddress.java常用方法：
   - 1）getCanonicalHostName() ：获得完全限定域名
   - 2) getHostName() ： 获得此IP地址的主机名
   - 3) getHostAddress() ： 返回IP地址字符串
   - 4) getAddress() ： 返回原始IP地址，返回值是byte[]
   
```java
public class Test5 {
  public static void main(String[] args) {
    try {
      Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
      while (networkInterface.hasMoreElements()) {
        NetworkInterface eachNetworkInterface = networkInterface.nextElement();
        System.out.println("getName获得网络设备名称=" + eachNetworkInterface.getName());
        System.out.println("getDisplayName获得网络设备显示名称 ： " + eachNetworkInterface.getDisplayName());
        System.out.println("getInetAddress获得网络接口的InetAddress信息 ： ");
        Enumeration<InetAddress> enumInetAddress= eachNetworkInterface.getInetAddresses();
        while (enumInetAddress.hasMoreElements()) {
          InetAddress inetAddress = enumInetAddress.nextElement();
          System.out.println("getCanonicalHostName获取此IP地址的完全限定域名： " + inetAddress.getCanonicalHostName());
          System.out.println("getHostName获取此IP地址的主机名： " + inetAddress.getHostName());
          System.out.println("getHostAddress返回IP地址字符串： " + inetAddress.getHostAddress());
          System.out.println("getAddress返回原始IP地址： ");
          byte[] addressByte = inetAddress.getAddress();
          for (int i = 0; i < addressByte.length; i++) {
            System.out.print(addressByte[i] + " ");
          }
          System.out.println();
        }
        System.out.println();
        System.out.println();
      }
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }
}
```
### 3.1.6 InterfaceAddress类的使用
### 3.1.7 判断是否为点对点设备
### 3.1.8 是否支持多播

## 3.2 NetworkInterface类的静态方法
### 3.2.1 根据索引获得NetWorkInterface对象
### 3.2.2 根据网络接口名称获得NetWorkInterface对象
### 3.2.3 根据IP地址获得NetWorkInterface对象

## 3.3 小结

