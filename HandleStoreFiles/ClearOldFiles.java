package HandleStoreFiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;

public class ClearOldFiles {

    public void deleteOldFiles(String directoryName, int days) {
        File directory = new File(directoryName);
        if (!directory.exists())
            return;
        LocalDateTime now = LocalDateTime.now();
        Instant nowInstant = now.atZone(ZoneId.systemDefault()).toInstant();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                try {
                    BasicFileAttributes basicTime = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                    Instant creationTime = basicTime.creationTime().toInstant();
                    LocalDateTime createDate = LocalDateTime.ofInstant(creationTime, ZoneId.systemDefault());
                    if (createDate.plusDays(days).isBefore(now)) {
                        file.delete();
                        System.out.println("file: " + file.getName() + " is deleted!!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
