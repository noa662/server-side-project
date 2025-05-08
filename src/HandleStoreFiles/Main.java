package HandleStoreFiles;

import testSaveFile.PersonForTestSaving;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        RenameFilesAtNight rn = new RenameFilesAtNight("test", "Racheli");
//        rn.start();

        PersonForTestSaving p5 = new PersonForTestSaving("123456789","sucess BH!");
        HandleFiles handleFiles=new HandleFiles();
        handleFiles.saveCSV(p5, "123456789");

        PersonForTestSaving readP5 = (PersonForTestSaving) handleFiles. readCsv ("123456789.csv");
        System.out.println(readP5);



    }
}
