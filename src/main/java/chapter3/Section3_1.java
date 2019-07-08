package chapter3;

import org.junit.Test;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * @author kate
 * @create 2019/7/2
 * @since 1.0.0
 */
public class Section3_1 {

  /**
   * 是否支持多播（组播）
   * 组播D类IP地址：224.0.0.0 - 239.255.255.255
   */
  @Test
  public void test8() {
    try {
      Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
      while (networkInterface.hasMoreElements()) {
        NetworkInterface eachNetworkInterface = networkInterface.nextElement();
        System.out.println("getName= " + eachNetworkInterface.getName());
        System.out.println("getDisplayName= " + eachNetworkInterface.getDisplayName());
        System.out.println("supportsMulticast= " + eachNetworkInterface.supportsMulticast());
        System.out.println();
        System.out.println();
      }
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }

  /**
   * 判断是否为点对点设备
   */
  @Test
  public void test7() {
    try {
      Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
      while (networkInterface.hasMoreElements()) {
        NetworkInterface eachNetworkInterface = networkInterface.nextElement();
        System.out.println("getName= " + eachNetworkInterface.getName());
        System.out.println("getDisplayName= " + eachNetworkInterface.getDisplayName());
        System.out.println("isPointToPoint= " + eachNetworkInterface.isPointToPoint());

        //
        List<InterfaceAddress> addressList = eachNetworkInterface.getInterfaceAddresses();


        System.out.println();
        System.out.println();
      }
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }


}