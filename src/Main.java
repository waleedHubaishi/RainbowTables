import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) throws NoSuchAlgorithmException {

        int twoDimensionalLength = 2000;

        String [][] rainbowTable = loadRainBowFromFile("rainbow");

        //STUFE IS LIKE THE ORDER OF THE HASH IN THE LIST STARTING WITH 0
        Rainbow rainbow = new Rainbow("0000000",twoDimensionalLength,twoDimensionalLength);
        rainbow.constructRainbowTable();
        rainbow.setRainBowTable(rainbowTable);
        rainbow.checkIfHashIsInRainbowTable("ce5345a9405474ef2244727cba11fcd5");

        writeRainbowTableToFile(rainbow.rainBowTable);

    }
    /**
     * This method loads the rainbow table from the file into an array.
     *
     * @param fileName that contains the rainbow table.
     */
    public static String[][] loadRainBowFromFile(String fileName)
    {
        File file = new File(fileName+".txt");

        String [][] rainbowTable = new String[2000][2];
        int counter = 0;
        String[] passwordsInEachLine;
        String line="";
        Scanner scan = null;

        try {
            scan = new Scanner(file);
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                passwordsInEachLine = line.split(",");
                rainbowTable[counter][0] = passwordsInEachLine[0];
                rainbowTable[counter][1] = passwordsInEachLine[1];
                counter++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            scan.close();
        }

        return rainbowTable;
    }



    /**
     * This method writes the rainbow table to "rainbow.txt".
     *
     * @param rainbowTable which is the array that contains the rainbow table.
     */
    public static void writeRainbowTableToFile(String[][] rainbowTable)
    {
        File textFile = new File("rainbow.txt");

        try (FileOutputStream fop = new FileOutputStream(textFile)) {

            // if file doesn't exists, then create it
            if (!textFile.exists()) {
                textFile.createNewFile();
            }

            for(int i=0;i<rainbowTable.length;i++){
                String line = rainbowTable[i][0]+","+rainbowTable[i][1];
                // get the content in bytes
                byte[] contentInChar = line.getBytes();
                fop.write(contentInChar);
                if (i<rainbowTable.length-1){
                    fop.write(System.getProperty("line.separator").getBytes());
                }
            }

            fop.flush();
            fop.close();

            System.out.println("Please check the file rainbow.txt to see the rainbow table");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * This method reads the y value from the file passed as a parameter.
     *
     * @param fileName that contains the y value.
     * @return line the actual y value as a string.
     */
    public static String readFile(String fileName)
    {
        File file = new File(fileName+".txt");

        String line="";
        Scanner scan = null;

        try {
            scan = new Scanner(file);
            while (scan.hasNextLine()) {
                line = scan.nextLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            scan.close();
        }

        return line;
    }

    /**
     * This method writes the actual text into the file "KlarText.txt".
     *
     * @param m which is the message that should be written into the file.
     */
    public static void writeToFile(String m)
    {
        File textFile = new File("KlarText.txt");

        try (FileOutputStream fop = new FileOutputStream(textFile)) {

            // if file doesn't exists, then create it
            if (!textFile.exists()) {
                textFile.createNewFile();
            }

            // get the content in bytes
            byte[] contentInChar = m.getBytes();
            fop.write(contentInChar);
            fop.flush();
            fop.close();

            System.out.println("Please check the file KlarText.txt to see the original text");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
