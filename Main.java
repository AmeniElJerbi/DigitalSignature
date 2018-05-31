
import java.io.IOException;

import java.util.Scanner;



import static controller.service.FileController.*;

import static model.Hash.hashFunction;

import static model.Signature.signature;



public class Main {



    public static void main(String[] args) throws IOException {

       Scanner in = new Scanner(System.in);
       String path = setPath(in);
        byte[] bytes = getAllBytes(path);
        byte [] hash = hashFunction(bytes);
        String[] toWrite = signature(hash);
        toWrite[0] = path;
        writeToFile(toWrite,path+".sign");




    }

}
