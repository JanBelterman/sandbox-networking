package UdpMathServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpMathServer {

    public static void run() throws Exception {

        DatagramSocket socket = new DatagramSocket(18000);

        boolean running = true;
        while (running) {
            byte[] buf = new byte[1024];

            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            socket.receive(packet);

            InetAddress address = packet.getAddress();
            int port = packet.getPort();

            String received
                    = new String(packet.getData(), 0, packet.getLength());

            String result = calculate(received);
            System.out.println(result);
            buf = result.getBytes();
            packet = new DatagramPacket(buf, buf.length, address, port);

            if (received.equals("end")) {
                running = false;
                continue;
            }

            socket.send(packet);

        }

        socket.close();


    }

    private static String calculate(String received) {
        System.out.println("Calculate called");

        String[] parts = received.split(";");

        if (parts.length < 2) {
            return "Not a valid command";
        }

        if (parts[2].equals("*")) {
            return String.valueOf(Integer.parseInt(parts[0]) * Integer.parseInt(parts[1]));
        } else if (parts[2].equals("/")) {
            return String.valueOf(Integer.parseInt(parts[0]) / Integer.parseInt(parts[1]));
        } else if (parts[2].equals("-")) {
            return String.valueOf(Integer.parseInt(parts[0]) - Integer.parseInt(parts[1]));
        } else if (parts[2].equals("+")) {
            return String.valueOf(Integer.parseInt(parts[0]) + Integer.parseInt(parts[1]));
        }

        return "Not a valid request";

    }

}
