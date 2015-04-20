package ua.cn.stu.messagetransfer.work;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by WarlordGrey on 19.04.2015.
 */
public class Sender {

    InetAddress addr;
    int port;

    public Sender(String ipAddress, int port) throws UnknownHostException{
        addr = Inet4Address.getByName(ipAddress);
        this.port = port;
    }

    public void sendMessage(String msg) throws IOException{
        Socket socket = new Socket(addr,port);
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())
        );
        out.write(msg);
        out.flush();
        out.close();
        socket.close();
    }

}
