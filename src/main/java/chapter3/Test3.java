package chapter3;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author kate
 * @create 2019/7/1
 * @since 1.0.0
 */
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