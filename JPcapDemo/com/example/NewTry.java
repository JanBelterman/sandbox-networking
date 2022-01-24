package com.example;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewTry {

    public static void main(String[] args) throws IOException {
//        DnsUtils.getHostName("109.237.211.228");
//        DnsUtils.getHostName("74.125.127.106");
        String ipAddress = RegexUtils.extractIpAddress("asdas74.125.127.106asd as d");
        System.out.println(ipAddress);
        System.out.println();
        System.out.println();
        System.out.println(System.getProperty("java.library.path"));
        NetworkInterface[] ni = JpcapCaptor.getDeviceList();
        System.out.println(Arrays.toString(ni));
        for (int i = 0; i < ni.length; i++) {
            NetworkInterface nwi = ni[i];
            System.out.println(nwi.name);
            System.out.println(nwi.description);
            System.out.println(nwi.datalink_name);
            System.out.println(nwi.datalink_description);
            System.out.println(Arrays.toString(nwi.addresses));
            System.out.println(Arrays.toString(nwi.mac_address));
        }
        JpcapCaptor captor = JpcapCaptor.openDevice(ni[1], 200, false, 10000);
        captor.setFilter("port 443", true);
        captor.loopPacket(90, new JanPacketReceiver());
    }

}

class JanPacketReceiver implements PacketReceiver {

    private int count = 0;

    @Override
    public void receivePacket(Packet packet) {
        count++;
        System.out.println("Got packet: " + count);
        System.out.println(packet);
        String data = packet.toString();
        System.out.println(data);
//        System.out.println(RegexUtils.extractIpAddress(packet.toString()));
        String ipAddress = RegexUtils.extractIpAddress(packet.toString());
        if (ipAddress == null) return;
        System.out.println(ipAddress);
        try {
            String hostName = DnsUtils.getHostName(ipAddress);
            System.out.println(hostName);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
//        System.out.println(packet.header);
//        System.out.println(packet.datalink);
//        System.out.println(packet.caplen);
//        System.out.println(packet.len);
//        System.out.println(Arrays.toString(packet.data));
//        packet.
    }
}

class DnsUtils {

    public static String getHostName(String ip) throws UnknownHostException {
        InetAddress ia;
//        ia = InetAddress.getByAddress(new byte[] {74,125,127,106});
        ia = InetAddress.getByName(ip);
        System.out.println(ia.getCanonicalHostName());
        return ia.getHostName().toLowerCase(Locale.ROOT);
    }
}

class RegexUtils {

    public static String extractIpAddress(String data) {
//        Pattern pattern = Pattern.compile("([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})");
        Pattern pattern = Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
//        Matcher mat = pat.matcher("192.168.143.130 asd");
        Matcher m = pattern.matcher(data);
        boolean found = m.find();
        if (!found) {
            return null;
        }
        return m.group();
//        Matcher m = pattern.matcher(data);
//        System.out.println(m);
//        System.out.println(m.matches());
////        System.out.println(m.group());
//        System.out.println(m.toMatchResult().start());
//        return m.group();
    }

}
