package model;

import java.math.BigInteger;
import java.util.Random;

import static model.Hash.hashFunction;

public class Signature {


    public static byte[] addPadToHash(byte[] hash){
        byte [] reversBytes = new byte[16];
        for (int i = 7; i >=0 ; i--) {
            reversBytes[7-i]=hash[i];
        }
        reversBytes[8]=0x00;
        reversBytes[9]= (byte) 0xff;
        reversBytes[10]= (byte) 0xff;
        reversBytes[11]= (byte) 0xff;
        reversBytes[12]= (byte) 0xff;
        reversBytes[13]= (byte) 0xff;
        reversBytes[14]= (byte) 0xff;
        reversBytes[15]=0x00;
        return reversBytes;
    }


    public static String[] signature(byte[] hash){


        BigInteger p = new BigInteger("AF5228967057FE1CB84B92511BE89A47",16);
        BigInteger q = new BigInteger("57A9144B382BFF0E5C25C9288DF44D23",16);
        BigInteger a = new BigInteger("9E93A4096E5416CED0242228014B67B5",16);
        BigInteger H = new BigInteger(1,hash);

        //System.out.println("H : "+H.toString(16));

        Random rand = new Random();
//       BigInteger U = new BigInteger("36C35C862ED90C1327D086CB0CA7F939", 16);
//        BigInteger x = new BigInteger("A69B1F284D33B191FDB59099A0703E0C",16);
        BigInteger U = new BigInteger(128, rand);
        BigInteger x = new BigInteger(128,rand);
       // System.out.println("U : " + U.toString(16));
      //  System.out.println("x : "+ x.toString(16));
         //System.out.println(U.toString(10));
        while (U.compareTo(p)==1){
             //System.out.println(U.toString(10));
            U = new BigInteger(128,rand);
        }
        while (x.compareTo(p)==1){
            x = new BigInteger(128,rand);
        }

        BigInteger Z = a.modPow(U,p);
       System.out.println("Z : "+Z.toString(16));
        BigInteger UH = U.multiply(H).mod(q);
       // System.out.println("UH : "+ UH.toString(16));
        BigInteger xZ = x.multiply(Z).mod(q);
       // System.out.println("xZ : "+ xZ.toString(16));
        BigInteger k =((UH.subtract(xZ)).multiply(H.modInverse(q))).mod(q);
        System.out.println("K : " + k.toString(16));
        BigInteger g = (xZ.multiply(H.modInverse(q))).mod(q);
        System.out.println("g : "+g.toString(16));
        BigInteger S = a.modPow(g,p);
        System.out.println("S : "+S.toString(16));
        BigInteger y = a.modPow(x,p);
      //  System.out.println("y : "+y.toString(16));
        System.out.println("H : " + H.toString(16));
        System.out.println("Y : " + y.toString(16));
        System.out.println("K : " + k.toString(16));
        //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        BigInteger ApowK =((a.modPow(k,p)).multiply(S)).mod(p);
        BigInteger SpowH = S.modPow(H,p);
        BigInteger Ypow = y.modPow(ApowK,p);

       // System.out.println("S^H : " + SpowH.toString(16));
      //  System.out.println("Y^ : " + Ypow.toString(16));



        if (H.toString(16).length()<16){
            String[] result = {"","H = 0" + H.toString(16).toUpperCase(),"Y = " + y.toString(16).toUpperCase(),
                    "K = " + k.toString(16).toUpperCase(),"S = " + S.toString(16).toUpperCase()};
            return result;
        }
            String[] result = {"","H = " + H.toString(16).toUpperCase(),"Y = " + y.toString(16).toUpperCase(),
                    "K = " + k.toString(16).toUpperCase(),"S = " + S.toString(16).toUpperCase()};


        return result;
    }

    public static boolean verify(String Ht, String Y,String K,String s,byte[] message){
        BigInteger Htt = new BigInteger(Ht,16);
        BigInteger y = new BigInteger(Y,16);
        BigInteger k = new BigInteger(K,16);
        BigInteger S = new BigInteger(s,16);



        BigInteger p = new BigInteger("AF5228967057FE1CB84B92511BE89A47",16);
        //BigInteger q = new BigInteger("57A9144B382BFF0E5C25C9288DF44D23",16);
        BigInteger a = new BigInteger("9E93A4096E5416CED0242228014B67B5",16);
        BigInteger H = new BigInteger(1,hashFunction(message));
        if (H.equals(Htt)){
            BigInteger ApowK =((a.modPow(k,p)).multiply(S)).mod(p);
            BigInteger SpowH = S.modPow(H,p);
            BigInteger Ypow = y.modPow(ApowK,p);
            if (SpowH.equals(Ypow)){
                System.out.println("Sign is valid");
                return true;
            }
            else {
                System.out.println("Sign is not valid");
                return false;
            }

        }
        else {
            System.out.println("Sign is not valid");
            return false;
        }
    }

}
