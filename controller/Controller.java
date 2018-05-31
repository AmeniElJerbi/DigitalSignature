package controller;

import java.io.IOException;

import java.util.Scanner;

import static controller.service.FileController.getAllBytes;
import static controller.service.FileController.setPath;
import static controller.service.FileController.writeToFile;
import static model.Hash.hashFunction;
import static model.Signature.signature;
import static view.Menu.showMenu;

public class Controller {
    public static void Menu() throws IOException {
        Scanner in = new Scanner(System.in);
            showMenu();
            int a = in.nextInt();

                switch (a){
                    case 1 :
                        String path = setPath(in);


                            byte[] bytes = getAllBytes(path);
                            byte [] hash = hashFunction(bytes);
                            String[] toWrite = signature(hash);
                            toWrite[0] = path;
                            writeToFile(toWrite,path+".sign");
                            break;
                        }


                }




    }



