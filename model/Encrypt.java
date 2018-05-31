package model;

import controller.service.Sblock;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static controller.service.Helper.intToByteArray;
import static controller.service.Helper.plusMinus256;

public class Encrypt implements Sblock {


    public static int nonLinear(int x){
        int result;
        byte[] bytesOfX = intToByteArray(x);
        int[] indexesForS = new int[4];
        byte[] resultBytes = new byte[4];
        for (int i = 0; i <4 ; i++) {
            indexesForS[i] = plusMinus256(bytesOfX[i]);
        }
        for (int i = 0; i <4 ; i++) {
            resultBytes[i] = (byte) S[indexesForS[i]];
        }

        result = ByteBuffer.wrap(resultBytes).getInt();

        return result;
    }

    public static int roundFunction(int roundKey,int currentR){
        int result=0;
        int keyOnR = roundKey^currentR;
        result = Integer.rotateLeft(nonLinear(keyOnR),13);
        return result;
    }

    public static byte[] fourRounds(int[] keys,int[]L,int[]R){
        int []l =L;
        l[0] = Integer.reverseBytes(l[0]);
        int[]r = R;
        r[0] = Integer.reverseBytes(r[0]);
        byte[] resultL ;
        byte[] resultR ;
        for (int i = 1; i <5 ; i++) {
//            System.out.println("Round " + (i-1)+ "L : ");
//            System.out.println(Integer.toHexString(l[i-1]));
//            System.out.println("R : ");
//            System.out.println(Integer.toHexString(r[i-1]));
            l[i] = roundFunction(keys[i],r[i-1]) ^ l[i-1];
            r[i] = l[i-1];
        }

        //System.out.println("L4 : "+ Integer.toHexString(l[4]));
        resultL = intToByteArray(l[4]);
        resultR = intToByteArray(r[4]);

        byte[] resultBytes = {resultR[3],resultR[2],resultR[1],resultR[0],
                                resultL[3],resultL[2],resultL[1],resultL[0]};
        //long res = ByteBuffer.wrap(resultBytes).getLong();
        //System.out.println(Long.toHexString(res));
        return resultBytes;
    }

    public static byte[] encryption(byte[] array,byte[] key){
        byte[] l0 = Arrays.copyOfRange(array,0,4);
        byte[] r0 =Arrays.copyOfRange(array,4,8);
        byte[] k1 = Arrays.copyOfRange(key,0,4);
        byte[] k2 = Arrays.copyOfRange(key,4,8);

        int L0, R0, K1, K2, K3, K4;
        L0 = ByteBuffer.wrap(l0).getInt();
        R0 = ByteBuffer.wrap(r0).getInt();
        K1 = Integer.reverseBytes(ByteBuffer.wrap(k1).getInt());
        K2 = Integer.reverseBytes(ByteBuffer.wrap(k2).getInt());
        K3 = ~K2;
        K4 = ~K1;
       // System.out.println(Integer.toHexString(K1)+" : "+Integer.toHexString(K2)+" : "+Integer.toHexString(K3)+" : "+Integer.toHexString(K4)+" : ");
        int []roundKeys ={0,K1,K2,K3,K4};
        int []roundL = {L0,0,0,0,0};

        int []roundR = {R0,0,0,0,0};

        byte[] encrypted = fourRounds(roundKeys,roundL,roundR);

        return encrypted;
    }

}
