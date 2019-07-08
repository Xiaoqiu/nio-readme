package chapter3;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author kate
 * @create 2019/7/1
 * @since 1.0.0
 */
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