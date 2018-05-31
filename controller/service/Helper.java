package controller.service;

public class Helper {
    public static byte[] intToByteArray(int a)
    {
        byte[] ret = new byte[4];
        ret[3] = (byte) (a & 0xFF);
        ret[2] = (byte) ((a >> 8) & 0xFF);
        ret[1] = (byte) ((a >> 16) & 0xFF);
        ret[0] = (byte) ((a >> 24) & 0xFF);
        return ret;
    }

    public static byte[] longToByteArray(long a)
    {
        byte[] ret = new byte[8];
        ret[7] = (byte) (a & 0xFF);
        ret[6] = (byte) ((a >> 8) & 0xFF);
        ret[5] = (byte) ((a >> 16) & 0xFF);
        ret[4] = (byte) ((a >> 24) & 0xFF);
        ret[3] = (byte) ((a >> 32) & 0xFF);
        ret[2] = (byte) ((a >> 40) & 0xFF);
        ret[1] = (byte) ((a >> 48) & 0xFF);
        ret[0] = (byte) ((a >> 56) & 0xFF);

        return ret;
    }

    public static int plusMinus256(byte b){
        int result = 0;
        if(b>=0){
            result = (int)b;
        }
        else {
            result = (int)b + 256;
        }
        return result;
    }
}
