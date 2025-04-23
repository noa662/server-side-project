package testSaveFile;

import HandleStoreFiles.IForSaving;

import java.util.List;

public class PersonForTestSaving implements IForSaving {

    String id;
    String name;

    public PersonForTestSaving(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getFolderName() {
        return getClass().getPackage().getName();
    }

    @Override
    public String getFileName() {
        return getClass().getSimpleName()+id;
    }

    @Override
    public String getData() {
        return id+","+name;
    }

    @Override
    public void parseFromFile(List<String> values) {

    }
}
