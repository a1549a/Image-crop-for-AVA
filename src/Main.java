import com.google.gson.Gson;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Main {
    public static int count = -1;
    public static int inpossibleCount = 0;

    public static int stopCount = 250_000;
    public static boolean stopProg = false;

    public static void main(String[] args) {

//        Img image = new Img("/home/a1549a/Pictures/iii.png");


        try {
            File file = new File("/home/a1549a/Desktop/AVA.txt");

            Scanner sc = new Scanner(file);

            while (sc.hasNext() && !stopProg) {
                int index = sc.nextInt();
                int id = sc.nextInt();

                int raiting = 0;
                for (int i = 0, mark = -9; i < 10; i++, mark += 2) {
                    int k = sc.nextInt();
                    raiting += mark * k;
                }

                int bla = sc.nextInt();
                bla = sc.nextInt();
                bla = sc.nextInt();

//                read image by index if its possible
                Img image = new Img("/home/a1549a/Desktop/images/" + Integer.toString(index) + ".jpg");

                if (image.isValid) {
                    //write image RGB integer values in file
                    count++;
                    raiting = raiting > 95 ? 1 : 0;

                    arrToStringInput(image.toArrayImg());
                    ToStringOutput(raiting);

                    count++;
                    arrToStringInput(image.toArrayRandomCrop());
                    ToStringOutput(raiting);

                    count++;
                    arrToStringInput(image.toArrayRandomCrop());
                    ToStringOutput(raiting);

                    count++;
                    arrToStringInput(image.toArrayRandomCrop());
                    ToStringOutput(raiting);

                    count++;
                    arrToStringInput(image.toArrayRandomCrop());
                    ToStringOutput(raiting);
                    if (count == stopCount) {
                        stopProg = true;
                    }
                } else {
                    inpossibleCount++;
                }

                if ((count) % 5000 == 0 && count != 0) {
                    System.out.println(" Ready state " + count * 1.0 / stopCount * 100 + "%");
                    System.out.println("count : " + count);
                    System.out.println("InPossible Count : " + inpossibleCount);
                }

            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Array to string and run file writer
    public static void arrToStringInput(int arr[][][]) {

        byte data[] = new byte[64 * 64 * 3];
        int n = 0;

        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                for (int k = 0; k < 3; k++) {
                    data[n++] = (byte) arr[i][j][k];
                }
            }
        }

        fileWriteIn(data);

    }

    public static void ToStringOutput(int raiting) {

        byte data[] = new byte[1];
        data[0] = (byte) raiting;

        fileWriteOut(data);

    }

    //write file In
    static int datasize = 25000;

    static int counter = 0;

    public static void fileWriteIn(byte data[]) {


        String name = "Input" + Integer.toString(count / datasize) + ".bin";
        File file = new File(name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Files.write(Paths.get(name), data, StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    //write file Out
    public static void fileWriteOut(byte data[]) {

        String name = "Output" + Integer.toString(count / datasize) + ".bin";
        File file = new File(name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Files.write(Paths.get(name), data, StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
