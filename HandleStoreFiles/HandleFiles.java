package HandleStoreFiles;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HandleFiles {
    public void saveFile(IForSaving forSaving){
        File file=new File(forSaving.getFileName());
        try {
            file.createNewFile();
            FileOutputStream fis=new FileOutputStream(file);
            DataOutputStream dis=new DataOutputStream(fis);
            dis.write(forSaving.getData().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(IForSaving forSaving){
        
    }
}
