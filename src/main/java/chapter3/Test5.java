package chapter3;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author kate
 * @create 2019/7/2
 * @since 1.0.0
 */
public class Test5 {
  public static void main(String[] args) {
    try {
      Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
      while (networkInterface.hasMoreElements()) {
        NetworkInterface eachNetworkInterface = networkInterface.nextElement();
        System.out.println("getName获得网络设备名称=" + eachNetworkInterface.getName());
        System.out.println("getDisplayName获得网络设备显示名称 ： " + eachNetworkInterface.getDisplayName());
        System.out.println("getInetAddress获得网络接口的InetAddress信息 ： ");
        Enumeration<InetAddress> enumInetAddress = eachNetworkInterface.getInetAddresses();
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