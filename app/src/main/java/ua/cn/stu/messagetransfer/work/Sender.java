package ua.cn.stu.messagetransfer.work;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by WarlordGrey on 19.04.2015.
 */
public class Sender {

    private static String COMMAND_LOGIN = "login";
    private static String COMMAND_SEND_MESSAGE = "message";

    private InetAddress addr;
    private int port;

    public Sender(String ipAddress, int port) throws UnknownHostException {
        addr = Inet4Address.getByName(ipAddress);
        this.port = port;
    }

    public void sendMessage(String msg) throws IOException {
        Socket socket = new Socket(addr, port);
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())
        );
        out.write(COMMAND_SEND_MESSAGE);
        out.newLine();
        out.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        boolean resultOk = toBoolean(in.readLine());
        if(resultOk){
            out.write(msg);
            out.newLine();
            out.flush();
        }
        in.close();
        out.close();
        socket.close();
    }

    public boolean isLoginCorrect(String login, String password) throws IOException {
        Socket socket = new Socket(addr, port);
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())
        );
        out.write(COMMAND_LOGIN);
        out.newLine();
        out.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        boolean resultOk = toBoolean(in.readLine());
        boolean goodLogin = false;
        if(resultOk){
            out.write(login);
            out.newLine();
            out.write(password);
            out.newLine();
            out.flush();
            goodLogin = toBoolean(in.readLine());
        }
        in.close();
        out.close();
        socket.close();
        return goodLogin;
    }

    private static boolean toBoolean(String s) {
        return ((s != null) && s.equalsIgnoreCase("true"));
    }

}
