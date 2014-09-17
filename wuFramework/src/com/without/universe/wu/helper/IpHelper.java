package com.without.universe.wu.helper;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.badlogic.gdx.utils.Array;

public class IpHelper {
	public static Array<InetAddress> getIpAddresses() {
		Array<InetAddress> addresses = new Array<InetAddress>();
		try {
			InetAddress inetAddress = null;

			for (Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces(); networkInterface
					.hasMoreElements();) {
				NetworkInterface singleInterface = networkInterface.nextElement();
				for (Enumeration<InetAddress> IpAddresses = singleInterface.getInetAddresses(); IpAddresses.hasMoreElements();) {
					inetAddress = IpAddresses.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						addresses.add(inetAddress);
					}
				}
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
		return addresses;
	}

	public static Array<String> convertToIpV4String(Array<InetAddress> list) {
		Array<String> ipv4 = new Array<String>();
		for (InetAddress address : list) {
			if (address instanceof Inet4Address) {
				ipv4.add(address.getHostAddress());
			}
		}
		return ipv4;
	}

}
