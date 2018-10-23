import KnockKnock.KnockKnockClient;
import KnockKnock.KnockKnockMultiServer;
import KnockKnock.KnockKnockServer;
import Library.LibraryClient;
import Library.LibraryMultiServer;
import Smtp.SmtpDriver;
import UdpMathServer.UdpMathServer;

public class Main {

    public static void main(String[] args) throws Exception {

        //KnockKnockServer.run();
        //KnockKnockMultiServer.run();
        //KnockKnockClient.run();

        //LibraryMultiServer.run();
        //LibraryClient.run();

        //SmtpDriver.run();

        UdpMathServer.run();

    }

}
