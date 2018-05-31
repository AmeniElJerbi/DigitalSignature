package controller.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public  class FileController {

    public static String getFilename(String path){
        File f = new File(path);
        return f.getName();
    }

    public static byte[] getAllBytes(String p) throws IOException {
        Path path = Paths.get(p);
        byte[] arr = Files.readAllBytes(path);
        return arr;

    }

    public static void writeToFile(String[] text, String path ){
        File fout = new File(path);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fout);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (int i = 0; i < text.length; i++) {
            try {
                bw.write(text[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String setPath(Scanner in){
        // Scanner in = new Scanner(System.in);
        System.out.println("Print path of file you want to sign : ");
        String path = in.nextLine();
        return path;
    }

    public static String setPath1(Scanner in){
        // Scanner in = new Scanner(System.in);
        System.out.println("Print path of file that you signed : ");
        String path = in.nextLine();
        return path;
    }

    public static String[] parseFile(String path){
        BufferedReader reader;
        String [] check = new String[5];
        try {
            reader = new BufferedReader(new FileReader(
                    path));
            String line = reader.readLine();
            int i =0;
            while (line != null) {
               // System.out.println(line);
                // read next line
                line = reader.readLine();
                check[i] = line;
                i++;
            }

            for (int j = 0; j <4 ; j++) {
                check[j] = check[j].split(" ")[2];
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



        return check;
    }
    }


