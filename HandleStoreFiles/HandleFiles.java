package HandleStoreFiles;

import Data.Inquiry;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandleFiles {
    public static String FOLDER="Inquiries";
    public void saveFile(IForSaving forSaving) {
        try {
            File folder = new File(FOLDER,forSaving.getFolderName());
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(folder, forSaving.getFileName() + ".txt");

            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
                writer.write(forSaving.getData());
            }

            System.out.println("קובץ נשמר בהצלחה: " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("שגיאה בשמירת הקובץ: " + e.getMessage(), e);
        }
    }

    public void deleteFile(IForSaving forSaving) {
        File f = new File(FOLDER,forSaving.getFolderName());
        File file=new File(f,forSaving.getFileName() + ".txt");
        System.out.println(file.exists());
        if (file.exists() && file.delete()) {
            System.out.println("הקובץ נמחק בהצלחה: " + file.getAbsolutePath());
        } else {
            System.out.println("שגיאה במחיקת הקובץ: " + file.getAbsolutePath());
        }
    }

    public void updateFile(IForSaving forSaving) {
        try {
            File f = new File(FOLDER,forSaving.getFolderName());
            File file=new File(f,forSaving.getFileName() + ".txt");

            if (!file.exists()) {
                System.out.println("הקובץ לא קיים, יבוצע שמירה במקום עדכון.");
                saveFile(forSaving);
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {
                writer.newLine();
                writer.write(forSaving.getData());
            }

            System.out.println("הקובץ עודכן בהצלחה: " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("שגיאה בעדכון הקובץ: " + e.getMessage(), e);
        }
    }

    public void saveFiles(List<IForSaving> forSavingList) {
        for (IForSaving i : forSavingList) {
            saveFile(i);
        }
    }

    public IForSaving readFile(File f){
        try {
            BufferedReader br=new BufferedReader(new FileReader(f));
            String line= br.readLine();
            List<String> values= Arrays.asList(line.split(","));
            String  className=values.get(0);
            IForSaving inquiry=null;
            if(!className.substring(5).equals("Complaint"))
                 inquiry=(IForSaving) Class.forName(values.get(0)).getConstructor().newInstance();
             else
                 inquiry=(IForSaving) Class.forName(values.get(0)).getConstructor().newInstance();
            inquiry.parseFromFile(values);
            br.close();
            return inquiry;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
