package chapter3;

import org.junit.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 *
 * @author kate
 * @create 2019/7/2
 * @since 1.0.0
 */
public class Section3_2 {
  /**
   *  根据IP地址获得NetworkInterface对象
   * @param args
   * @throws UnknownHostException
   */
  public static void main(String[] args) throws UnknownHostException {
    try {
      InetAddress localhostAddress = InetAddress.getByName("127.0.0.1");
      NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localhostAddress);
      System.out.println(networkInterface.getName());
      System.out.println(networkInterface.getDisplayName());
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }

  /**
   * 根据网络接口名称获得NetworkInterface对象
   */
  @Test
  public void test10() {
    try {
      NetworkInterface networkInterface = NetworkInterface.getByName("lo0");
      System.out.println("-------->" + networkInterface.getName());
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }

  /**
   * 根据索引获得NetworkInterface对象
   */
  @Test
  public void test9() {
    try {
      Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
      while (networkInterface.hasMoreElements()) {
        NetworkInterface eachNetworkInterface = networkInterface.nextElement();
        System.out.println("getName= " + eachNetworkInterface.getName());
        System.out.println("getDisplayName= " + eachNetworkInterface.getDisplayName());
        System.out.println("getIndex= " + eachNetworkInterface.getIndex());
        System.out.println();
      }

      System.out.println();
      NetworkInterface networkInterface1 = NetworkInterface.getByIndex(1);
      System.out.println("----> " + networkInterface1.getName());
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }
}