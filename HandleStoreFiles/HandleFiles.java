package HandleStoreFiles;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HandleFiles {
    public void saveFile(IForSaving forSaving) {
        try {
            File folder = new File(forSaving.getFolderName());
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
        File file = new File(forSaving.getFolderName(), forSaving.getFileName() + ".txt");
        if (file.exists() && file.delete()) {
            System.out.println("הקובץ נמחק בהצלחה: " + file.getAbsolutePath());
        } else {
            System.out.println("שגיאה במחיקת הקובץ: " + file.getAbsolutePath());
        }
    }

    public void updateFile(IForSaving forSaving) {
        try {
            File file = new File(forSaving.getFolderName(), forSaving.getFileName() + ".txt");

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
}
