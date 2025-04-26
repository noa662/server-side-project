package HandleStoreFiles;

import java.io.File;
import java.util.List;

public class RenameFilesAtNight extends Thread{

    String directory;
    String text;

    public RenameFilesAtNight(String directory,String text){
        this.directory=directory;
        this.text=text;
    }

    @Override
    public void run() {
        File dir=new File(directory);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println(" error: The directory doesn't exist" + directory);
            return;
        }

        File[] files = dir.listFiles();
        if (files == null) {
            System.out.println("error:  There is no files in the directory " + directory);
            return;
        }

        for(File f:files){
            if(f.isFile()){
                File newFile=new File(directory, text + f.getName());
                f.renameTo(newFile);
                System.out.println("Rename file from "+f.getName()+" to "+newFile);
            }
        }
    }
}
