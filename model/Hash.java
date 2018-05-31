package model;

import java.math.BigInteger;
import java.nio.ByteBuffer;


import static controller.service.Helper.longToByteArray;
import static model.Encrypt.encryption;


public class Hash {

    private static int gcdThing(int a, int b) {
        BigInteger b1 = BigInteger.valueOf(a);
        BigInteger b2 = BigInteger.valueOf(b);
        BigInteger gcd = b1.gcd(b2);
        return gcd.intValue();
    }

    public static byte[] addPadding(byte[] message){
        int size = 8-(message.length%8);
         byte[] messageResult = new byte[message.length+size];
        //System.out.println(size);
         if(size==1){
             for (int i = 0; i <message.length ; i++) {
                 messageResult[i] = message[i];
             }
             messageResult[messageResult.length-size] =  (byte)0x80;

         }
         else if (size>1){
             for (int i = 0; i <message.length ; i++) {
                 messageResult[i] = message[i];
             }
             messageResult[messageResult.length-size] = (byte)0x80;


             for (int i = message.length+1; i <messageResult.length ; i++) {
                 messageResult[i] = 0x00;
             }

         }
         else {
             for (int i = 0; i <message.length ; i++) {
                 messageResult[i] = message[i];
             }
         }

       System.out.println("Padding size : " + size);

         return messageResult;


    }

    public static byte[][] createBlocks(byte[] message){
       // System.out.println("m: " + message.length%8);
        byte[][]blocks = new byte[message.length/8][8];
      //  System.out.println(message.length/8);
        for(int i = 0, k = 0; i < message.length/8; ++i)
            for(int j = 0; j < 8; ++j)
                blocks[i][j] = message[k++];
        return blocks;
    }

    public static byte[] gFunction(long M,long H){
        long key = M^H;
        long message = H;
        byte[] Encrypted;
        byte[]Key = longToByteArray(key);
        byte[]Message = longToByteArray(H);
        long encrypted = ByteBuffer.wrap(encryption(Message,Key)).getLong()^message;
        Encrypted = longToByteArray(encrypted);
        return Encrypted;
    }

    public static byte[] hashFunction(byte[] message){
        byte[] addPadding = addPadding(message);
        byte[][] blocks = createBlocks(addPadding);
        byte[] H0 = { 0x00, 0x00, 0x00, 0x00, 0x00 ,0x00, 0x00 ,0x00 };
        int iterations = blocks.length;
        byte[][] hashIterations = new byte[iterations+1][8];
        hashIterations[0] = H0;

        for (int i = 1; i <iterations+1 ; i++) {
            long M = ByteBuffer.wrap(blocks[i-1]).getLong();
            long H = ByteBuffer.wrap(hashIterations[i-1]).getLong();

            hashIterations[i] = gFunction(M,H);

//            System.out.println("K : " + Long.toHexString(M));
//            System.out.println("B : " + Long.toHexString(H));
//            System.out.println("H : " + Long.toHexString(ByteBuffer.wrap(hashIterations[i]).getLong()));


        }

        return hashIterations[iterations];

    }
}
