package ua.cn.stu.messagetransfer.work;

import java.util.regex.Pattern;

import ua.cn.stu.messagetransfer.exceptions.IpNumbersCountException;

/**
 * Created by WarlordGrey on 30.03.2015.
 */
public class Validator {

    private static Validator me = null;
    private static  int MAX_NUMBER_COUNT_IN_IP = 4;
    private static  int MAX_IP_NUMBER = 255;

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
        if( ipPattern.matcher(ip).matches()){
            for (int i = 1; i < MAX_NUMBER_COUNT_IN_IP + 1; i++){
                int nextIpNum;
                try {
                    nextIpNum = getIntFromIp(ip,i);
                } catch (IpNumbersCountException e) {
                    return false;
                }
                if (nextIpNum > MAX_IP_NUMBER){
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private int getIntFromIp(String ip, int number) throws IpNumbersCountException{
        if (number > MAX_NUMBER_COUNT_IN_IP+1){
            throw new IpNumbersCountException("Max ip numbers count = "+MAX_NUMBER_COUNT_IN_IP+"!");
        }
        final int correctedPos = 1;
        int start = 0;
        int end = ip.indexOf(".");
        String tmpIp = ip;
        for (int i = 1; i < number; i++){
            start += tmpIp.indexOf(".") + correctedPos;
            tmpIp = tmpIp.substring(tmpIp.indexOf(".") + correctedPos);
            if (tmpIp.contains(".")){
                end += tmpIp.indexOf(".") + correctedPos;
            } else {
                end += tmpIp.length() + correctedPos;
            }
        }
        return Integer.parseInt(ip.substring(start,end));
    }

}
