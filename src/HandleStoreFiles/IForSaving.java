package HandleStoreFiles;

import java.util.List;

public interface IForSaving {
    public String getFolderName();
    public  String getFileName();
    public String getData();
    void parseFromFile(List<String> values);
}
