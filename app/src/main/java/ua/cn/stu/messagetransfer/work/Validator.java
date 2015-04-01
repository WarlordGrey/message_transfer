package ua.cn.stu.messagetransfer.work;

import java.util.regex.Pattern;

/**
 * Created by Admin on 30.03.2015.
 */
public class Validator {

    private static Validator me = null;

    private Validator(){

    }

    public static Validator getInstance(){
        if (me == null){
            me = new Validator();
        }
        return  me;
    }

    public boolean isValidIp(String ip){
        Pattern ipPattern = Pattern.compile("^(\\d{1,3}\\.){3}\\d{1,3}$");
        return ipPattern.matcher(ip).matches();
    }

}
