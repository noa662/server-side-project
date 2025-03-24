package HandleStoreFiles;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HandleFiles {
    public void saveFile(IForSaving forSaving){
        try {
            File folder=new File(forSaving.getFolderName());
            if(!folder.exists())
                folder.mkdir();
            File file=new File(forSaving.getFolderName()+"/"+forSaving.getFileName());
            file.createNewFile();
            FileOutputStream fis=new FileOutputStream(file);
            DataOutputStream dis=new DataOutputStream(fis);
            dis.write(forSaving.getData().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(IForSaving forSaving){
        File file=new File(forSaving.getFolderName()+"/"+forSaving.getFileName());
        file.delete();
    }

    public void updateFile(IForSaving forSaving){
        try {
            File file=new File(forSaving.getFolderName()+"/"+forSaving.getFileName());
            FileOutputStream fis=new FileOutputStream(file,true);
            DataOutputStream dis=new DataOutputStream(fis);
            dis.write(forSaving.getData().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFiles(List<IForSaving> forSavingList){
        for(IForSaving i:forSavingList){
            saveFile(i);
        }
    }

    private String getDirectoryPath(IForSaving forSaving){
        return new File(forSaving.getFolderName()).getAbsolutePath();
    }

    private String getFileName(IForSaving forSaving){
        return forSaving.getFileName();
    }
}
