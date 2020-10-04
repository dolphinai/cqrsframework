package com.github.larkvii.cqrsframework.commons.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

public final class MachineUtils {

  private MachineUtils() {
  }

  private static InetAddress getLocalAddress() {
    InetAddress address;
    try {
      address = InetAddress.getLocalHost();
    } catch (UnknownHostException e) {
      throw new IllegalStateException(e);
    }
    return address;
  }

  public static String getHostAddress() {
    return getLocalAddress().getHostAddress();
  }

  public static String getHostName() {
    return getLocalAddress().getHostName();
  }

  public static String getMacAddress() {
    final InetAddress address = getLocalAddress();
    final StringBuilder result = new StringBuilder();
    try {
      NetworkInterface network = NetworkInterface.getByInetAddress(address);
      byte[] mac = network.getHardwareAddress();
      for (int i = 0; i < mac.length; i++) {
        result.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
      }
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    return result.toString();
  }
}
